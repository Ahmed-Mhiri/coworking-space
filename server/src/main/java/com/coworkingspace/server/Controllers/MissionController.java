package com.coworkingspace.server.Controllers;

import com.coworkingspace.server.DTOs.MissionDTO;
import com.coworkingspace.server.services.MissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true") // ✅ Add this
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @PostMapping
    public ResponseEntity<MissionDTO> createMission(@RequestBody MissionDTO dto) {
        return ResponseEntity.ok(missionService.createMission(dto));
    }

    @GetMapping
    public ResponseEntity<List<MissionDTO>> getMissions() {
        return ResponseEntity.ok(missionService.getMissionsForFreelancer());
    }

    @PutMapping("/{missionId}")
    public ResponseEntity<MissionDTO> updateMission(@PathVariable Long missionId, @RequestBody MissionDTO dto) {
        return ResponseEntity.ok(missionService.updateMission(missionId, dto));
    }

    @DeleteMapping("/{missionId}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long missionId) {
        missionService.deleteMission(missionId);
        return ResponseEntity.noContent().build();
    }
}
