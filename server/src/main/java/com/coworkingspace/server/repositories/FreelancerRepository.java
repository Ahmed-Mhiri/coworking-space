package com.coworkingspace.server.repositories;

import com.coworkingspace.server.models.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Optional<Freelancer> findByUsername(String username);


    Optional<Freelancer> findByEmail(String email);

}
