package ru.arlabs.taskservice.security.converter;

import org.mapstruct.Mapper;
import ru.arlabs.taskservice.security.request.AuthorizedUser;
import ru.arlabs.taskservice.user.model.User;

/**
 * @author Jeb
 */
@Mapper(componentModel = "spring")
public interface SecurityConverter {
    AuthorizedUser convertUser(User user);
}
