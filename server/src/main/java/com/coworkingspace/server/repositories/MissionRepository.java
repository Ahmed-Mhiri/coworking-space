package com.coworkingspace.server.repositories;

import com.coworkingspace.server.models.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByFreelancerId(Long freelancerId);
}
