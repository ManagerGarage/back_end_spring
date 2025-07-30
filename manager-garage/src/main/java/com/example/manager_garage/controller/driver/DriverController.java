package com.example.manager_garage.controller.driver;

import com.example.manager_garage.entity.driver.Driver;
import com.example.manager_garage.service.driver.DriverService;
import com.example.manager_garage.dto.request.UpdateDriverRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    // Lấy danh sách tất cả tài xế
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    // Lấy tài xế theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    // Cập nhật thông tin tài xế
    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @RequestBody UpdateDriverRequest request) {
        Driver updatedDriver = driverService.updateDriver(
                id,
                request.getName(),
                request.getPhone(),
                request.getDayBirth(),
                request.getAddress(),
                request.getLicenseName(),
                request.getStatusDriverName());
        return ResponseEntity.ok(updatedDriver);
    }

    // Cập nhật thông tin tài xế (POST method)
    @PostMapping("/{id}")
    public ResponseEntity<Driver> updateDriverPost(@PathVariable Long id, @RequestBody UpdateDriverRequest request) {
        Driver updatedDriver = driverService.updateDriver(
                id,
                request.getName(),
                request.getPhone(),
                request.getDayBirth(),
                request.getAddress(),
                request.getLicenseName(),
                request.getStatusDriverName());
        return ResponseEntity.ok(updatedDriver);
    }

    // Xóa tài xế
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    // Tìm tài xế rảnh theo biển số xe
    @GetMapping("/available-by-car")
    public ResponseEntity<List<Driver>> getAvailableDriversByCar(@RequestParam String licensePlateNumber) {
        return ResponseEntity.ok(driverService.findAvailableDriversByCarLicensePlate(licensePlateNumber));
    }
}