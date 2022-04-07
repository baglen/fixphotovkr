package ru.arlabs.taskservice.task.model;

import lombok.Getter;
import lombok.Setter;
import ru.arlabs.taskservice.project.model.Project;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "perform_tasks", schema = "public")
@Entity
public class PerformTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "execution_time", nullable = false)
    private LocalDateTime executionTime;

}