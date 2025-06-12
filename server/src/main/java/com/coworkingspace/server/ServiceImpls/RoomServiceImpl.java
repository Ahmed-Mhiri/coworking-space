package com.coworkingspace.server.ServiceImpls;

import com.coworkingspace.server.DTOs.RoomDTO;
import com.coworkingspace.server.mappers.RoomMapper;
import com.coworkingspace.server.models.Room;
import com.coworkingspace.server.models.RoomType;
import com.coworkingspace.server.repositories.RoomRepository;
import com.coworkingspace.server.services.RoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        Room room = roomMapper.toEntity(roomDTO);
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(roomMapper::toDTO).toList();
    }

    @Override
    public RoomDTO updateRoom(Long roomId, RoomDTO dto) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        room.setType(RoomType.valueOf(dto.getType()));
        room.setSizeLimit(dto.getSizeLimit());
        room.setPricePerHour(dto.getPricePerHour());
        return roomMapper.toDTO(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    @Override
    public Room getRoomEntityById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));
    }
}
