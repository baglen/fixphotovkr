package ru.arlabs.taskservice.permission.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arlabs.taskservice.permission.model.ProjectPermission;
import ru.arlabs.taskservice.permission.model.ProjectPermissionId;
import ru.arlabs.taskservice.user.model.User;

import java.util.List;
import java.util.Optional;

public interface ProjectPermissionRepository extends JpaRepository<ProjectPermission, ProjectPermissionId> {
    @Query("SELECT p FROM ProjectPermission p WHERE p.project.owner.id = :userId AND p.project.id = :id")
    List<ProjectPermission> findAllByProject(long id, long userId);

    @Query("SELECT p FROM ProjectPermission  p WHERE p.user = :target AND p.project.id = :projectId AND p.project.owner.id = :ownerId")
    Optional<ProjectPermission> findByUserAndProjectOwner(User target, long projectId, long ownerId);
}