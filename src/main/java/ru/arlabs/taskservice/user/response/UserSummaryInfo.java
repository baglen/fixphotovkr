package ru.arlabs.taskservice.user.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.arlabs.taskservice.user.model.User;

@Data
@NoArgsConstructor
public class UserSummaryInfo {
    private String email;
    private String username;

    public UserSummaryInfo(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
