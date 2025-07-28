package com.example.manager_garage.repository;

import com.example.manager_garage.entity.driver.Driver;
import com.example.manager_garage.entity.auth.User;
import com.example.manager_garage.entity.driver.License;
import com.example.manager_garage.entity.driver.StatusDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByUser(User user);

    Optional<Driver> findByPhone(String phone);

    List<Driver> findByStatusDriverAndLicense(StatusDriver statusDriver, License license);

    List<Driver> findByLicense(License license);

    Optional<Driver> findByName(String name);
}