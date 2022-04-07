package ru.arlabs.taskservice.project.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import ru.arlabs.taskservice.project.model.Project;
import ru.arlabs.taskservice.project.request.NewProject;
import ru.arlabs.taskservice.project.request.UpdateProject;
import ru.arlabs.taskservice.project.response.ProjectSummaryInfo;

/**
 * @author Jeb
 */
@Mapper(componentModel = "spring")
public interface ProjectConverter {
    @Mappings({
            @Mapping(target = "typeName", source = "project.projectType.name"),
            @Mapping(target = "ownerName", source = "project.owner.username"),
            @Mapping(target = "favorite", expression = "java(project.isFavorite(userId))"),
            @Mapping(target = "owner", ignore = true),
            @Mapping(target = "type", ignore = true),
    })
    ProjectSummaryInfo convertToInfo(Project project, long userId);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "avatar", ignore = true),
            @Mapping(target = "permissions", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "owner", ignore = true),
            @Mapping(target = "projectType", ignore = true),
            @Mapping(target = "users", ignore = true)
    })
    Project convertNew(NewProject newProject);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "avatar", ignore = true),
            @Mapping(target = "permissions", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "owner", ignore = true),
            @Mapping(target = "projectType", ignore = true),
            @Mapping(target = "users", ignore = true),
            @Mapping(target = "code", ignore = true)
    })
    void update(UpdateProject updateProject, @MappingTarget Project project);
}
