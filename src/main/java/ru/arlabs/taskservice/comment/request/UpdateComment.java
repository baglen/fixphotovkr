package ru.arlabs.taskservice.comment.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateComment {
    private String text;

    @JsonCreator
    public UpdateComment(@JsonProperty(required = true) String text) {
        this.text = text;
    }
}
