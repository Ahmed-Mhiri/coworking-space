package com.coworkingspace.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "freelancers")
public class Freelancer extends User {
    @JsonBackReference // Prevent serialization of freelancer's missions again
    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mission> missions = new ArrayList<>();
    @JsonBackReference // Prevent serialization of freelancer's missions again
    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
    @PrePersist
    public void prePersist() {
        this.setRole(Role.FREELANCER);
        this.setEnabled(false);
    }


}
