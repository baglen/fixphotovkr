package ru.arlabs.taskservice.project.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arlabs.taskservice.project.model.ProjectType;

import java.util.List;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {
    List<ProjectType> findAllByOwner_Id(long ownerId);

    ProjectType getByIdAndOwner_Id(long id, long ownerId);

    void deleteByIdAndOwner_Id(long id, long ownerId);
}