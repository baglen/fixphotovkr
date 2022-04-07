package ru.arlabs.taskservice.comment.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arlabs.taskservice.comment.model.Attachment;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByAddress(String address);
}