package com.coworkingspace.server.mappers;

import com.coworkingspace.server.DTOs.BookingDTO;
import com.coworkingspace.server.models.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    private final RoomMapper roomMapper;

    public BookingMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    public BookingDTO toDTO(Booking booking) {
        if (booking == null) return null;

        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setDate(booking.getDate());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setRoom(roomMapper.toDTO(booking.getRoom()));
        dto.setAmountPaid(booking.getAmountPaid());
        dto.setPaid(booking.isPaid());

        if (booking.getFreelancer() != null) {
            dto.setFreelancerId(booking.getFreelancer().getId());
        }

        if (booking.getIntern() != null) {
            dto.setInternId(booking.getIntern().getId());
        }

        return dto;
    }

    public Booking toEntity(BookingDTO dto) {
        if (dto == null) return null;

        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setDate(dto.getDate());
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setRoom(roomMapper.toEntity(dto.getRoom()));
        booking.setAmountPaid(dto.getAmountPaid());
        booking.setPaid(dto.isPaid());
        // Set freelancer and intern via service or repo in controller/service layer
        return booking;
    }
}
