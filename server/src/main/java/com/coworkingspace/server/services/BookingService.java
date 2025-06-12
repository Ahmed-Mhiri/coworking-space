package com.coworkingspace.server.services;

import com.coworkingspace.server.DTOs.BookingDTO;
import com.coworkingspace.server.DTOs.RoomDTO;
import com.coworkingspace.server.models.Room;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingService {
    BookingDTO createBooking(Long userId, boolean isFreelancer, BookingDTO bookingDTO);
    boolean checkRoomAvailability(Long roomId, LocalDate date, LocalTime from, LocalTime to);
    double calculatePrice(Room room, LocalTime from, LocalTime to);
    List<BookingDTO> getBookingsForUser(Long userId);
    List<RoomDTO> getAvailableRooms(LocalDate date, LocalTime startTime, LocalTime endTime);


}
