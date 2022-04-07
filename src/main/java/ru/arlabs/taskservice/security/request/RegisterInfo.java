package ru.arlabs.taskservice.security.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

/**
 * @author Jeb
 */
@Data
public class RegisterInfo {
    @Pattern(regexp = "^(\\+7)[0-9]{10}$", message = "Invalid phone format")
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    private String password;

    @Pattern(regexp = "^[a-zA-Z ]{0,255}$", message = "Invalid username format")
    private String username;
}
