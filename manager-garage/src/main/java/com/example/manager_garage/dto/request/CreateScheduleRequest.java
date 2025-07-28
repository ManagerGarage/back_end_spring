package com.example.manager_garage.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import java.time.LocalDateTime;

public class CreateScheduleRequest {
    @NotBlank(message = "Trip purpose is required")
    private String tripPurpose;

    @NotBlank(message = "Car license plate number is required")
    private String carLicensePlateNumber;

    @NotBlank(message = "Driver name is required")
    private String driverName;

    @NotBlank(message = "Departure point is required")
    private String departurePoint;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "Estimated end time is required")
    private LocalDateTime estimatedTimeEnd;

    // Custom validation method
    @AssertTrue(message = "Estimated end time must be after start time")
    public boolean isEstimatedTimeEndAfterStartTime() {
        if (startTime == null || estimatedTimeEnd == null) {
            return true; // Let @NotNull handle null validation
        }
        return estimatedTimeEnd.isAfter(startTime);
    }

    // Getters and Setters
    public String getTripPurpose() {
        return tripPurpose;
    }

    public void setTripPurpose(String tripPurpose) {
        this.tripPurpose = tripPurpose;
    }

    public String getCarLicensePlateNumber() {
        return carLicensePlateNumber;
    }

    public void setCarLicensePlateNumber(String carLicensePlateNumber) {
        this.carLicensePlateNumber = carLicensePlateNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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
}