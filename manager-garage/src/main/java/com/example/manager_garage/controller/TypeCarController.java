package com.example.manager_garage.controller;

import com.example.manager_garage.entity.car.TypeCar;
import com.example.manager_garage.service.car.TypeCarService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/type-cars")
@RequiredArgsConstructor
public class TypeCarController {
    private final TypeCarService typeCarService;

    @PostMapping
    public ResponseEntity<TypeCar> createTypeCar(@RequestParam String name) {
        TypeCar created = typeCarService.createTypeCar(name);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TypeCar>> getAllTypeCars() {
        return ResponseEntity.ok(typeCarService.getAllTypeCars());
    }
}