package com.example.manager_garage.repository;

import com.example.manager_garage.entity.car.TypeCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeCarRepository extends JpaRepository<TypeCar, Long> {
    Optional<TypeCar> findByName(String name);
}