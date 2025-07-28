package com.example.manager_garage.config;

import com.example.manager_garage.entity.driver.License;
import com.example.manager_garage.entity.driver.StatusDriver;
import com.example.manager_garage.repository.LicenseRepository;
import com.example.manager_garage.repository.StatusDriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    private StatusDriverRepository statusDriverRepository;

    @Override
    public void run(String... args) {
        // Seed License
        if (licenseRepository.findByName("A1").isEmpty()) {
            License l = new License();
            l.setName("A1");
            l.setCreateDay(LocalDateTime.now());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("B2").isEmpty()) {
            License l = new License();
            l.setName("B2");
            l.setCreateDay(LocalDateTime.now());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("C").isEmpty()) {
            License l = new License();
            l.setName("C");
            l.setCreateDay(LocalDateTime.now());
            licenseRepository.save(l);
        }
        // Seed StatusDriver
        if (statusDriverRepository.findByName("Rảnh").isEmpty()) {
            StatusDriver s = new StatusDriver();
            s.setName("Rảnh");
            s.setCreateDay(LocalDateTime.now());
            statusDriverRepository.save(s);
        }
        if (statusDriverRepository.findByName("Đang công tác").isEmpty()) {
            StatusDriver s = new StatusDriver();
            s.setName("Đang công tác");
            s.setCreateDay(LocalDateTime.now());
            statusDriverRepository.save(s);
        }
    }
}