package com.coworkingspace.server.mappers;

import com.coworkingspace.server.DTOs.LectureDTO;
import com.coworkingspace.server.models.Lecture;
import com.coworkingspace.server.models.ProgressStatus;
import org.springframework.stereotype.Component;

@Component
public class LectureMapper {

    public LectureDTO toDTO(Lecture lecture) {
        if (lecture == null) return null;

        LectureDTO dto = new LectureDTO();
        dto.setId(lecture.getId());
        dto.setName(lecture.getName());
        dto.setProgress(lecture.getProgress().name());
        dto.setFiles(lecture.getFiles());
        dto.setDescription(lecture.getDescription());  // <-- ADD THIS

        return dto;
    }

    public Lecture toEntity(LectureDTO dto) {
        if (dto == null) return null;

        Lecture lecture = new Lecture();
        lecture.setId(dto.getId());
        lecture.setName(dto.getName());
        lecture.setProgress(ProgressStatus.valueOf(dto.getProgress()));
        lecture.setDescription(dto.getDescription());  // <-- ADD THIS

        lecture.setFiles(dto.getFiles());
        return lecture;
    }
}
