package ru.arlabs.taskservice.project.model;

import lombok.Getter;
import lombok.Setter;
import ru.arlabs.taskservice.permission.model.ProjectPermission;
import ru.arlabs.taskservice.user.model.User;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "project_type_id")
    private ProjectType projectType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "user_favorite_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @OneToMany(mappedBy = "project",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<ProjectPermission> permissions = new HashSet<>();

    public boolean isFavorite(long userId) {
        return users
                .stream()
                .anyMatch(u -> u.getId().equals(userId));
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now(Clock.systemUTC());
    }


}