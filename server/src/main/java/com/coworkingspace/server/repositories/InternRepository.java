package com.coworkingspace.server.repositories;

import com.coworkingspace.server.models.Intern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternRepository extends JpaRepository<Intern, Long> {
    Optional<Intern> findByEmail(String email);

}
