package com.example.manager_garage.service.car;

import com.example.manager_garage.dto.request.CreateVehicleLicenseMappingRequest;
import com.example.manager_garage.entity.car.Car;
import com.example.manager_garage.entity.car.TypeCar;
import com.example.manager_garage.entity.car.VehicleLicenseMapping;
import com.example.manager_garage.entity.driver.License;
import com.example.manager_garage.repository.LicenseRepository;
import com.example.manager_garage.repository.TypeCarRepository;
import com.example.manager_garage.repository.VehicleLicenseMappingRepository;
import com.example.manager_garage.repository.CarRepository;
import com.example.manager_garage.repository.StatusCarRepository;
import com.example.manager_garage.exception.ResourceNotFoundException;
import com.example.manager_garage.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleLicenseMappingService {
    private final VehicleLicenseMappingRepository vehicleLicenseMappingRepository;
    private final TypeCarRepository typeCarRepository;
    private final LicenseRepository licenseRepository;
    private final CarRepository carRepository;
    private final StatusCarRepository statusCarRepository;

    public VehicleLicenseMapping createMapping(CreateVehicleLicenseMappingRequest request) {
        TypeCar typeCar = typeCarRepository.findByName(request.getTypeCarName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "TypeCar not found with name: " + request.getTypeCarName()));
        License license = licenseRepository.findByName(request.getLicenseName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "License not found with name: " + request.getLicenseName()));
        if (vehicleLicenseMappingRepository.existsByTypeCarAndLicense(typeCar, license)) {
            throw new ConflictException("Mapping with this TypeCar and License already exists.");
        }
        VehicleLicenseMapping mapping = new VehicleLicenseMapping();
        mapping.setTypeCar(typeCar);
        mapping.setLicense(license);
        mapping.setCreateDay(LocalDateTime.now());
        return vehicleLicenseMappingRepository.save(mapping);
    }

    // Lấy tất cả vehicle-license-mappings
    public List<VehicleLicenseMapping> getAllMappings() {
        return vehicleLicenseMappingRepository.findAll();
    }

    // Lấy vehicle-license-mappings chỉ cho những xe có trạng thái "Rảnh"
    public List<VehicleLicenseMapping> getAvailableMappings() {
        // Lấy trạng thái "Rảnh"
        var availableStatus = statusCarRepository.findByName("Rảnh")
                .orElseThrow(() -> new ResourceNotFoundException("Status 'Rảnh' not found"));

        // Lấy tất cả xe có trạng thái "Rảnh"
        List<Car> availableCars = carRepository.findByStatusCar(availableStatus);

        // Lấy các loại xe từ những xe rảnh
        List<TypeCar> availableTypeCars = availableCars.stream()
                .map(car -> car.getTypeCar())
                .distinct()
                .toList();

        // Lấy mapping chỉ cho những loại xe có xe rảnh
        return vehicleLicenseMappingRepository.findByTypeCarIn(availableTypeCars);
    }
}