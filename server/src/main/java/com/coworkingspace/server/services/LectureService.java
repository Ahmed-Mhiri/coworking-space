package com.coworkingspace.server.services;

import com.coworkingspace.server.DTOs.LectureDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LectureService {
    LectureDTO createLecture(LectureDTO lectureDTO);
    LectureDTO updateLecture(Long lectureId, LectureDTO lectureDTO);
    void deleteLecture(Long lectureId);
    List<LectureDTO> getLecturesForIntern();
    void uploadFilesToLecture(Long lectureId, MultipartFile[] files);
}
