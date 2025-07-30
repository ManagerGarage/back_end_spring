package com.example.manager_garage.config;

import com.example.manager_garage.entity.auth.Role;
import com.example.manager_garage.entity.auth.User;
import com.example.manager_garage.entity.driver.License;
import com.example.manager_garage.entity.driver.StatusDriver;
import com.example.manager_garage.repository.LicenseRepository;
import com.example.manager_garage.repository.StatusDriverRepository;
import com.example.manager_garage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import com.example.manager_garage.util.DateTimeUtil;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    private StatusDriverRepository statusDriverRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Seed License
        if (licenseRepository.findByName("A1").isEmpty()) {
            License l = new License();
            l.setName("A1");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("A0").isEmpty()) {
            License l = new License();
            l.setName("A0");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }

        if (licenseRepository.findByName("A2").isEmpty()) {
            License l = new License();
            l.setName("A2");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }

        if (licenseRepository.findByName("A3").isEmpty()) {
            License l = new License();
            l.setName("A3");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("A4").isEmpty()) {
            License l = new License();
            l.setName("A4");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("B1").isEmpty()) {
            License l = new License();
            l.setName("B1");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("B2").isEmpty()) {
            License l = new License();
            l.setName("B2");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("C").isEmpty()) {
            License l = new License();
            l.setName("C");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("D").isEmpty()) {
            License l = new License();
            l.setName("D");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("E").isEmpty()) {
            License l = new License();
            l.setName("E");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("FB2").isEmpty()) {
            License l = new License();
            l.setName("FB2");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("FC").isEmpty()) {
            License l = new License();
            l.setName("FC");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("FD").isEmpty()) {
            License l = new License();
            l.setName("FD");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        if (licenseRepository.findByName("FE").isEmpty()) {
            License l = new License();
            l.setName("FE");
            l.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            licenseRepository.save(l);
        }
        // Seed StatusDriver
        if (statusDriverRepository.findByName("Rảnh").isEmpty()) {
            StatusDriver s = new StatusDriver();
            s.setName("Rảnh");
            s.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            statusDriverRepository.save(s);
        }
        if (statusDriverRepository.findByName("Đang công tác").isEmpty()) {
            StatusDriver s = new StatusDriver();
            s.setName("Đang công tác");
            s.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            statusDriverRepository.save(s);
        }

        // Seed default users
        seedDefaultUsers();
    }

    private void seedDefaultUsers() {
        // Create MANAGER user if not exists
        if (userRepository.findByUsername("manager") == null) {
            User managerUser = new User();
            managerUser.setUsername("manager");
            managerUser.setPassword(passwordEncoder.encode("manager123"));
            managerUser.setRole(Role.MANAGER);
            managerUser.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            userRepository.save(managerUser);
            System.out.println("Created default MANAGER user: manager");
        }

        // Create COMMANDER user if not exists
        if (userRepository.findByUsername("commander") == null) {
            User commanderUser = new User();
            commanderUser.setUsername("commander");
            commanderUser.setPassword(passwordEncoder.encode("commander123"));
            commanderUser.setRole(Role.COMMANDER);
            commanderUser.setCreateDay(DateTimeUtil.getCurrentVietnamTime());
            userRepository.save(commanderUser);
            System.out.println("Created default COMMANDER user: commander");
        }
    }
}