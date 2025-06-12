package com.coworkingspace.server.mappers;

import com.coworkingspace.server.DTOs.InternDTO;
import com.coworkingspace.server.models.Intern;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class InternMapper {

    private final LectureMapper lectureMapper;
    private final BookingMapper bookingMapper;

    public InternMapper(LectureMapper lectureMapper, BookingMapper bookingMapper) {
        this.lectureMapper = lectureMapper;
        this.bookingMapper = bookingMapper;
    }

    public InternDTO toDTO(Intern intern) {
        if (intern == null) return null;

        InternDTO dto = new InternDTO();
        dto.setId(intern.getId());
        dto.setUsername(intern.getUsername());
        dto.setEmail(intern.getEmail());

        if (intern.getLectures() != null) {
            dto.setLectures(
                    intern.getLectures().stream()
                            .map(lectureMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }

        if (intern.getBookings() != null) {
            dto.setBookings(
                    intern.getBookings().stream()
                            .map(bookingMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Intern toEntity(InternDTO dto) {
        if (dto == null) return null;

        Intern intern = new Intern();
        intern.setId(dto.getId());
        intern.setUsername(dto.getUsername());
        intern.setEmail(dto.getEmail());

        if (dto.getLectures() != null) {
            intern.setLectures(
                    dto.getLectures().stream()
                            .map(lectureMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }

        if (dto.getBookings() != null) {
            intern.setBookings(
                    dto.getBookings().stream()
                            .map(bookingMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }

        return intern;
    }
}
