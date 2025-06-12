package com.coworkingspace.server.DTOs;

import lombok.Data;

@Data
public class RoomDTO {
    private Long id;
    private String type;  // Enum value as String
    private int sizeLimit;
    private double pricePerHour;
}