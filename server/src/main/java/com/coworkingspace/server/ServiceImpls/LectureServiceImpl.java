package com.coworkingspace.server.ServiceImpls;

import com.coworkingspace.server.DTOs.LectureDTO;
import com.coworkingspace.server.mappers.LectureMapper;
import com.coworkingspace.server.models.Intern;
import com.coworkingspace.server.models.Lecture;
import com.coworkingspace.server.models.ProgressStatus;
import com.coworkingspace.server.repositories.InternRepository;
import com.coworkingspace.server.repositories.LectureRepository;
import com.coworkingspace.server.services.LectureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final InternRepository internRepository;
    private final LectureMapper lectureMapper;

    @Value("${file.upload-dir}")
    private String uploadDirRoot;

    public LectureServiceImpl(LectureRepository lectureRepository,
                              InternRepository internRepository,
                              LectureMapper lectureMapper) {
        this.lectureRepository = lectureRepository;
        this.internRepository = internRepository;
        this.lectureMapper = lectureMapper;
    }

    private Intern getCurrentIntern() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Unauthenticated request");
        }
        String email = authentication.getName();
        System.out.println("🔐 Authenticated intern email: " + email);
        return internRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Intern not found: " + email));
    }

    @Override
    public LectureDTO createLecture(LectureDTO dto) {
        Lecture lecture = lectureMapper.toEntity(dto);
        Intern intern = getCurrentIntern();
        lecture.setIntern(intern);
        // Make sure description is set from dto (usually handled in mapper)
        lecture.setDescription(dto.getDescription());
        return lectureMapper.toDTO(lectureRepository.saveAndFlush(lecture));
    }

    @Override
    public LectureDTO updateLecture(Long lectureId, LectureDTO dto) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        lecture.setName(dto.getName());
        lecture.setProgress(ProgressStatus.valueOf(dto.getProgress()));

        // Set description from DTO
        lecture.setDescription(dto.getDescription());

        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            lecture.setFiles(dto.getFiles());
        }

        return lectureMapper.toDTO(lectureRepository.save(lecture));
    }

    @Override
    public void deleteLecture(Long lectureId) {
        lectureRepository.deleteById(lectureId);
    }

    @Override
    public List<LectureDTO> getLecturesForIntern() {
        Intern intern = getCurrentIntern();
        return lectureRepository.findByInternId(intern.getId())
                .stream().map(lectureMapper::toDTO).toList();
    }

    @Override
    public void uploadFilesToLecture(Long lectureId, MultipartFile[] files) {
        String uploadDir = uploadDirRoot + "/lectures/" + lectureId;
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        List<String> filePaths = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String originalFilename = file.getOriginalFilename();
                    String uniqueName = UUID.randomUUID() + "_" + originalFilename;
                    String path = uploadDir + "/" + uniqueName;
                    File destination = new File(path);
                    file.transferTo(destination);

                    filePaths.add(path);

                } catch (IOException e) {
                    throw new RuntimeException("Failed to store file", e);
                }
            }
        }

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        if (lecture.getFiles() == null) {
            lecture.setFiles(new ArrayList<>());
        }
        lecture.getFiles().addAll(filePaths);

        lectureRepository.save(lecture);
    }
}

