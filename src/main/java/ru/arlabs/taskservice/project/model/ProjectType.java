package ru.arlabs.taskservice.project.model;

import lombok.Getter;
import lombok.Setter;
import ru.arlabs.taskservice.user.model.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "project_types", schema = "public")
public class ProjectType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_owner_id", nullable = false)
    private User owner;
}