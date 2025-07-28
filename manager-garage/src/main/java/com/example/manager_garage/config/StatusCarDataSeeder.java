package com.example.manager_garage.config;

import com.example.manager_garage.entity.car.StatusCar;
import com.example.manager_garage.repository.StatusCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatusCarDataSeeder implements CommandLineRunner {
    private final StatusCarRepository statusCarRepository;

    @Override
    public void run(String... args) {
        List<String> defaultStatus = List.of("Rảnh", "Đang công tác");
        for (String name : defaultStatus) {
            if (statusCarRepository.findByName(name).isEmpty()) {
                StatusCar statusCar = new StatusCar();
                statusCar.setName(name);
                statusCar.setCreateDay(LocalDateTime.now());
                statusCarRepository.save(statusCar);
            }
        }
    }
}