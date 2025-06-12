package com.coworkingspace.server.DTOs;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDTO {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private RoomDTO room;
    private Long freelancerId;
    private Long internId;
    private double amountPaid;
    private boolean paid;
}
