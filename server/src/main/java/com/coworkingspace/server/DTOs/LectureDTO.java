package com.coworkingspace.server.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class LectureDTO {
    private Long id;
    private String name;
    private String progress;  // Enum as string
    private List<String> files;
    private String description; // New field added
}
