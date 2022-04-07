package ru.arlabs.taskservice.security.request;

import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class ResetPasswordInfo {
    private String code;
    private String password;
}
