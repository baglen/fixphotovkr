package ru.arlabs.taskservice.user.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arlabs.taskservice.common.exception.InvalidPasswordException;
import ru.arlabs.taskservice.common.exception.ObjectAlreadyExistException;
import ru.arlabs.taskservice.security.VerifyService;
import ru.arlabs.taskservice.security.request.RegisterInfo;
import ru.arlabs.taskservice.user.UserService;
import ru.arlabs.taskservice.user.converter.UserConverter;
import ru.arlabs.taskservice.user.data.UserRepository;
import ru.arlabs.taskservice.user.model.User;
import ru.arlabs.taskservice.user.request.UpdateUser;

/**
 * @author Jeb
 */
@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final VerifyService verifyService;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository,
                              VerifyService verifyService,
                              UserConverter userConverter,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.verifyService = verifyService;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterInfo request) throws ObjectAlreadyExistException {
        if (userRepository.existsByEmailOrPhoneAndEnabledTrue(request.getEmail(), request.getPhone())) {
            throw new ObjectAlreadyExistException("User with email or phone already exists");
        }
        final User user = userRepository.findByEmail(request.getEmail())
                .orElse(new User());
        userConverter.updateRegister(request, user);
        userRepository.save(user);
        verifyService.sendVerification(user);
    }

    @Override
    public User inviteUser(String email) {
       final User user = new User();
       user.setEmail(email);
       return userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(long userId, String password) {
        final User user = userRepository.getById(userId);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(long userId, String oldPassword, String newPassword) throws InvalidPasswordException {
        final User user = userRepository.getById(userId);
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new InvalidPasswordException("Invalid password");
        }
    }

    @Override
    @Transactional
    public void update(long userId, UpdateUser updateUser) {
        final User user = userRepository.getById(userId);
        userConverter.updateUser(updateUser, user);
        userRepository.save(user);
    }
}
