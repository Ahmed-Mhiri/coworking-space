package com.coworkingspace.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProgressStatus progress;

    private String description; // New field added

    @ElementCollection
    private List<String> files; // URLs or file paths

    @ManyToOne
    @JoinColumn(name = "intern_id")
    @JsonBackReference // Prevent serialization of freelancer's missions again
    private Intern intern;
}
