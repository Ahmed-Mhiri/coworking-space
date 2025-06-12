package com.coworkingspace.server.Controllers;

import com.coworkingspace.server.DTOs.BookingDTO;
import com.coworkingspace.server.DTOs.RoomDTO;
import com.coworkingspace.server.models.Room;
import com.coworkingspace.server.services.BookingService;
import com.coworkingspace.server.services.RoomService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final RoomService roomService;

    public BookingController(BookingService bookingService,RoomService roomService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    // Create booking for a user (freelancer or intern)
    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createBooking(
            @PathVariable Long userId,
            @RequestParam boolean isFreelancer,
            @Valid @RequestBody BookingDTO dto) {

        // Validate mandatory fields
        if (dto.getDate() == null
                || dto.getStartTime() == null
                || dto.getEndTime() == null
                || dto.getRoom() == null
                || dto.getRoom().getId() == null) {
            return ResponseEntity.badRequest().body("Date, startTime, endTime, and roomId must be provided");
        }

        // Validate time logic
        if (!dto.getStartTime().isBefore(dto.getEndTime())) {
            return ResponseEntity.badRequest().body("startTime must be before endTime");
        }

        // Check if room is available
        boolean available = bookingService.checkRoomAvailability(
                dto.getRoom().getId(),
                dto.getDate(),
                dto.getStartTime(),
                dto.getEndTime());

        if (!available) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Room is not available for the selected time slot");
        }

        // Calculate price (assuming Room has constructor Room(Long id))
        Room room = roomService.getRoomEntityById(dto.getRoom().getId());
        double price = bookingService.calculatePrice(room, dto.getStartTime(), dto.getEndTime());
        dto.setAmountPaid(price);

        // Create the booking
        BookingDTO createdBooking = bookingService.createBooking(userId, isFreelancer, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingsForUser(@PathVariable Long userId) {
        List<BookingDTO> bookings = bookingService.getBookingsForUser(userId);
        return ResponseEntity.ok(bookings);
    }
    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomDTO>> getAvailableRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {

        if (!startTime.isBefore(endTime)) {
            return ResponseEntity.badRequest().body(null); // or error message
        }

        List<RoomDTO> availableRooms = bookingService.getAvailableRooms(date, startTime, endTime);

        return ResponseEntity.ok(availableRooms);
    }
}
