package ru.arlabs.taskservice.permission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.arlabs.taskservice.permission.model.Permission;

/**
 * @author Jeb
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPermissionException extends IllegalArgumentException {
    public InvalidPermissionException(Permission permission) {
        super(permission.name() + " is invalid permission for users");
    }
}
