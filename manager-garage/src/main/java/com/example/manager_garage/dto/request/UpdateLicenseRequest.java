package com.example.manager_garage.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateLicenseRequest {
    @NotBlank(message = "License name is required")
    private String name;
}