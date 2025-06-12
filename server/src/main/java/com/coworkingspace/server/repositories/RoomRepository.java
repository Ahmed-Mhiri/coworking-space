package com.coworkingspace.server.repositories;

import com.coworkingspace.server.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
