package com.example.manager_garage.controller;

import com.example.manager_garage.entity.driver.License;
import com.example.manager_garage.service.driver.LicenseService;
import com.example.manager_garage.dto.request.CreateLicenseRequest;
import com.example.manager_garage.dto.request.UpdateLicenseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/licenses")
@RequiredArgsConstructor
public class LicenseController {
    private final LicenseService licenseService;

    // Lấy danh sách tất cả bằng lái
    @GetMapping
    public ResponseEntity<List<License>> getAllLicenses() {
        return ResponseEntity.ok(licenseService.getAllLicenses());
    }

    // Lấy bằng lái theo ID
    @GetMapping("/{id}")
    public ResponseEntity<License> getLicenseById(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.getLicenseById(id));
    }

    // Tạo bằng lái mới
    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody CreateLicenseRequest request) {
        return ResponseEntity.ok(licenseService.createLicense(request.getName()));
    }

    // Cập nhật bằng lái
    @PutMapping("/{id}")
    public ResponseEntity<License> updateLicense(@PathVariable Long id, @RequestBody UpdateLicenseRequest request) {
        return ResponseEntity.ok(licenseService.updateLicense(id, request.getName()));
    }

    // Xóa bằng lái
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        licenseService.deleteLicense(id);
        return ResponseEntity.noContent().build();
    }
}