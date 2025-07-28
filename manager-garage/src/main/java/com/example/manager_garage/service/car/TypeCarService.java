package com.example.manager_garage.service.car;

import com.example.manager_garage.entity.car.TypeCar;
import com.example.manager_garage.repository.TypeCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeCarService {
    private final TypeCarRepository typeCarRepository;

    public TypeCar createTypeCar(String name) {
        TypeCar typeCar = new TypeCar();
        typeCar.setName(name);
        typeCar.setCreateDay(LocalDateTime.now());
        return typeCarRepository.save(typeCar);
    }

    public List<TypeCar> getAllTypeCars() {
        return typeCarRepository.findAll();
    }
}