package ru.arlabs.taskservice.label.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arlabs.taskservice.label.model.Label;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findAllByProject_Id(long projectId);

    Optional<Label> findByIdAndProject_Id(long id, long projectId);

    void deleteByIdAndProject_Id(long id, long projectId);
}