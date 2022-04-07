package ru.arlabs.taskservice.security;

import ru.arlabs.taskservice.security.request.ResetPasswordInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jeb
 */
public interface RestoreService {
    void restore(HttpServletRequest servletRequest, String email);

    void changePassword(ResetPasswordInfo request);
}
