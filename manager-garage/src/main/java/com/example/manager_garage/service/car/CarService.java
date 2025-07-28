package com.example.manager_garage.service.car;

import com.example.manager_garage.dto.request.CreateCarRequest;
import com.example.manager_garage.entity.car.Car;
import com.example.manager_garage.entity.car.StatusCar;

import com.example.manager_garage.entity.car.TypeCar;
import com.example.manager_garage.entity.driver.License;
import com.example.manager_garage.exception.ConflictException;
import com.example.manager_garage.exception.ResourceNotFoundException;
import com.example.manager_garage.repository.CarRepository;
import com.example.manager_garage.repository.StatusCarRepository;

import com.example.manager_garage.repository.TypeCarRepository;
import com.example.manager_garage.repository.LicenseRepository;
import com.example.manager_garage.repository.VehicleLicenseMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

        private final CarRepository carRepository;
        private final TypeCarRepository typeCarRepository;
        private final StatusCarRepository statusCarRepository;
        private final LicenseRepository licenseRepository;
        private final VehicleLicenseMappingRepository vehicleLicenseMappingRepository;

        public Car createCar(CreateCarRequest request) {
                // Kiểm tra biển số xe đã tồn tại chưa
                carRepository.findByLicensePlateNumber(request.getLicensePlateNumber()).ifPresent(car -> {
                        throw new ConflictException(
                                        "Car with license plate " + request.getLicensePlateNumber()
                                                        + " already exists.");
                });

                // Tìm TypeCar và StatusCar
                TypeCar typeCar = typeCarRepository.findByName(request.getTypeCarName())
                                .orElseThrow(
                                                () -> new ResourceNotFoundException(
                                                                "TypeCar not found with name: "
                                                                                + request.getTypeCarName()));

                StatusCar statusCar = statusCarRepository.findByName(request.getStatusCarName())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "StatusCar not found with name: " + request.getStatusCarName()));

                // Tạo xe mới
                Car car = new Car();
                car.setLicensePlateNumber(request.getLicensePlateNumber());
                car.setTypeCar(typeCar);
                car.setStatusCar(statusCar);
                car.setCreateDay(LocalDateTime.now());

                return carRepository.save(car);
        }

        public List<Car> findAvailableCarsByLicense(String licenseName) {
                if (licenseName == null || licenseName.isBlank()) {
                        return carRepository.findByStatusCarName("Rảnh");
                }
                return carRepository.findByStatusCarNameAndLicenseName("Rảnh", licenseName);
        }

        public List<Car> findAvailableCarsByType(String typeCarName) {
                if (typeCarName == null || typeCarName.isBlank()) {
                        throw new ResourceNotFoundException("Tên loại xe không được để trống",
                                        org.springframework.http.HttpStatus.BAD_REQUEST);
                }
                TypeCar typeCar = typeCarRepository.findByName(typeCarName)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "TypeCar not found with name: " + typeCarName));
                StatusCar statusCar = statusCarRepository.findByName("Rảnh")
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "StatusCar 'Rảnh' not found"));
                return carRepository.findByStatusCarAndTypeCar(statusCar, typeCar);
        }

        // Lấy tất cả xe
        public List<Car> getAllCars() {
                return carRepository.findAll();
        }

        // Lấy xe theo ID
        public Car getCarById(Long id) {
                return carRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        }

        // Cập nhật thông tin xe
        public Car updateCar(Long id, String licensePlateNumber, String typeCarName, String statusCarName) {
                Car car = getCarById(id);

                // Kiểm tra biển số xe có trùng với xe khác không
                carRepository.findByLicensePlateNumber(licensePlateNumber).ifPresent(existingCar -> {
                        if (!existingCar.getId().equals(id)) {
                                throw new ConflictException(
                                                "Car with license plate '" + licensePlateNumber + "' already exists.");
                        }
                });

                // Cập nhật biển số xe
                car.setLicensePlateNumber(licensePlateNumber);

                // Cập nhật loại xe
                if (typeCarName != null && !typeCarName.isEmpty()) {
                        TypeCar typeCar = typeCarRepository.findByName(typeCarName)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "TypeCar not found with name: " + typeCarName));
                        car.setTypeCar(typeCar);
                }

                // Cập nhật trạng thái xe
                if (statusCarName != null && !statusCarName.isEmpty()) {
                        StatusCar statusCar = statusCarRepository.findByName(statusCarName)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "StatusCar not found with name: " + statusCarName));
                        car.setStatusCar(statusCar);
                }

                return carRepository.save(car);
        }

        // Xóa xe
        public void deleteCar(Long id) {
                Car car = getCarById(id);
                carRepository.delete(car);
        }
}