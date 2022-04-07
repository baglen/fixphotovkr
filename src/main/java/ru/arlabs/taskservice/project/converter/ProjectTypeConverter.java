package ru.arlabs.taskservice.project.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import ru.arlabs.taskservice.project.model.ProjectType;
import ru.arlabs.taskservice.project.request.NewProjectType;
import ru.arlabs.taskservice.project.request.UpdateProjectType;
import ru.arlabs.taskservice.project.response.ProjectTypeInfo;

import java.util.List;

/**
 * @author Jeb
 */
@Mapper(componentModel = "spring")
public interface ProjectTypeConverter {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "owner", ignore = true)
    })
    ProjectType convertNew(NewProjectType source);

    List<ProjectTypeInfo> convertAllToInfo(List<ProjectType> projectTypes);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "owner", ignore = true)
    })
    void updateProjectType(UpdateProjectType updateProjectType, @MappingTarget ProjectType projectType);
}
