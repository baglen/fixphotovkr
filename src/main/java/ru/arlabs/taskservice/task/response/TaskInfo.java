package ru.arlabs.taskservice.task.response;

import lombok.Data;
import ru.arlabs.taskservice.label.response.LabelInfo;
import ru.arlabs.taskservice.task.model.Task;
import ru.arlabs.taskservice.user.response.UserSummaryInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TaskInfo {
    private long id;
    private String name;
    private String description;
    private Repeat repeat;
    private UserSummaryInfo owner;
    private List<UserSummaryInfo> assigned;
    private List<LabelInfo> labels;
    private LocalDateTime createdAt;

    public TaskInfo(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        if (task.getRepeatUnit() != null) {
            this.repeat = new Repeat(task.getRepeatUnit(), task.getRepeatDelay());
        }
        this.owner = new UserSummaryInfo(task.getOwner());
        this.assigned = task.getAssigned()
                .stream()
                .map(UserSummaryInfo::new)
                .collect(Collectors.toList());
        this.labels = task.getLabels()
                .stream()
                .map(LabelInfo::new)
                .collect(Collectors.toList());
        this.createdAt = task.getCreatedAt();

    }


}
