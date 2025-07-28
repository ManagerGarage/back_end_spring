package com.example.manager_garage.repository;

import com.example.manager_garage.entity.car.Car;
import com.example.manager_garage.entity.car.StatusCar;
import com.example.manager_garage.entity.car.TypeCar;
import com.example.manager_garage.entity.driver.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByLicensePlateNumber(String licensePlateNumber);

    List<Car> findByStatusCarAndTypeCar(StatusCar statusCar, TypeCar typeCar);

    @Query("SELECT c FROM Car c WHERE c.statusCar = :statusCar AND c.typeCar IN (SELECT v.typeCar FROM VehicleLicenseMapping v WHERE v.license.id = :licenseId)")
    List<Car> findByStatusCarAndLicenseId(@Param("statusCar") StatusCar statusCar, @Param("licenseId") Long licenseId);

    @Query("SELECT c FROM Car c WHERE c.statusCar.name = :statusCarName AND c.typeCar IN (SELECT v.typeCar FROM VehicleLicenseMapping v WHERE v.license.name = :licenseName)")
    List<Car> findByStatusCarNameAndLicenseName(@Param("statusCarName") String statusCarName,
            @Param("licenseName") String licenseName);

    @Query("SELECT c FROM Car c WHERE c.statusCar.name = :statusCarName")
    List<Car> findByStatusCarName(@Param("statusCarName") String statusCarName);

    List<Car> findByStatusCar(StatusCar statusCar);
}