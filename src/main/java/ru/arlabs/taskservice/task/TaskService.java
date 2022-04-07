package ru.arlabs.taskservice.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.arlabs.taskservice.comment.response.AttachmentInfo;
import ru.arlabs.taskservice.task.exception.InvalidPerformerException;
import ru.arlabs.taskservice.task.request.NewTask;
import ru.arlabs.taskservice.task.request.UpdateTask;
import ru.arlabs.taskservice.task.response.TaskInfo;

import javax.transaction.Transactional;
import java.util.List;

public interface TaskService {
    @PreAuthorize("@permissionService.canRead(authentication.principal.id, #projectId)")
    Page<TaskInfo> getTasks(long projectId, Pageable pageable);

    @Transactional
    @PreAuthorize("@permissionService.canWrite(authentication.principal.id, #projectId)")
    void createTask(long userId, long projectId, NewTask newTask);

    @Transactional
    @PreAuthorize("@permissionService.canWrite(authentication.principal.id, #projectId)")
    void updateTask(long projectId, long taskId, UpdateTask updateTask);

    @Transactional
    @PreAuthorize("@permissionService.canWrite(authentication.principal.id, #projectId)")
    void updateTaskLabels(long projectId, long taskId, List<Long> labelsId);

    @Transactional
    @PreAuthorize("@permissionService.canWrite(authentication.principal.id, #projectId)")
    void updateTaskAssigned(long projectId, long taskId, List<String> emails) throws InvalidPerformerException;


    @Transactional
    @PreAuthorize("@permissionService.canWrite(authentication.principal.id, #projectId)")
    void deleteTaskAssigned(long projectId, long taskId, List<String> emails) throws InvalidPerformerException;

    @Transactional
    @PreAuthorize("@permissionService.canWrite(authentication.principal.id, #projectId)")
    void deleteTask(long projectId, long taskId);
}
