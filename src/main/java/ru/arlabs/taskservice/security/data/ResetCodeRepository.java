package ru.arlabs.taskservice.security.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arlabs.taskservice.security.model.ResetCode;

import java.util.Optional;

public interface ResetCodeRepository extends JpaRepository<ResetCode, Long> {
    Optional<ResetCode> findByCode(String code);
}