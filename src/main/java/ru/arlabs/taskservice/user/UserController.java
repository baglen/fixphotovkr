package ru.arlabs.taskservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arlabs.taskservice.common.exception.InvalidPasswordException;
import ru.arlabs.taskservice.security.request.AuthorizedUser;
import ru.arlabs.taskservice.user.request.UpdateUser;
import ru.arlabs.taskservice.user.request.UpdateUserPassword;
import ru.arlabs.taskservice.user.response.UserInfo;

/**
 * @author Jeb
 */
@RestController("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserInfo get(@AuthenticationPrincipal AuthorizedUser user) {
        return new UserInfo(user);
    }

    @PostMapping
    public void update(@AuthenticationPrincipal AuthorizedUser user, @RequestBody UpdateUser updateUser) {
        userService.update(user.getId(), updateUser);
    }

    @PostMapping("/changePassword")
    public void updatePassword(@AuthenticationPrincipal AuthorizedUser user, @RequestBody UpdateUserPassword updateUser) throws InvalidPasswordException {
        userService.changePassword(user.getId(), updateUser.getOldPassword(), updateUser.getPassword());
    }
}
