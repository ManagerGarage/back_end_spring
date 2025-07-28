package com.example.manager_garage.repository;

import com.example.manager_garage.entity.driver.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenseRepository extends JpaRepository<License, Long> {
    Optional<License> findByName(String name);
}