package com.coworkingspace.server.mappers;

import com.coworkingspace.server.DTOs.AdminDTO;
import com.coworkingspace.server.models.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminDTO toDTO(Admin admin) {
        if (admin == null) return null;

        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setUsername(admin.getUsername());
        dto.setEmail(admin.getEmail());
        return dto;
    }

    public Admin toEntity(AdminDTO dto) {
        if (dto == null) return null;

        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setUsername(dto.getUsername());
        admin.setEmail(dto.getEmail());
        return admin;
    }
}
