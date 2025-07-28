package com.example.manager_garage.entity.car;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.example.manager_garage.entity.auth.Role;
import com.example.manager_garage.entity.driver.License;

@Entity
@Table(name = "veheicle_license_mapping")
public class VehicleLicenseMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "license_id", referencedColumnName = "id", nullable = false)
    private License license;

    @ManyToOne
    @JoinColumn(name = "type_car_id", referencedColumnName = "id", nullable = false)
    private TypeCar typeCar;

    @Column(nullable = false)
    private LocalDateTime createDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public TypeCar getTypeCar() {
        return typeCar;
    }

    public void setTypeCar(TypeCar typeCar) {
        this.typeCar = typeCar;
    }

    public LocalDateTime getCreateDay() {
        return createDay;
    }

    public void setCreateDay(LocalDateTime createDay) {
        this.createDay = createDay;
    }
}