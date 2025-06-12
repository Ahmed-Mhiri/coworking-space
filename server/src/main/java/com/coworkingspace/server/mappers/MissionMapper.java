package com.coworkingspace.server.mappers;

import com.coworkingspace.server.DTOs.MissionDTO;
import com.coworkingspace.server.models.Mission;
import com.coworkingspace.server.models.ProgressStatus;
import org.springframework.stereotype.Component;

@Component
public class MissionMapper {

    public MissionDTO toDTO(Mission mission) {
        if (mission == null) return null;

        MissionDTO dto = new MissionDTO();
        dto.setId(mission.getId());
        dto.setName(mission.getName());
        dto.setDeadline(mission.getDeadline());
        dto.setProgress(mission.getProgress().name());
        dto.setDescription(mission.getDescription());  // <-- ADD THIS
        return dto;
    }

    public Mission toEntity(MissionDTO dto) {
        if (dto == null) return null;

        Mission mission = new Mission();
        mission.setId(dto.getId());
        mission.setName(dto.getName());
        mission.setDeadline(dto.getDeadline());
        mission.setProgress(ProgressStatus.valueOf(dto.getProgress()));
        mission.setDescription(dto.getDescription());  // <-- ADD THIS
        return mission;
    }
}