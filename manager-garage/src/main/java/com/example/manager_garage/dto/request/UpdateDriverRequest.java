package com.example.manager_garage.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateDriverRequest {
    @NotBlank(message = "Driver name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Day of birth is required")
    private String dayBirth;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "License name is required")
    private String licenseName;

    @NotBlank(message = "Status driver name is required")
    private String statusDriverName;
}