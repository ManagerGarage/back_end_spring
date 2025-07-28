package com.example.manager_garage.service.driver;

import com.example.manager_garage.entity.driver.StatusDriver;
import com.example.manager_garage.repository.StatusDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusDriverService {
    private final StatusDriverRepository statusDriverRepository;

    public StatusDriver createStatusDriver(String name) {
        StatusDriver statusDriver = new StatusDriver();
        statusDriver.setName(name);
        statusDriver.setCreateDay(LocalDateTime.now());
        return statusDriverRepository.save(statusDriver);
    }

    public List<StatusDriver> getAllStatusDrivers() {
        return statusDriverRepository.findAll();
    }
}