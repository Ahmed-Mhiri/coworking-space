package com.coworkingspace.server.repositories;

import com.coworkingspace.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByConfirmationToken(String confirmationToken);

    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
}
