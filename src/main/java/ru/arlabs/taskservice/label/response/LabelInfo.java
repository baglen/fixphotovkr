package ru.arlabs.taskservice.label.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.arlabs.taskservice.label.model.Label;

/**
 * @author Jeb
 */
@Data
@NoArgsConstructor
public class LabelInfo {
    private Long id;
    private String name;
    private String color;
    private String description;

    public LabelInfo(Label label) {
        this.id = label.getId();
        this.name = label.getName();
        this.color = label.getColor();
        this.description = label.getDescription();
    }
}
