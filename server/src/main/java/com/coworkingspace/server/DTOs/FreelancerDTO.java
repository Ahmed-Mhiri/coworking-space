package com.coworkingspace.server.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class FreelancerDTO extends UserDTO {
    private List<MissionDTO> missions;
    private List<BookingDTO> bookings;
}
