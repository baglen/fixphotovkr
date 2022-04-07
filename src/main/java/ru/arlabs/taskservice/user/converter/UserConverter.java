package ru.arlabs.taskservice.user.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.core.convert.converter.Converter;
import ru.arlabs.taskservice.common.mapper.EncodedMapping;
import ru.arlabs.taskservice.common.mapper.PasswordEncoderMapper;
import ru.arlabs.taskservice.security.request.RegisterInfo;
import ru.arlabs.taskservice.user.model.User;
import ru.arlabs.taskservice.user.request.UpdateUser;

import javax.validation.constraints.NotNull;

/**
 * @author Jeb
 */
@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserConverter extends Converter<RegisterInfo, User> {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class),
            @Mapping(target = "enabled", ignore = true),
            @Mapping(target = "authorities", ignore = true)
    })
    User convert(RegisterInfo source);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class),
            @Mapping(target = "enabled", ignore = true),
            @Mapping(target = "authorities", ignore = true)
    })
    void updateRegister(RegisterInfo source, @MappingTarget User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "enabled", ignore = true),
            @Mapping(target = "authorities", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "phone", ignore = true)
    })
    void updateUser(UpdateUser updateUser, @MappingTarget User user);
}
