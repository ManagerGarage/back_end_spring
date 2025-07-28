package com.example.manager_garage.entity.car;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String licensePlateNumber;

    @ManyToOne
    @JoinColumn(name = "status_car_id", referencedColumnName = "id", nullable = false)
    private StatusCar statusCar;

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

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public StatusCar getStatusCar() {
        return statusCar;
    }

    public void setStatusCar(StatusCar statusCar) {
        this.statusCar = statusCar;
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