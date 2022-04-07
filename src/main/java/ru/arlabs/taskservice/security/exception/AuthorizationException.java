package ru.arlabs.taskservice.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Jeb
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationException extends Exception {
    public AuthorizationException(String message) {
        super(message);
    }

}
