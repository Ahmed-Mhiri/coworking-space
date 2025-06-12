package com.coworkingspace.server.mappers;

import com.coworkingspace.server.DTOs.FreelancerDTO;
import com.coworkingspace.server.models.Freelancer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FreelancerMapper {

    private final MissionMapper missionMapper;
    private final BookingMapper bookingMapper;

    public FreelancerMapper(MissionMapper missionMapper, BookingMapper bookingMapper) {
        this.missionMapper = missionMapper;
        this.bookingMapper = bookingMapper;
    }

    public FreelancerDTO toDTO(Freelancer freelancer) {
        if (freelancer == null) return null;

        FreelancerDTO dto = new FreelancerDTO();
        dto.setId(freelancer.getId());
        dto.setUsername(freelancer.getUsername());
        dto.setEmail(freelancer.getEmail());

        if (freelancer.getMissions() != null) {
            dto.setMissions(
                    freelancer.getMissions().stream()
                            .map(missionMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }

        if (freelancer.getBookings() != null) {
            dto.setBookings(
                    freelancer.getBookings().stream()
                            .map(bookingMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Freelancer toEntity(FreelancerDTO dto) {
        if (dto == null) return null;

        Freelancer freelancer = new Freelancer();
        freelancer.setId(dto.getId());
        freelancer.setUsername(dto.getUsername());
        freelancer.setEmail(dto.getEmail());

        if (dto.getMissions() != null) {
            freelancer.setMissions(
                    dto.getMissions().stream()
                            .map(missionMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }

        if (dto.getBookings() != null) {
            freelancer.setBookings(
                    dto.getBookings().stream()
                            .map(bookingMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }

        return freelancer;
    }
}
