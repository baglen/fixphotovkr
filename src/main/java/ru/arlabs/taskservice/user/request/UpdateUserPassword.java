package ru.arlabs.taskservice.user.request;

import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class UpdateUserPassword {
    private String oldPassword;
    private String password;
}
