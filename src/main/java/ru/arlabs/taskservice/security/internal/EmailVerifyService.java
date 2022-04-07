package ru.arlabs.taskservice.security.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arlabs.taskservice.common.properties.ApplicationProperties;
import ru.arlabs.taskservice.mail.MailService;
import ru.arlabs.taskservice.security.VerifyService;
import ru.arlabs.taskservice.security.data.VerifyCodeRepository;
import ru.arlabs.taskservice.security.request.VerificationInfo;
import ru.arlabs.taskservice.user.data.UserRepository;
import ru.arlabs.taskservice.user.model.User;
import ru.arlabs.taskservice.security.model.VerifyCode;

import java.util.Optional;

/**
 * @author Jeb
 */
@Service
public class EmailVerifyService implements VerifyService {
    private final MailService mailService;
    private final VerifyCodeRepository verifyCodeRepository;
    private final ApplicationProperties applicationProperties;
    private final UserRepository userRepository;

    @Autowired
    public EmailVerifyService(MailService mailService,
                              VerifyCodeRepository verifyCodeRepository,
                              ApplicationProperties applicationProperties,
                              UserRepository userRepository) {
        this.mailService = mailService;
        this.verifyCodeRepository = verifyCodeRepository;
        this.applicationProperties = applicationProperties;
        this.userRepository = userRepository;
    }

    @Override
    public void sendVerification(User user) {
        VerifyCode verifyCode = new VerifyCode(user);
        String url = String.format(applicationProperties.getVerifyUrl(), verifyCode.getCode());
        mailService.send(user.getEmail(), "Подтверждение", String.format("Для подтверждения перейдите по ссылке: %s", url));
        verifyCodeRepository.save(verifyCode);
    }

    @Override
    public void verify(VerificationInfo request) {
        final Optional<User> optionalUser = verifyCodeRepository.findByCode(request.getCode())
                .map(VerifyCode::getUser);
        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            user.setEnabled(true);
            userRepository.save(user);
        }
    }
}
