package com.example.manager_garage.exception;

public class DriverLicenseMismatchException extends RuntimeException {

    public DriverLicenseMismatchException(String message) {
        super(message);
    }

    public DriverLicenseMismatchException(String driverName, String carType, String licenseType) {
        super(String.format("Tài xế %s không có quyền điều khiển loại xe %s với bằng lái %s",
                driverName, carType, licenseType));
    }
}