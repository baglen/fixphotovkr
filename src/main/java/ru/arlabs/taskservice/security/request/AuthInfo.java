package ru.arlabs.taskservice.security.request;

import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class AuthInfo {
    private String login;
    private String password;
}
