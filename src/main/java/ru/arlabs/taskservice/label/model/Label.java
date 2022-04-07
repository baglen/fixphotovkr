package ru.arlabs.taskservice.label.model;

import lombok.Getter;
import lombok.Setter;
import ru.arlabs.taskservice.project.model.Project;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "labels", schema = "public")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color", nullable = false)
    private String color = "#FFFFFF";

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "label_project_id", nullable = false)
    private Project project;
}