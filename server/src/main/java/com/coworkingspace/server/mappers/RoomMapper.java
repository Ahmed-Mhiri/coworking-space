package com.coworkingspace.server.mappers;

import com.coworkingspace.server.DTOs.RoomDTO;
import com.coworkingspace.server.models.Room;
import com.coworkingspace.server.models.RoomType;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomDTO toDTO(Room room) {
        if (room == null) return null;

        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setType(room.getType().name());
        dto.setSizeLimit(room.getSizeLimit());
        dto.setPricePerHour(room.getPricePerHour());
        return dto;
    }

    public Room toEntity(RoomDTO dto) {
        if (dto == null) return null;

        Room room = new Room();
        room.setId(dto.getId());
        room.setType(RoomType.valueOf(dto.getType()));
        room.setSizeLimit(dto.getSizeLimit());
        room.setPricePerHour(dto.getPricePerHour());
        return room;
    }
}

