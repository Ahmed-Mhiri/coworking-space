package com.coworkingspace.server.ServiceImpls;

import com.coworkingspace.server.DTOs.MissionDTO;
import com.coworkingspace.server.mappers.MissionMapper;
import com.coworkingspace.server.models.Freelancer;
import com.coworkingspace.server.models.Mission;
import com.coworkingspace.server.models.ProgressStatus;
import com.coworkingspace.server.models.User;
import com.coworkingspace.server.repositories.FreelancerRepository;
import com.coworkingspace.server.repositories.MissionRepository;
import com.coworkingspace.server.repositories.UserRepository;
import com.coworkingspace.server.services.MissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final FreelancerRepository freelancerRepository;
    private final MissionMapper missionMapper;

    private final UserRepository userRepository;

    public MissionServiceImpl(MissionRepository missionRepository,
                              FreelancerRepository freelancerRepository,
                              UserRepository userRepository,
                              MissionMapper missionMapper) {
        this.missionRepository = missionRepository;
        this.freelancerRepository = freelancerRepository;
        this.userRepository = userRepository;
        this.missionMapper = missionMapper;
    }

    private Freelancer getCurrentFreelancer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Unauthenticated request");
        }
        String email = authentication.getName(); // this is actually an email, not username
        System.out.println("🔐 Authenticated email: " + email);
        return freelancerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    @Override
    public MissionDTO createMission(MissionDTO dto) {
        Mission mission = missionMapper.toEntity(dto);
        Freelancer freelancer = getCurrentFreelancer();
        mission.setFreelancer(freelancer);
        return missionMapper.toDTO(missionRepository.save(mission));
    }

    @Override
    public MissionDTO updateMission(Long missionId, MissionDTO dto) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));
        mission.setName(dto.getName());
        mission.setDeadline(dto.getDeadline());
        mission.setProgress(ProgressStatus.valueOf(dto.getProgress()));
        mission.setDescription(dto.getDescription()); // Ensure this is set too
        return missionMapper.toDTO(missionRepository.save(mission));
    }

    @Override
    public void deleteMission(Long missionId) {
        missionRepository.deleteById(missionId);
    }

    @Override
    public List<MissionDTO> getMissionsForFreelancer() {
        Freelancer freelancer = getCurrentFreelancer();
        return missionRepository.findByFreelancerId(freelancer.getId())
                .stream().map(missionMapper::toDTO).toList();
    }
}
