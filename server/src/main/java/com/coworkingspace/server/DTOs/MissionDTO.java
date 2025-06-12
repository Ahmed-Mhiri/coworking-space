package com.coworkingspace.server.DTOs;

import lombok.Data;

@Data
public class MissionDTO {
    private Long id;
    private String name;
    private String deadline;
    private String progress; // Enum as string
    private String description; // New field added
}
