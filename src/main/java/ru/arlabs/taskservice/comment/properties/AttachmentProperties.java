package ru.arlabs.taskservice.comment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Configuration
@ConfigurationProperties(prefix = "task.attachment")
public class AttachmentProperties {
    private Path attachmentFolder = Paths.get("files/");
    private int maxAttachments = 4;
}
