package ru.arlabs.taskservice.task.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.temporal.ChronoUnit;

@Data
public class Repeat {
    private ChronoUnit unit;
    private int delay;

    @JsonCreator
    public Repeat(@JsonProperty(required = true) ChronoUnit unit,
                  @JsonProperty(required = true) int delay) {
        this.unit = unit;
        this.delay = delay;
    }
}
