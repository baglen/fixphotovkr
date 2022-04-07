package ru.arlabs.taskservice.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.arlabs.taskservice.comment.exception.TooManyAttachmentsException;
import ru.arlabs.taskservice.comment.request.UpdateComment;
import ru.arlabs.taskservice.comment.response.CommentInfo;
import ru.arlabs.taskservice.security.request.AuthorizedUser;

import static java.awt.SystemColor.text;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public Page<CommentInfo> getComments(@PathVariable long projectId,
                                         @PathVariable long taskId,
                                         @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return commentService.getComments(projectId, taskId, pageable);
    }

    @PostMapping
    public void writeComment(@AuthenticationPrincipal AuthorizedUser user,
                             @PathVariable long projectId,
                             @PathVariable long taskId,
                             @RequestParam("text") String text,
                             @RequestParam(value = "attachments", required = false) MultipartFile[] attachments) throws TooManyAttachmentsException {
        commentService.writeComment(user.getId(), projectId, taskId, text, attachments);
    }

    @PutMapping("/{id}")
    public void updateComment(@AuthenticationPrincipal AuthorizedUser user,
                              @PathVariable long projectId,
                              @PathVariable long taskId,
                              @PathVariable long id,
                              @RequestBody UpdateComment updateComment) {
        commentService.updateComment(user.getId(), projectId, taskId, id, updateComment.getText());
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@AuthenticationPrincipal AuthorizedUser user,
                              @PathVariable long projectId,
                              @PathVariable long taskId,
                              @PathVariable long id) {
        commentService.deleteComment(user.getId(), projectId, taskId, id);
    }
}
