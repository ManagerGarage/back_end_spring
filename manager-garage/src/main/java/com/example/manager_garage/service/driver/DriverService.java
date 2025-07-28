package com.example.manager_garage.service.driver;

import com.example.manager_garage.entity.driver.Driver;
import com.example.manager_garage.entity.auth.User;
import com.example.manager_garage.repository.DriverRepository;
import com.example.manager_garage.repository.StatusDriverRepository;
import com.example.manager_garage.entity.driver.StatusDriver;

import com.example.manager_garage.repository.LicenseRepository;
import com.example.manager_garage.entity.driver.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import com.example.manager_garage.exception.ResourceNotFoundException;
import com.example.manager_garage.exception.ConflictException;
import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private StatusDriverRepository statusDriverRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private com.example.manager_garage.repository.CarRepository carRepository;
    @Autowired
    private com.example.manager_garage.repository.VehicleLicenseMappingRepository vehicleLicenseMappingRepository;
    @Autowired
    private com.example.manager_garage.repository.TypeCarRepository typeCarRepository;

    public Driver createDriverForUser(User user, String name, String phone, String dayBirth, String address,
            String licenseName) {
        Driver driver = new Driver();
        driver.setUser(user);
        driver.setName(name);
        driver.setPhone(phone);
        driver.setDayBirth(dayBirth);
        driver.setAddress(address);
        driver.setCreateDay(LocalDateTime.now());
        // Set status_driver mặc định là 'Rảnh'
        StatusDriver status = statusDriverRepository.findByName("Rảnh")
                .orElseThrow(() -> new ResourceNotFoundException("Default status 'Rảnh' not found in database."));
        driver.setStatusDriver(status);
        // Set license theo tên được truyền vào
        if (licenseName != null && !licenseName.isEmpty()) {
            License license = licenseRepository.findByName(licenseName)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "License '" + licenseName + "' not found in database."));
            driver.setLicense(license);
        } else {
            // Nếu không có license được truyền vào, ném lỗi hoặc set mặc định
            throw new ResourceNotFoundException("License is required for driver.");
        }
        return driverRepository.save(driver);
    }

    public Optional<Driver> findByUser(User user) {
        return driverRepository.findByUser(user);
    }

    public List<Driver> findAvailableDriversByCarLicensePlate(String licensePlateNumber) {
        // 1. Lấy trạng thái 'Rảnh'
        var statusOpt = statusDriverRepository.findByName("Rảnh");
        if (statusOpt.isEmpty())
            throw new ResourceNotFoundException("Status 'Rảnh' not found");
        var status = statusOpt.get();
        if (licensePlateNumber == null || licensePlateNumber.isBlank()) {
            throw new ResourceNotFoundException("Biển số xe không được để trống",
                    org.springframework.http.HttpStatus.BAD_REQUEST);
        }
        // 2. Lấy xe theo biển số
        var carOpt = carRepository.findByLicensePlateNumber(licensePlateNumber);
        if (carOpt.isEmpty())
            throw new ResourceNotFoundException("Car not found");
        var car = carOpt.get();
        var typeCar = car.getTypeCar();
        // 3. Lấy các license phù hợp với typeCar
        var mappings = vehicleLicenseMappingRepository.findAll();
        List<License> suitableLicenses = mappings.stream()
                .filter(m -> m.getTypeCar().getId().equals(typeCar.getId()))
                .map(m -> m.getLicense())
                .toList();
        // 4. Lọc tài xế theo trạng thái và license phù hợp
        List<Driver> result = new java.util.ArrayList<>();
        for (License license : suitableLicenses) {
            result.addAll(driverRepository.findByStatusDriverAndLicense(status, license));
        }
        return result;
    }

    // Lấy tất cả tài xế
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Lấy tài xế theo ID
    public Driver getDriverById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + id));
    }

    // Cập nhật thông tin tài xế
    public Driver updateDriver(Long id, String name, String phone, String dayBirth, String address,
            String licenseName, String statusDriverName) {
        Driver driver = getDriverById(id);

        // Kiểm tra số điện thoại có trùng với tài xế khác không
        driverRepository.findByPhone(phone).ifPresent(existingDriver -> {
            if (!existingDriver.getId().equals(id)) {
                throw new ConflictException("Driver with phone '" + phone + "' already exists.");
            }
        });

        // Cập nhật thông tin cơ bản
        driver.setName(name);
        driver.setPhone(phone);
        driver.setDayBirth(dayBirth);
        driver.setAddress(address);

        // Cập nhật license
        if (licenseName != null && !licenseName.isEmpty()) {
            License license = licenseRepository.findByName(licenseName)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "License '" + licenseName + "' not found in database."));
            driver.setLicense(license);
        }

        // Cập nhật trạng thái
        if (statusDriverName != null && !statusDriverName.isEmpty()) {
            StatusDriver status = statusDriverRepository.findByName(statusDriverName)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "StatusDriver '" + statusDriverName + "' not found in database."));
            driver.setStatusDriver(status);
        }

        return driverRepository.save(driver);
    }

    // Xóa tài xế
    public void deleteDriver(Long id) {
        Driver driver = getDriverById(id);
        driverRepository.delete(driver);
    }
}