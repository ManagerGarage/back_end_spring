package com.example.manager_garage.repository;

import com.example.manager_garage.entity.car.StatusCar;
import com.example.manager_garage.entity.car.TypeCar;
import com.example.manager_garage.entity.driver.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface StatusCarRepository extends JpaRepository<StatusCar, Long> {
    Optional<StatusCar> findByName(String name);
}