package ru.arlabs.taskservice.project.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class UpdateProjectType {

    private String name;

    private String description;

    @JsonCreator
    public UpdateProjectType(@JsonProperty(required = true) String name,
                             @JsonProperty(required = true) String description) {
        this.name = name;
        this.description = description;
    }
}
