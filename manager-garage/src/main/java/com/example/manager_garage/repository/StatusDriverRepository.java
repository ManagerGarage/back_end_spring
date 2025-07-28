package com.example.manager_garage.repository;

import com.example.manager_garage.entity.driver.StatusDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StatusDriverRepository extends JpaRepository<StatusDriver, Long> {
    Optional<StatusDriver> findByName(String name);
}