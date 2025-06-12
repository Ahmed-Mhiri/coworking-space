package com.coworkingspace.server.ServiceImpls;

import com.coworkingspace.server.DTOs.RoomDTO;

import com.coworkingspace.server.repositories.BookingRepository;
import com.coworkingspace.server.services.AdminService;
import com.coworkingspace.server.services.AdminStatsService;
import com.coworkingspace.server.services.RoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    private final RoomService roomService;
    private final BookingRepository bookingRepository;

    // Inject both dependencies via constructor
    public AdminServiceImpl(RoomService roomService, BookingRepository bookingRepository) {
        this.roomService = roomService;
        this.bookingRepository = bookingRepository;
    }

    // Room management delegated to RoomService
    @Override
    public RoomDTO createRoom(RoomDTO dto) {
        return roomService.createRoom(dto);
    }

    @Override
    public RoomDTO updateRoom(Long roomId, RoomDTO dto) {
        return roomService.updateRoom(roomId, dto);
    }

    @Override
    public void deleteRoom(Long roomId) {
        roomService.deleteRoom(roomId);
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @Override
    public int getOccupancyCountAt(Long roomId, LocalDate date, LocalTime time) {
        return bookingRepository.countBookingsInRoomAtTime(roomId, date, time);
    }

}
