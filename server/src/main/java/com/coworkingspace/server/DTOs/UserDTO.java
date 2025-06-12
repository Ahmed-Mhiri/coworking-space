package com.coworkingspace.server.DTOs;

import lombok.Data;

@Data
public abstract class UserDTO {
    private Long id;
    private String username;
    private String email;
}