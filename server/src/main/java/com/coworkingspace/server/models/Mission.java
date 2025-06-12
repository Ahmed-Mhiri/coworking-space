package com.coworkingspace.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "missions")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String deadline; // consider using LocalDate

    private String description; // New field added

    @Enumerated(EnumType.STRING)
    private ProgressStatus progress;

    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    @JsonBackReference // Prevent serialization of freelancer's missions again
    private Freelancer freelancer;
}

