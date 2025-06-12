package com.coworkingspace.server.DTOs;

import com.coworkingspace.server.models.Role;
import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private Role role; // only FREELANCER or INTERN allowed
}
