package ru.arlabs.taskservice.permission.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPermissionId implements Serializable {

    private static final long serialVersionUID = -2003960865484952523L;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProjectPermissionId entity = (ProjectPermissionId) o;
        return Objects.equals(this.projectId, entity.projectId) &&
                Objects.equals(this.userId, entity.userId);
    }
}