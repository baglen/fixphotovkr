package ru.arlabs.taskservice.permission.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arlabs.taskservice.permission.PermissionService;
import ru.arlabs.taskservice.permission.data.ProjectPermissionRepository;
import ru.arlabs.taskservice.project.data.ProjectRepository;
import ru.arlabs.taskservice.permission.exception.InvalidPermissionException;
import ru.arlabs.taskservice.permission.exception.SelfOwnerUpdatePermissionsException;
import ru.arlabs.taskservice.permission.model.Permission;
import ru.arlabs.taskservice.permission.model.ProjectPermission;
import ru.arlabs.taskservice.permission.model.ProjectPermissionId;
import ru.arlabs.taskservice.permission.request.UpdatePermission;
import ru.arlabs.taskservice.permission.response.UserPermissionInfo;
import ru.arlabs.taskservice.project.model.Project;
import ru.arlabs.taskservice.user.UserService;
import ru.arlabs.taskservice.user.data.UserRepository;
import ru.arlabs.taskservice.user.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jeb
 */
@Service("permissionService")
public class DefaultPermissionService implements PermissionService {
    private final ProjectPermissionRepository permissionRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public DefaultPermissionService(ProjectPermissionRepository permissionRepository,
                                    ProjectRepository projectRepository,
                                    UserRepository userRepository,
                                    UserService userService) {
        this.permissionRepository = permissionRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public boolean canRead(long userId, long projectId) {
        return permissionRepository.existsById(new ProjectPermissionId(projectId, userId));
    }

    @Override
    public boolean canWrite(long userId, long projectId) {
        return permissionRepository.findById(new ProjectPermissionId(projectId, userId))
                .map(ProjectPermission::getPermission)
                .map(Permission::canWrite)
                .orElse(false);
    }

    @Override
    public boolean canPerform(long userId, long projectId) {
        return permissionRepository.findById(new ProjectPermissionId(projectId, userId))
                .map(ProjectPermission::getPermission)
                .map(Permission::canPerform)
                .orElse(false);
    }

    @Override
    public List<UserPermissionInfo> getPermissions(long userId, long projectId) {
        return permissionRepository.findAllByProject(projectId, userId)
                .stream()
                .map(UserPermissionInfo::new)
                .collect(Collectors.toList());
    }

    @Override
    public void setPermission(long userId, long projectId, UpdatePermission updatePermission) {
        final User target = userRepository.findByEmail(updatePermission.getEmail())
                .orElseGet(() -> userService.inviteUser(updatePermission.getEmail()));

        if (target.getId().equals(userId)) {
            throw new SelfOwnerUpdatePermissionsException();
        }

        final Optional<ProjectPermission> optionalPermission = permissionRepository.findByUserAndProjectOwner(target, projectId, userId);
        if (updatePermission.getPermission() == null) {
            optionalPermission.ifPresent(permission -> permissionRepository.deleteById(permission.getId()));
        } else {
            if (updatePermission.getPermission().equals(Permission.ADMINISTRATOR)) {
                throw new InvalidPermissionException(updatePermission.getPermission());
            }

            final ProjectPermission projectPermission;

            if (optionalPermission.isPresent()) {
                projectPermission = optionalPermission.get();
            } else {
                final Project project = projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
                projectPermission = new ProjectPermission();
                projectPermission.setProject(project);
                projectPermission.setUser(target);
            }

            projectPermission.setPermission(updatePermission.getPermission());
            permissionRepository.save(projectPermission);
        }

    }
}
