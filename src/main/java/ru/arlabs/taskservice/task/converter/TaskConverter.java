package ru.arlabs.taskservice.task.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import ru.arlabs.taskservice.label.model.Label;
import ru.arlabs.taskservice.label.request.NewLabel;
import ru.arlabs.taskservice.task.model.Task;
import ru.arlabs.taskservice.task.request.NewTask;
import ru.arlabs.taskservice.task.request.UpdateTask;

@Mapper(componentModel = "spring")
public interface TaskConverter {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "repeatDelay", source = "repeat.delay"),
            @Mapping(target = "repeatUnit", source = "repeat.unit"),
            @Mapping(target = "assigned", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "labels", ignore = true),
            @Mapping(target = "owner", ignore = true),
            @Mapping(target = "project", ignore = true),
            @Mapping(target = "status", ignore = true)
    })
    Task convertNew(NewTask newProject);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "repeatDelay", source = "repeat.delay"),
            @Mapping(target = "repeatUnit", source = "repeat.unit"),
            @Mapping(target = "assigned", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "labels", ignore = true),
            @Mapping(target = "owner", ignore = true),
            @Mapping(target = "project", ignore = true),
            @Mapping(target = "status", ignore = true)
    })
    void update(UpdateTask updateTask, @MappingTarget Task task);
}
