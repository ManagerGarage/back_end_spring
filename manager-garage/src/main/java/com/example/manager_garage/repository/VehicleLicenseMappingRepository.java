package com.example.manager_garage.repository;

import com.example.manager_garage.entity.car.TypeCar;
import com.example.manager_garage.entity.driver.License;
import com.example.manager_garage.entity.car.VehicleLicenseMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleLicenseMappingRepository extends JpaRepository<VehicleLicenseMapping, Long> {
    boolean existsByTypeCarAndLicense(TypeCar typeCar, License license);

    @Query("SELECT v.typeCar FROM VehicleLicenseMapping v WHERE v.license.id = :licenseId")
    List<TypeCar> findTypeCarsByLicenseId(Long licenseId);

    List<VehicleLicenseMapping> findByTypeCarIn(List<TypeCar> typeCars);
}