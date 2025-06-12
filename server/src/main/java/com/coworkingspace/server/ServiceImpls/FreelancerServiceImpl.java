package com.coworkingspace.server.ServiceImpls;

import com.coworkingspace.server.DTOs.BookingDTO;
import com.coworkingspace.server.DTOs.MissionDTO;
import com.coworkingspace.server.services.BookingService;
import com.coworkingspace.server.services.FreelancerService;
import com.coworkingspace.server.services.MissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreelancerServiceImpl implements FreelancerService {

    private final MissionService missionService; // reuse MissionService if exists
    private final BookingService bookingService; // reuse BookingService if exists

    public FreelancerServiceImpl(MissionService missionService, BookingService bookingService) {
        this.missionService = missionService;
        this.bookingService = bookingService;
    }

    @Override
    public MissionDTO createMission(Long freelancerId, MissionDTO dto) {
        return missionService.createMission(dto);
    }

    @Override
    public MissionDTO updateMission(Long missionId, MissionDTO dto) {
        return missionService.updateMission(missionId, dto);
    }

    @Override
    public void deleteMission(Long missionId) {
        missionService.deleteMission(missionId);
    }

    @Override
    public List<MissionDTO> getMissions(Long freelancerId) {
        return missionService.getMissionsForFreelancer();
    }

    @Override
    public BookingDTO createBooking(Long freelancerId, BookingDTO bookingDTO) {
        return bookingService.createBooking(freelancerId, true, bookingDTO);
    }

    @Override
    public List<BookingDTO> getBookings(Long freelancerId) {
        return bookingService.getBookingsForUser(freelancerId);
    }
}
