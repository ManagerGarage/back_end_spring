package com.example.manager_garage.service.driver;

import com.example.manager_garage.entity.driver.License;
import com.example.manager_garage.repository.LicenseRepository;
import com.example.manager_garage.exception.ResourceNotFoundException;
import com.example.manager_garage.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LicenseService {
    @Autowired
    private LicenseRepository licenseRepository;

    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    public License getLicenseById(Long id) {
        return licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License not found with id: " + id));
    }

    public License createLicense(String name) {
        // Kiểm tra tên bằng lái đã tồn tại chưa
        licenseRepository.findByName(name).ifPresent(license -> {
            throw new ConflictException("License with name '" + name + "' already exists.");
        });

        License license = new License();
        license.setName(name);
        license.setCreateDay(LocalDateTime.now());
        return licenseRepository.save(license);
    }

    public License updateLicense(Long id, String name) {
        License license = getLicenseById(id);

        // Kiểm tra tên mới có trùng với bằng lái khác không
        licenseRepository.findByName(name).ifPresent(existingLicense -> {
            if (!existingLicense.getId().equals(id)) {
                throw new ConflictException("License with name '" + name + "' already exists.");
            }
        });

        license.setName(name);
        return licenseRepository.save(license);
    }

    public void deleteLicense(Long id) {
        License license = getLicenseById(id);
        licenseRepository.delete(license);
    }
}