package com.coworkingspace.server.services;

import com.coworkingspace.server.DTOs.RoomDTO;
import com.coworkingspace.server.models.Room;

import java.util.List;

public interface RoomService {
    RoomDTO createRoom(RoomDTO roomDTO);
    List<RoomDTO> getAllRooms();
    RoomDTO updateRoom(Long roomId, RoomDTO dto);
    void deleteRoom(Long roomId);
    Room getRoomEntityById(Long roomId);


}
