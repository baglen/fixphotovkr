package ru.arlabs.taskservice.user.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.arlabs.taskservice.user.model.User;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :login OR u.phone = :login")
    Optional<User> findByLogin(String login);

    Optional<User> findByEmail(String email);

    Set<User> findAllByEmailIn(Iterable<String> emails);

    boolean existsByEmailOrPhoneAndEnabledTrue(String email, String phone);

    boolean existsByEmail(String email);
}