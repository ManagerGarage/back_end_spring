package com.example.manager_garage.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCarRequest {
    @NotBlank(message = "License plate number is required")
    private String licensePlateNumber;

    @NotBlank(message = "TypeCar name is required")
    private String typeCarName;

    @NotBlank(message = "StatusCar name is required")
    private String statusCarName;
}