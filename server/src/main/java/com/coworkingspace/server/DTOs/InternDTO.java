package com.coworkingspace.server.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class InternDTO extends UserDTO {
    private List<LectureDTO> lectures;
    private List<BookingDTO> bookings;
}
