package ru.arlabs.taskservice.comment.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arlabs.taskservice.comment.model.Comment;
import ru.arlabs.taskservice.task.model.Task;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByTask(Task task, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.id = :id AND c.task.id = :taskId AND c.task.project.id = :projectId")
    Optional<Comment> findComment(long id, long taskId, long projectId);
}