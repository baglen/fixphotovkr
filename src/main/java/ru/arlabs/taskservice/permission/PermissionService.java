package ru.arlabs.taskservice.permission;

import org.springframework.transaction.annotation.Transactional;
import ru.arlabs.taskservice.permission.request.UpdatePermission;
import ru.arlabs.taskservice.permission.response.UserPermissionInfo;

import java.util.List;

/**
 * @author Jeb
 */
public interface PermissionService {
    boolean canRead(long userId, long projectId);

    boolean canWrite(long userId, long projectId);

    boolean canPerform(long userId, long projectId);

    List<UserPermissionInfo> getPermissions(long userId, long projectId);

    @Transactional
    void setPermission(long userId, long projectId, UpdatePermission updatePermission);
}
