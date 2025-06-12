package com.coworkingspace.server.Controllers;

import com.coworkingspace.server.DTOs.LectureDTO;
import com.coworkingspace.server.services.LectureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping
    public ResponseEntity<LectureDTO> createLecture(@RequestBody LectureDTO dto) {
        return ResponseEntity.ok(lectureService.createLecture(dto));
    }

    @GetMapping
    public ResponseEntity<List<LectureDTO>> getLectures() {
        return ResponseEntity.ok(lectureService.getLecturesForIntern());
    }

    @PutMapping("/{lectureId}")
    public ResponseEntity<LectureDTO> updateLecture(@PathVariable Long lectureId, @RequestBody LectureDTO dto) {
        return ResponseEntity.ok(lectureService.updateLecture(lectureId, dto));
    }

    @DeleteMapping("/{lectureId}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Long lectureId) {
        lectureService.deleteLecture(lectureId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{lectureId}/files")
    public ResponseEntity<Void> uploadLectureFiles(
            @PathVariable Long lectureId,
            @RequestParam("files") MultipartFile[] files) {
        lectureService.uploadFilesToLecture(lectureId, files);
        return ResponseEntity.ok().build();
    }
}
