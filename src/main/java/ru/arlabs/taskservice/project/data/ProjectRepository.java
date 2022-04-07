package ru.arlabs.taskservice.project.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arlabs.taskservice.project.model.Project;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p JOIN p.permissions pp WHERE pp.user.id = :userId")
    Page<Project> findAllByUser(long userId, Pageable pageable);

    @Query("SELECT p.project FROM ProjectPermission p WHERE p.user.id = :userId AND p.project.id = :id")
    Optional<Project> findByUser(long id, long userId);

    Optional<Project> findByIdAndOwner_Id(long id, long userId);


    void deleteByIdAndOwner_Id(long id, long userId);
}