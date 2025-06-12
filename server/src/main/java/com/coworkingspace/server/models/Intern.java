package com.coworkingspace.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "interns")
public class Intern extends User {

    @JsonBackReference // Prevent serialization of freelancer's missions again
    @OneToMany(mappedBy = "intern", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lecture> lectures = new ArrayList<>();

    @JsonBackReference // Prevent serialization of freelancer's missions again
    @OneToMany(mappedBy = "intern", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
    @PrePersist
    public void prePersist() {
        this.setRole(Role.INTERN);
        this.setEnabled(false);
    }
}
