package ru.arlabs.taskservice.user.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author Jeb
 */
@Data
public class UpdateUser {
    @Pattern(regexp = "^[a-zA-Z ]{0,255}$", message = "Invalid username format")
    private String username;
}
