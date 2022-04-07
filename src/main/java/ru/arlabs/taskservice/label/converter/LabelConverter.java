package ru.arlabs.taskservice.label.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import ru.arlabs.taskservice.label.model.Label;
import ru.arlabs.taskservice.label.request.NewLabel;
import ru.arlabs.taskservice.label.request.UpdateLabel;
import ru.arlabs.taskservice.label.response.LabelInfo;

/**
 * @author Jeb
 */
@Mapper(componentModel = "spring")
public interface LabelConverter {

    LabelInfo convertToInfo(Label label);

    @Mappings({
            @Mapping(target = "project", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Label convertNew(NewLabel newProject);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "project", ignore = true)
    })
    void update(UpdateLabel updateProject, @MappingTarget Label project);
}
