package ru.arlabs.taskservice.security.request;

import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class AuthorizedUser {
    private Long id;
    private String email;
    private String username;
    private String phone;
}
