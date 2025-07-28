package com.example.manager_garage.service.car;

import com.example.manager_garage.entity.car.StatusCar;
import com.example.manager_garage.repository.StatusCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusCarService {
    private final StatusCarRepository statusCarRepository;

    public StatusCar createStatusCar(String name) {
        StatusCar statusCar = new StatusCar();
        statusCar.setName(name);
        statusCar.setCreateDay(LocalDateTime.now());
        return statusCarRepository.save(statusCar);
    }

    public List<StatusCar> getAllStatusCars() {
        return statusCarRepository.findAll();
    }
}