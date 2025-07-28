package com.example.manager_garage.controller;

import com.example.manager_garage.dto.request.CreateVehicleLicenseMappingRequest;
import com.example.manager_garage.entity.car.VehicleLicenseMapping;
import com.example.manager_garage.service.car.VehicleLicenseMappingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-license-mappings")
@RequiredArgsConstructor
public class VehicleLicenseMappingController {
    private final VehicleLicenseMappingService vehicleLicenseMappingService;

    // Lấy danh sách tất cả vehicle-license-mappings
    @GetMapping
    public ResponseEntity<List<VehicleLicenseMapping>> getAllMappings() {
        return ResponseEntity.ok(vehicleLicenseMappingService.getAllMappings());
    }

    // Lấy danh sách vehicle-license-mappings chỉ cho xe có trạng thái "Rảnh"
    @GetMapping("/available")
    public ResponseEntity<List<VehicleLicenseMapping>> getAvailableMappings() {
        return ResponseEntity.ok(vehicleLicenseMappingService.getAvailableMappings());
    }

    @PostMapping
    public ResponseEntity<VehicleLicenseMapping> createMapping(
            @Valid @RequestBody CreateVehicleLicenseMappingRequest request) {
        VehicleLicenseMapping created = vehicleLicenseMappingService.createMapping(request);
        return ResponseEntity.ok(created);
    }
}