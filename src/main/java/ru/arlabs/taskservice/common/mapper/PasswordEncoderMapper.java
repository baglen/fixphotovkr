package ru.arlabs.taskservice.common.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Jeb
 */
@Component
public class PasswordEncoderMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordEncoderMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @EncodedMapping
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }

    public Boolean matches(String password, String encoded){
        return passwordEncoder.matches(password, encoded);
    }

}