package ru.arlabs.taskservice.comment.response;

import lombok.Data;
import ru.arlabs.taskservice.user.response.UserSummaryInfo;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentInfo {
    private long id;
    private UserSummaryInfo author;
    private String text;
    private List<AttachmentInfo> attachments;
    private LocalDateTime createdAt;
}
