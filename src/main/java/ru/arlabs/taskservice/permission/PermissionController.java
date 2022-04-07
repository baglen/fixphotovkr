package ru.arlabs.taskservice.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.arlabs.taskservice.permission.request.UpdatePermission;
import ru.arlabs.taskservice.permission.response.UserPermissionInfo;
import ru.arlabs.taskservice.security.request.AuthorizedUser;
import ru.arlabs.taskservice.user.UserService;

import java.util.List;

/**
 * @author Jeb
 */
@RestController
@RequestMapping("/projects/{id}/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public List<UserPermissionInfo> getPermissions(@AuthenticationPrincipal AuthorizedUser user,
                                                   @PathVariable long id) {
        return permissionService.getPermissions(user.getId(), id);
    }

    @PostMapping
    public void updatePermission(@AuthenticationPrincipal AuthorizedUser user,
                                 @PathVariable long id,
                                 @RequestBody UpdatePermission updatePermission) {
        permissionService.setPermission(user.getId(), id, updatePermission);
    }
}
