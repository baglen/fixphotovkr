package ru.arlabs.taskservice.photo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jeb
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "task.photos")
public class PhotoProperties {
    private String baseUrl;
    private String secretKey;
    private String apiKey;
}
