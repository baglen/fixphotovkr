package ru.arlabs.taskservice.user.response;

import lombok.Data;
import ru.arlabs.taskservice.security.request.AuthorizedUser;

/**
 * @author Jeb
 */
@Data
public class UserInfo {
    private String email;

    private String phone;

    private String username;

    public UserInfo(AuthorizedUser user) {
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.username = user.getUsername();
    }
}
