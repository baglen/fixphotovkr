package ru.arlabs.taskservice.comment.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;

@Data
@AllArgsConstructor
public class AttachmentFile {
    private String filename;
    private String type;
    private Path path;
}
