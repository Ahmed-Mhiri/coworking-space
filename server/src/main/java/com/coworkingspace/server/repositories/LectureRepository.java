package com.coworkingspace.server.repositories;

import com.coworkingspace.server.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByInternId(Long internId);
}
