package com.coworkingspace.server.services;

import com.coworkingspace.server.DTOs.BookingDTO;
import com.coworkingspace.server.DTOs.MissionDTO;

import java.util.List;

public interface FreelancerService {
    MissionDTO createMission(Long freelancerId, MissionDTO dto);
    MissionDTO updateMission(Long missionId, MissionDTO dto);
    void deleteMission(Long missionId);
    List<MissionDTO> getMissions(Long freelancerId);

    BookingDTO createBooking(Long freelancerId, BookingDTO bookingDTO);
    List<BookingDTO> getBookings(Long freelancerId);
}

