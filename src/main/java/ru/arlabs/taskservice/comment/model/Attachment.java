package ru.arlabs.taskservice.comment.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attachments", schema = "public")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(Clock.systemUTC());
    }
}