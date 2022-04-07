package ru.arlabs.taskservice.comment.internal;

import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.arlabs.taskservice.comment.CommentService;
import ru.arlabs.taskservice.comment.converter.CommentConverter;
import ru.arlabs.taskservice.comment.data.AttachmentRepository;
import ru.arlabs.taskservice.comment.data.CommentRepository;
import ru.arlabs.taskservice.comment.exception.TooManyAttachmentsException;
import ru.arlabs.taskservice.comment.model.Attachment;
import ru.arlabs.taskservice.comment.model.Comment;
import ru.arlabs.taskservice.comment.response.AttachmentFile;
import ru.arlabs.taskservice.comment.response.AttachmentInfo;
import ru.arlabs.taskservice.comment.response.CommentInfo;
import ru.arlabs.taskservice.comment.properties.AttachmentProperties;
import ru.arlabs.taskservice.common.exception.AccessDeniedException;
import ru.arlabs.taskservice.task.data.TaskRepository;
import ru.arlabs.taskservice.task.model.Task;
import ru.arlabs.taskservice.user.data.UserRepository;
import ru.arlabs.taskservice.user.model.User;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCommentService implements CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final CommentConverter converter;
    private final AttachmentProperties attachmentProperties;


    @Autowired
    public DefaultCommentService(CommentRepository commentRepository,
                                 TaskRepository taskRepository,
                                 AttachmentRepository attachmentRepository,
                                 CommentConverter converter,
                                 UserRepository userRepository,
                                 AttachmentProperties attachmentProperties) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.attachmentRepository = attachmentRepository;
        this.converter = converter;
        this.userRepository = userRepository;
        this.attachmentProperties = attachmentProperties;
    }

    @Override
    public Page<CommentInfo> getComments(long projectId, long taskId, Pageable pageable) {
        final Task task = taskRepository.findByIdAndProject_Id(taskId, projectId)
                .orElseThrow(EntityNotFoundException::new);
        return commentRepository.findAllByTask(task, pageable)
                .map(converter::convertInfo);
    }

    @Override
    public void writeComment(long userId, long projectId, long taskId, String text, MultipartFile[] attachmentFiles) throws TooManyAttachmentsException {
        if (attachmentFiles != null && attachmentFiles.length > attachmentProperties.getMaxAttachments()) {
            throw new TooManyAttachmentsException();
        }
        final Task task = taskRepository.findByIdAndProject_Id(taskId, projectId)
                .orElseThrow(EntityNotFoundException::new);
        final User sender = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        final Comment comment = new Comment(text, sender, task);

        commentRepository.save(comment);

        if (attachmentFiles != null) {
            List<Attachment> attachments = Arrays.stream(attachmentFiles)
                    .map(f -> this.createAttachment(f, comment))
                    .collect(Collectors.toList());

            attachmentRepository.saveAll(attachments);
        }
    }

    @Override
    public void updateComment(long userId, long projectId, long taskId, long id, String text) {
        final Comment comment = commentRepository.findComment(id, taskId, projectId)
                .orElseThrow(EntityNotFoundException::new);
        if (comment.getSender().getId() != userId) {
            throw new AccessDeniedException("Can't update this comment");
        }
        comment.setText(text);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long userId, long projectId, long taskId, long id) {
        final Comment comment = commentRepository.findComment(id, taskId, projectId)
                .orElseThrow(EntityNotFoundException::new);
        if (comment.getSender().getId() != userId) {
            throw new AccessDeniedException("Can't update this comment");
        }
        commentRepository.delete(comment);
    }

    @Override
    public AttachmentFile getAttachment(String address) {
        final Attachment attachment = attachmentRepository.findByAddress(address)
                .orElseThrow(EntityNotFoundException::new);
        return new AttachmentFile(attachment.getName(), attachment.getType(),  attachmentProperties.getAttachmentFolder()
                .resolve(attachment.getAddress()));
    }

    @SneakyThrows
    private Attachment createAttachment(MultipartFile file, Comment comment) {
        final String address = RandomStringUtils.random(16, true, true);
        final Path attachmentPath = attachmentProperties.getAttachmentFolder()
                .resolve(address);
        file.transferTo(attachmentPath);
        final Attachment attachment = new Attachment();
        attachment.setAddress(address);
        attachment.setType(file.getContentType());
        attachment.setName(file.getOriginalFilename());
        attachment.setComment(comment);

        return attachment;
    }
}
