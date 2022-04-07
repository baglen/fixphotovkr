package ru.arlabs.taskservice.security.request;

import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class VerificationInfo {
    private String code;
}
