package ru.arlabs.taskservice.project.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jeb
 */
@Data
public class NewProject {
    private String name;
    private String code;
    private Long typeId;

    @JsonCreator
    public NewProject(@JsonProperty(required = true) String name,
                      @JsonProperty(required = true) String code,
                      @JsonProperty(required = true) Long typeId) {
        this.name = name;
        this.code = code;
        this.typeId = typeId;
    }
}
