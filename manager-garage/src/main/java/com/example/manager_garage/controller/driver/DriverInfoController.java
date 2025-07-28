package com.example.manager_garage.controller.driver;

import com.example.manager_garage.entity.driver.License;
import com.example.manager_garage.entity.driver.StatusDriver;
import com.example.manager_garage.service.driver.LicenseService;
import com.example.manager_garage.service.driver.StatusDriverService;
import com.example.manager_garage.service.driver.DriverService;
import com.example.manager_garage.entity.driver.Driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverInfoController {
    @Autowired
    private LicenseService licenseService;
    @Autowired
    private StatusDriverService statusDriverService;
    @Autowired
    private DriverService driverService;

    @GetMapping("/licenses")
    public List<License> getAllLicenses() {
        return licenseService.getAllLicenses();
    }

    @GetMapping("/available-drivers")
    public List<Driver> getAvailableDriversByCarLicensePlate(@RequestParam("licensePlate") String licensePlate) {
        return driverService.findAvailableDriversByCarLicensePlate(licensePlate);
    }
}