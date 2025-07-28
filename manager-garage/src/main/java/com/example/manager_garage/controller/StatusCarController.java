package com.example.manager_garage.controller;

import com.example.manager_garage.entity.car.StatusCar;
import com.example.manager_garage.service.car.StatusCarService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status-cars")
@RequiredArgsConstructor
public class StatusCarController {
    private final StatusCarService statusCarService;

    @GetMapping
    public ResponseEntity<List<StatusCar>> getAllStatusCars() {
        return ResponseEntity.ok(statusCarService.getAllStatusCars());
    }
}