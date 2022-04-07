package ru.arlabs.taskservice.security.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arlabs.taskservice.security.model.VerifyCode;

import java.util.Optional;

public interface VerifyCodeRepository extends JpaRepository<VerifyCode, Long> {
    Optional<VerifyCode> findByCode(String code);
}