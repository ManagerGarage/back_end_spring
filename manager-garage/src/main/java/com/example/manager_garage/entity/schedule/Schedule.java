package com.example.manager_garage.entity.schedule;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.example.manager_garage.entity.car.Car;
import com.example.manager_garage.entity.driver.Driver;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tripPurpose;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "status_schedule", referencedColumnName = "id", nullable = false)
    private StatusSchedule statusSchedule;

    @Column(nullable = false)
    private String departurePoint;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime estimatedTimeEnd;

    @Column(nullable = false)
    private LocalDateTime createDay;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTripPurpose() {
        return tripPurpose;
    }

    public void setTripPurpose(String tripPurpose) {
        this.tripPurpose = tripPurpose;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public StatusSchedule getStatusSchedule() {
        return statusSchedule;
    }

    public void setStatusSchedule(StatusSchedule statusSchedule) {
        this.statusSchedule = statusSchedule;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEstimatedTimeEnd() {
        return estimatedTimeEnd;
    }

    public void setEstimatedTimeEnd(LocalDateTime estimatedTimeEnd) {
        this.estimatedTimeEnd = estimatedTimeEnd;
    }

    public LocalDateTime getCreateDay() {
        return createDay;
    }

    public void setCreateDay(LocalDateTime createDay) {
        this.createDay = createDay;
    }
}