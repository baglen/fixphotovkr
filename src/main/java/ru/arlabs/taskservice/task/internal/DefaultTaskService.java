package ru.arlabs.taskservice.task.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arlabs.taskservice.comment.response.AttachmentInfo;
import ru.arlabs.taskservice.label.data.LabelRepository;
import ru.arlabs.taskservice.label.model.Label;
import ru.arlabs.taskservice.permission.PermissionService;
import ru.arlabs.taskservice.project.data.ProjectRepository;
import ru.arlabs.taskservice.project.model.Project;
import ru.arlabs.taskservice.task.TaskService;
import ru.arlabs.taskservice.task.converter.TaskConverter;
import ru.arlabs.taskservice.task.data.TaskRepository;
import ru.arlabs.taskservice.task.exception.InvalidPerformerException;
import ru.arlabs.taskservice.task.model.Task;
import ru.arlabs.taskservice.task.request.NewTask;
import ru.arlabs.taskservice.task.request.UpdateTask;
import ru.arlabs.taskservice.task.response.TaskInfo;
import ru.arlabs.taskservice.user.data.UserRepository;
import ru.arlabs.taskservice.user.model.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class DefaultTaskService implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final LabelRepository labelRepository;
    private final TaskConverter converter;
    private final PermissionService permissionService;

    @Autowired
    public DefaultTaskService(TaskRepository taskRepository,
                              UserRepository userRepository,
                              ProjectRepository projectRepository,
                              LabelRepository labelRepository,
                              TaskConverter converter,
                              PermissionService permissionService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.labelRepository = labelRepository;
        this.converter = converter;
        this.permissionService = permissionService;
    }

    @Override
    @Transactional
    public Page<TaskInfo> getTasks(long projectId, Pageable pageable) {
        return taskRepository.findAllByProject_Id(projectId, pageable)
                .map(TaskInfo::new);
    }

    @Override
    public void createTask(long userId, long projectId, NewTask newTask) {
        final User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        final Project project = projectRepository.findById(projectId)
                .orElseThrow(EntityNotFoundException::new);
        final Task task = converter.convertNew(newTask);
        task.setProject(project);
        task.setOwner(user);
        taskRepository.save(task);
    }

    @Override
    public void updateTask(long projectId, long taskId, UpdateTask updateTask) {
        final Task task = taskRepository.findByIdAndProject_Id(taskId, projectId)
                .orElseThrow(EntityNotFoundException::new);
        converter.update(updateTask, task);
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(long projectId, long taskId) {
        taskRepository.deleteByIdAndProject_Id(taskId, projectId);
    }

    @Override
    public void updateTaskLabels(long projectId, long taskId, List<Long> labelsId) {
        final Task task = taskRepository.findByIdAndProject_Id(taskId, projectId)
                .orElseThrow(EntityNotFoundException::new);
        List<Label> labels = labelRepository.findAllById(labelsId);
        task.setLabels(Set.copyOf(labels));
        taskRepository.save(task);
    }

    @Override
    public void updateTaskAssigned(long projectId, long taskId, List<String> emails) throws InvalidPerformerException {
        final Task task = taskRepository.findByIdAndProject_Id(taskId, projectId)
                .orElseThrow(EntityNotFoundException::new);
        Set<User> users = userRepository.findAllByEmailIn(emails);
        boolean canPerforms = users
                .stream()
                .allMatch(u -> permissionService.canPerform(u.getId(), task.getProject().getId()));
        if (!canPerforms) {
            throw new InvalidPerformerException("Permission denied");
        }
        task.setAssigned(users);
        taskRepository.save(task);
    }

    @Override
    public void deleteTaskAssigned(long projectId, long taskId, List<String> emails) {
        final Task task = taskRepository.findByIdAndProject_Id(taskId, projectId)
                .orElseThrow(EntityNotFoundException::new);
        task.getAssigned().removeIf(u -> emails.contains(u.getEmail()));
        taskRepository.save(task);
    }
}
