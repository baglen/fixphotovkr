package ru.arlabs.taskservice.label.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class UpdateLabel {
    private String name;
    private String color;
    private String description;

    @JsonCreator
    public UpdateLabel(@JsonProperty(required = true) String name,
                    @JsonProperty(required = true) String color,
                    @JsonProperty(required = true) String description) {
        this.name = name;
        this.color = color;
        this.description = description;
    }
}
