package ru.arlabs.taskservice.project.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.arlabs.taskservice.permission.model.Permission;
import ru.arlabs.taskservice.permission.model.ProjectPermission;
import ru.arlabs.taskservice.photo.PhotoService;
import ru.arlabs.taskservice.project.ProjectService;
import ru.arlabs.taskservice.project.converter.ProjectConverter;
import ru.arlabs.taskservice.project.data.ProjectRepository;
import ru.arlabs.taskservice.project.data.ProjectTypeRepository;
import ru.arlabs.taskservice.project.model.Project;
import ru.arlabs.taskservice.project.model.ProjectType;
import ru.arlabs.taskservice.project.request.NewProject;
import ru.arlabs.taskservice.project.request.UpdateProject;
import ru.arlabs.taskservice.project.response.ProjectSummaryInfo;
import ru.arlabs.taskservice.user.data.UserRepository;
import ru.arlabs.taskservice.user.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

/**
 * @author Jeb
 */
@Service
public class DefaultProjectService implements ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final ProjectConverter converter;
    private final PhotoService photoService;

    @Autowired
    public DefaultProjectService(UserRepository userRepository,
                                 ProjectRepository projectRepository,
                                 ProjectConverter converter,
                                 ProjectTypeRepository projectTypeRepository,
                                 PhotoService photoService) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.converter = converter;
        this.projectTypeRepository = projectTypeRepository;
        this.photoService = photoService;
    }

    @Override
    public Page<ProjectSummaryInfo> getUserProjects(long userId, Pageable pageable) {
        return projectRepository.findAllByUser(userId, pageable)
                .map(p -> converter.convertToInfo(p, userId));

    }

    @Override
    public void createProject(long userId, NewProject newProject) {
        final Project project = converter.convertNew(newProject);
        final User owner = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        project.setOwner(owner);
        if (newProject.getTypeId() != null) {
            final ProjectType projectType = projectTypeRepository.getByIdAndOwner_Id(newProject.getTypeId(), userId);
            project.setProjectType(projectType);
        }
        final ProjectPermission projectPermission = new ProjectPermission();
        projectPermission.setProject(project);
        projectPermission.setUser(owner);
        projectPermission.setPermission(Permission.ADMINISTRATOR);
        project.getPermissions().add(projectPermission);
        projectRepository.save(project);
    }

    @Override
    public void deleteProject(long userId, long projectId) {
        projectRepository.deleteByIdAndOwner_Id(projectId, userId);
    }

    @Override
    public void updateProject(long userId, long projectId, UpdateProject updateProject) {
        final Project project = projectRepository.findByIdAndOwner_Id(projectId, userId)
                .orElseThrow(EntityNotFoundException::new);
        converter.update(updateProject, project);
        projectRepository.save(project);
    }

    @Override
    public boolean favoriteProject(long userId, long projectId) {
        final Project project = projectRepository.findByUser(projectId, userId)
                .orElseThrow(EntityNotFoundException::new);
        final User user = userRepository.getById(userId);
        if (project.getUsers().contains(user)) {
            project.getUsers().remove(user);
        } else {
            project.getUsers().add(user);
        }
        projectRepository.save(project);
        return project.getUsers().contains(user);
    }

    @Override
    public void updateAvatar(long userId, long projectId, MultipartFile file) {
        final Project project = projectRepository.findByUser(projectId, userId)
                .orElseThrow(EntityNotFoundException::new);

        final String avatar = project.getAvatar();
        if (avatar != null) {
            final UUID photoId = UUID.fromString(avatar);
            photoService.delete(photoId);
        }

        final UUID photoId = photoService.upload(file);
        project.setAvatar(photoId.toString());
        projectRepository.save(project);
    }
}
