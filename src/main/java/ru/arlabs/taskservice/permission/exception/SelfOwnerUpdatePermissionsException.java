package ru.arlabs.taskservice.permission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Jeb
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SelfOwnerUpdatePermissionsException extends IllegalArgumentException {
    public SelfOwnerUpdatePermissionsException() {
        super("Owner update permission");
    }
}
