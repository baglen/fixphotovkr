package ru.arlabs.taskservice.project.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author Jeb
 */
@Data
public class ProjectSummaryInfo {
    private Long id;
    private String name;
    private String code;
    private boolean favorite;
    private String avatar;
    @JsonIgnore
    private String typeName;
    @JsonIgnore
    private String ownerName;

    @JsonProperty("owner")
    public Map<String, Object> getOwner() {
        return Map.of("username", ownerName);
    }
    @JsonProperty("type")
    public Map<String, Object> getType() {
        if (typeName == null) {
            return null;
        }
        return Map.of("name", typeName);
    }
}
