package ru.arlabs.taskservice.security;


import ru.arlabs.taskservice.security.exception.AuthorizationException;
import ru.arlabs.taskservice.security.request.AuthInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jeb
 */
public interface AuthenticationService {
    String authenticate(HttpServletRequest request, AuthInfo authenticationRequest) throws AuthorizationException;
}
