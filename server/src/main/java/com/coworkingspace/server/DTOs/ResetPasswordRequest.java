package com.coworkingspace.server.DTOs;


import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
