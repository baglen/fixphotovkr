package ru.arlabs.taskservice.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Jeb
 */
@Data
@AllArgsConstructor
public class AuthResult {
    private String token;
}
