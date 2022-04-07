package ru.arlabs.taskservice.task.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.arlabs.taskservice.task.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByProject_Id(long projectId, Pageable pageable);

    Optional<Task> findByIdAndProject_Id(long id, long projectId);

    void deleteByIdAndProject_Id(long id, long projectId);
}