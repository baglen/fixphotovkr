package ru.arlabs.taskservice.security;

import ru.arlabs.taskservice.security.request.VerificationInfo;
import ru.arlabs.taskservice.user.model.User;

/**
 * @author Jeb
 */
public interface VerifyService {
    void sendVerification(User user);

    void verify(VerificationInfo request);
}
