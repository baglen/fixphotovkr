package ru.arlabs.taskservice.project.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class UpdateProject {
    private String name;
    private Long typeId;

    @JsonCreator
    public UpdateProject(@JsonProperty(required = true) String name,
                         @JsonProperty(required = true) Long typeId) {
        this.name = name;
        this.typeId = typeId;
    }
}
