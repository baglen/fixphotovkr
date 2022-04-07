package ru.arlabs.taskservice.user;

import ru.arlabs.taskservice.common.exception.InvalidPasswordException;
import ru.arlabs.taskservice.common.exception.ObjectAlreadyExistException;
import ru.arlabs.taskservice.security.request.RegisterInfo;
import ru.arlabs.taskservice.user.model.User;
import ru.arlabs.taskservice.user.request.UpdateUser;

/**
 * @author Jeb
 */
public interface UserService {
    void register(RegisterInfo request) throws ObjectAlreadyExistException;

    User inviteUser(String email);

    void changePassword(long userId, String password);

    void changePassword(long userId, String oldPassword, String newPassword) throws InvalidPasswordException;

    void update(long userId, UpdateUser updateUser);
}
