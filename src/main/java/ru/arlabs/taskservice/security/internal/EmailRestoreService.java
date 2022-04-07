package ru.arlabs.taskservice.security.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.arlabs.taskservice.attempts.AttemptService;
import ru.arlabs.taskservice.common.properties.ApplicationProperties;
import ru.arlabs.taskservice.mail.MailService;
import ru.arlabs.taskservice.security.RestoreService;
import ru.arlabs.taskservice.security.data.ResetCodeRepository;
import ru.arlabs.taskservice.security.model.ResetCode;
import ru.arlabs.taskservice.security.request.ResetPasswordInfo;
import ru.arlabs.taskservice.user.UserService;
import ru.arlabs.taskservice.user.data.UserRepository;
import ru.arlabs.taskservice.user.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Jeb
 */
@Service
public class EmailRestoreService implements RestoreService {
    private final MailService mailService;
    private final UserRepository userRepository;
    private final ResetCodeRepository resetCodeRepository;
    private final ApplicationProperties applicationProperties;
    private final UserService userService;
    private final AttemptService attemptService;

    @Autowired
    public EmailRestoreService(MailService mailService,
                               UserRepository userRepository,
                               ResetCodeRepository resetCodeRepository,
                               ApplicationProperties applicationProperties,
                               UserService userService,
                               @Qualifier("changeAttemptService") AttemptService attemptService) {
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.resetCodeRepository = resetCodeRepository;
        this.applicationProperties = applicationProperties;
        this.userService = userService;
        this.attemptService = attemptService;
    }

    @Override
    public void restore(HttpServletRequest servletRequest, String email) {
        attemptService.attempt(servletRequest);
        final Optional<User> user = userRepository.findByLogin(email);
        if (user.isEmpty()) {
            return;
        }
        ResetCode verifyCode = new ResetCode(user.get());
        String url = String.format(applicationProperties.getVerifyUrl(), verifyCode.getCode());
        mailService.send(email, "Восстановление", String.format("Для восстановления перейдите по ссылке: %s", url));
        resetCodeRepository.save(verifyCode);
    }

    @Override
    public void changePassword(ResetPasswordInfo request) {
        final Optional<User> optionalUser = resetCodeRepository.findByCode(request.getCode())
                .map(ResetCode::getUser);
        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            userService.changePassword(user.getId(), request.getPassword());
        }
    }
}
