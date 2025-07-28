package com.example.manager_garage.controller;

import com.example.manager_garage.dto.request.CreateCarRequest;
import com.example.manager_garage.dto.request.UpdateCarRequest;
import com.example.manager_garage.entity.car.Car;
import com.example.manager_garage.service.car.CarService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<Car> createCar(@Valid @RequestBody CreateCarRequest request) {
        Car createdCar = carService.createCar(request);
        return ResponseEntity.ok(createdCar);
    }

    // Lấy danh sách tất cả xe
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/available-by-license")
    public ResponseEntity<List<Car>> getAvailableCarsByLicense(@RequestParam String licenseName) {
        List<Car> cars = carService.findAvailableCarsByLicense(licenseName);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/available-by-type")
    public ResponseEntity<List<Car>> getAvailableCarsByType(@RequestParam String typeCarName) {
        List<Car> cars = carService.findAvailableCarsByType(typeCarName);
        return ResponseEntity.ok(cars);
    }

    // Lấy xe theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    // Cập nhật thông tin xe
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @Valid @RequestBody UpdateCarRequest request) {
        Car updatedCar = carService.updateCar(
                id,
                request.getLicensePlateNumber(),
                request.getTypeCarName(),
                request.getStatusCarName());
        return ResponseEntity.ok(updatedCar);
    }

    // Cập nhật thông tin xe (POST method)
    @PostMapping("/{id}")
    public ResponseEntity<Car> updateCarPost(@PathVariable Long id, @Valid @RequestBody UpdateCarRequest request) {
        Car updatedCar = carService.updateCar(
                id,
                request.getLicensePlateNumber(),
                request.getTypeCarName(),
                request.getStatusCarName());
        return ResponseEntity.ok(updatedCar);
    }

    // Xóa xe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}