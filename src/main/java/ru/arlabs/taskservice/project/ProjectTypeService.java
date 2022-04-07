package ru.arlabs.taskservice.project;

import org.springframework.transaction.annotation.Transactional;
import ru.arlabs.taskservice.project.request.NewProjectType;
import ru.arlabs.taskservice.project.request.UpdateProjectType;
import ru.arlabs.taskservice.project.response.ProjectTypeInfo;

import java.util.List;

/**
 * @author Jeb
 */
public interface ProjectTypeService {

    @Transactional
    List<ProjectTypeInfo> findProjectTypes(long userId);

    void createProjectType(long userId, NewProjectType newProjectType);

    void deleteProjectType(long userId, long id);

    void updateProjectType(long userId, long id, UpdateProjectType updateProjectType);
}
