package com.coworkingspace.server.services;

import com.coworkingspace.server.DTOs.RoomDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface AdminService {
    RoomDTO createRoom(RoomDTO dto);
    RoomDTO updateRoom(Long roomId, RoomDTO dto);
    void deleteRoom(Long roomId);
    List<RoomDTO> getAllRooms();
    int getOccupancyCountAt(Long roomId, LocalDate date, LocalTime time);

}
