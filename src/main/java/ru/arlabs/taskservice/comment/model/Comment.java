package ru.arlabs.taskservice.comment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.arlabs.taskservice.task.model.Task;
import ru.arlabs.taskservice.user.model.User;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "comments", schema = "public")
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "comment", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Attachment> attachments;

    public Comment(String text, User sender, Task task) {
        this.text = text;
        this.sender = sender;
        this.task = task;
    }


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(Clock.systemUTC());
    }

}