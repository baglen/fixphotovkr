package ru.arlabs.taskservice.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.arlabs.taskservice.comment.exception.TooManyAttachmentsException;
import ru.arlabs.taskservice.comment.response.AttachmentFile;
import ru.arlabs.taskservice.comment.response.AttachmentInfo;
import ru.arlabs.taskservice.comment.response.CommentInfo;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;

public interface CommentService {
    @Transactional(readOnly = true)
    @PreAuthorize("@permissionService.canRead(authentication.principal.id, #projectId)")
    Page<CommentInfo> getComments(long projectId, long taskId, Pageable pageable);

    @Transactional
    @PreAuthorize("@permissionService.canPerform(authentication.principal.id, #projectId)")
    void writeComment(long userId, long projectId, long taskId, String text, MultipartFile[] attachments) throws TooManyAttachmentsException;

    @Transactional
    void updateComment(long userId, long projectId, long taskId, long id, String text);

    @Transactional
    void deleteComment(long userId, long projectId, long taskId, long id);

    @Transactional
    AttachmentFile getAttachment(String address);
}
