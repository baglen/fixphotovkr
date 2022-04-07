package ru.arlabs.taskservice.project.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arlabs.taskservice.project.ProjectTypeService;
import ru.arlabs.taskservice.project.converter.ProjectTypeConverter;
import ru.arlabs.taskservice.project.data.ProjectTypeRepository;
import ru.arlabs.taskservice.project.model.ProjectType;
import ru.arlabs.taskservice.project.request.NewProjectType;
import ru.arlabs.taskservice.project.request.UpdateProjectType;
import ru.arlabs.taskservice.project.response.ProjectTypeInfo;
import ru.arlabs.taskservice.user.data.UserRepository;

import java.util.List;

/**
 * @author Jeb
 */
@Service
public class DefaultProjectTypeService implements ProjectTypeService {
    private final UserRepository userRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final ProjectTypeConverter converter;

    @Autowired
    public DefaultProjectTypeService(ProjectTypeRepository projectTypeRepository, ProjectTypeConverter converter, UserRepository userRepository) {
        this.projectTypeRepository = projectTypeRepository;
        this.converter = converter;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectTypeInfo> findProjectTypes(long userId) {
        final List<ProjectType> projectTypes = projectTypeRepository.findAllByOwner_Id(userId);
        return converter.convertAllToInfo(projectTypes);
    }

    @Override
    public void createProjectType(long userId, NewProjectType newProjectType) {
        final ProjectType projectType = converter.convertNew(newProjectType);
        projectType.setOwner(userRepository.getById(userId));
        projectTypeRepository.save(projectType);
    }

    @Override
    public void updateProjectType(long userId, long id, UpdateProjectType updateProjectType) {
        final ProjectType projectType = projectTypeRepository.getByIdAndOwner_Id(id, userId);
        converter.updateProjectType(updateProjectType, projectType);
        projectTypeRepository.save(projectType);
    }

    @Override
    public void deleteProjectType(long userId, long id) {
        projectTypeRepository.deleteByIdAndOwner_Id(id, userId);
    }
}
