package com.example.manager_garage.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateVehicleLicenseMappingRequest {
    @NotBlank(message = "TypeCar name is required")
    private String typeCarName;

    @NotBlank(message = "License name is required")
    private String licenseName;
}