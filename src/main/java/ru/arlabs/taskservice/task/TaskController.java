package ru.arlabs.taskservice.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.arlabs.taskservice.security.request.AuthorizedUser;
import ru.arlabs.taskservice.task.exception.InvalidPerformerException;
import ru.arlabs.taskservice.task.request.NewTask;
import ru.arlabs.taskservice.task.request.UpdateTask;
import ru.arlabs.taskservice.task.response.TaskInfo;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Page<TaskInfo> getTasks(@PathVariable long projectId,
                                   @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return taskService.getTasks(projectId, pageable);
    }

    @PostMapping
    public void createTask(@AuthenticationPrincipal AuthorizedUser user,
                           @PathVariable long projectId,
                           @Valid @RequestBody NewTask newTask) {
        taskService.createTask(user.getId(), projectId, newTask);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable long projectId,
                           @PathVariable long id,
                           @Valid @RequestBody UpdateTask updateTask) {
        taskService.updateTask(projectId, id, updateTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long projectId,
                           @PathVariable long id) {
        taskService.deleteTask(projectId, id);
    }

    @PostMapping("/{id}/labels")
    public void updateTaskLabels(@PathVariable long projectId,
                                 @PathVariable long id,
                                 @Valid @RequestBody List<Long> labelIds) {
        taskService.updateTaskLabels(projectId, id, labelIds);
    }

    @PostMapping("/{id}/assign")
    public void updateTaskAssigned(@PathVariable long projectId,
                                   @PathVariable long id,
                                   @Valid @RequestBody List<String> emails) throws InvalidPerformerException {
        taskService.updateTaskAssigned(projectId, id, emails);
    }

    @DeleteMapping("/{id}/assign")
    public void deleteTaskAssigned(@PathVariable long projectId,
                                   @PathVariable long id,
                                   @Valid @RequestBody List<String> emails) throws InvalidPerformerException {
        taskService.deleteTaskAssigned(projectId, id, emails);
    }
}
