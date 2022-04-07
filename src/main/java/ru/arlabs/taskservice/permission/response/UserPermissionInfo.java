package ru.arlabs.taskservice.permission.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.arlabs.taskservice.permission.model.Permission;
import ru.arlabs.taskservice.permission.model.ProjectPermission;

import java.util.Map;

/**
 * @author Jeb
 */
@Data
public class UserPermissionInfo {
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private boolean active;
    private Permission permission;

    public UserPermissionInfo(ProjectPermission permission) {
        this.permission = permission.getPermission();
        this.username = permission.getUser().getUsername();
        this.email = permission.getUser().getEmail();
        this.active = permission.getUser().isEnabled();
    }

    @JsonProperty("user")
    public Map<String, Object> getUser() {
        return Map.of("username", username,
                "email", email,
                "active", active);
    }


}
