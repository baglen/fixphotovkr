package ru.arlabs.taskservice.permission.model;

import lombok.Getter;
import lombok.Setter;
import ru.arlabs.taskservice.project.model.Project;
import ru.arlabs.taskservice.user.model.User;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_project_permissions", schema = "public")
public class ProjectPermission {
    @EmbeddedId
    private ProjectPermissionId id = new ProjectPermissionId();

    @MapsId("userId")
    @ManyToOne
    private User user;

    @MapsId("projectId")
    @ManyToOne
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false)
    private Permission permission;
}