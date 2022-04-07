package ru.arlabs.taskservice.security.request;

import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author Jeb
 */
@Data
public class ResetInfo {
    @Email(message = "Invalid email format")
    private String email;
}
