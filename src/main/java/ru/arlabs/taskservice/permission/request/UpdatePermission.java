package ru.arlabs.taskservice.permission.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.arlabs.taskservice.permission.model.Permission;

/**
 * @author Jeb
 */
@Data
public class UpdatePermission {
    private String email;
    private Permission permission;

    @JsonCreator
    public UpdatePermission(@JsonProperty(required = true) String email,
                            @JsonProperty(required = true) Permission permission) {
        this.email = email;
        this.permission = permission;
    }
}
