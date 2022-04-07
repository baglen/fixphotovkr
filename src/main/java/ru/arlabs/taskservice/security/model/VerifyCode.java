package ru.arlabs.taskservice.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.arlabs.taskservice.user.model.User;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "verify_codes", schema = "public")
@NoArgsConstructor
public class VerifyCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "code")
    private String code;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public VerifyCode(User user) {
        this.user = user;
        this.code = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now(Clock.systemUTC());
    }
}