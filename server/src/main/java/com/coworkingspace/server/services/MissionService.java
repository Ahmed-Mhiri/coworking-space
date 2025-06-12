package com.coworkingspace.server.services;

import com.coworkingspace.server.DTOs.MissionDTO;

import java.util.List;

public interface MissionService {
    MissionDTO createMission(MissionDTO dto);
    MissionDTO updateMission(Long missionId, MissionDTO missionDTO);
    void deleteMission(Long missionId);
    List<MissionDTO> getMissionsForFreelancer();
}
