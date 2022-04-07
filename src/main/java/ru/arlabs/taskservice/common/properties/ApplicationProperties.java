package ru.arlabs.taskservice.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jeb
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "task")
public class ApplicationProperties {
    private String jwtSecret = "";
    private String verifyUrl = "http://localhost:8080/verify/%s?user=%s";
    private String resetUrl = "http://localhost:8080/reset/%s?user=%s";
}
