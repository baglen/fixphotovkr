package ru.arlabs.taskservice.task.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.arlabs.taskservice.task.response.Repeat;

@Data
public class UpdateTask {
    private String name;
    private String description;
    private Repeat repeat;

    @JsonCreator
    public UpdateTask(@JsonProperty(required = true) String name,
                      @JsonProperty(required = true) String description,
                      @JsonProperty(required = true) Repeat repeat) {
        this.name = name;
        this.description = description;
        this.repeat = repeat;
    }
}
