package com.finances.finance_control.repository.user;

import com.finances.finance_control.entity.user.Email;
import com.finances.finance_control.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(Email email);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.email) = LOWER(:emailOrUsername) OR " +
            "LOWER(u.username) = LOWER(:emailOrUsername)")
    Optional<User> findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
}