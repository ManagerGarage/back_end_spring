package com.example.manager_garage.dto.request;

import jakarta.validation.constraints.NotNull;

public class CompleteScheduleRequest {
    @NotNull(message = "Schedule ID is required")
    private Long scheduleId;

    @NotNull(message = "Driver ID is required")
    private Long driverId;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
}