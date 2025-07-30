package com.example.manager_garage.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.manager_garage.entity.schedule.Schedule;
import com.example.manager_garage.entity.schedule.StatusSchedule;
import com.example.manager_garage.entity.car.Car;
import com.example.manager_garage.entity.car.TypeCar;
import com.example.manager_garage.entity.driver.Driver;
import com.example.manager_garage.entity.driver.License;
import com.example.manager_garage.dto.request.CreateScheduleRequest;
import com.example.manager_garage.dto.request.CompleteScheduleRequest;
import com.example.manager_garage.exception.ResourceNotFoundException;
import com.example.manager_garage.exception.ConflictException;
import com.example.manager_garage.exception.DriverLicenseMismatchException;
import com.example.manager_garage.repository.ScheduleRepository;
import com.example.manager_garage.repository.StatusScheduleRepository;
import com.example.manager_garage.repository.CarRepository;
import com.example.manager_garage.repository.DriverRepository;
import com.example.manager_garage.repository.VehicleLicenseMappingRepository;
import com.example.manager_garage.entity.auth.User;
import com.example.manager_garage.entity.auth.Role;
import java.util.List;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import com.example.manager_garage.util.DateTimeUtil;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private StatusScheduleRepository statusScheduleRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private VehicleLicenseMappingRepository vehicleLicenseMappingRepository;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesByUser(User user) {
        if (user.getRole() == Role.DRIVER) {
            // Nếu là DRIVER, chỉ lấy lịch trình của user này
            Driver driver = driverRepository.findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy thông tin tài xế cho user: " + user.getUsername()));
            return scheduleRepository.findByDriver(driver);
        } else {
            // Nếu là MANAGER hoặc COMMANDER, lấy tất cả lịch trình
            return scheduleRepository.findAll();
        }
    }

    public Schedule createSchedule(CreateScheduleRequest request) {
        // Validate time constraints
        if (request.getEstimatedTimeEnd().isBefore(request.getStartTime()) ||
                request.getEstimatedTimeEnd().isEqual(request.getStartTime())) {
            throw new IllegalArgumentException("Thời gian kết thúc phải sau thời gian bắt đầu");
        }

        // Kiểm tra thời gian không được trong quá khứ (theo múi giờ Việt Nam)
        if (DateTimeUtil.isInPast(request.getStartTime())) {
            throw new IllegalArgumentException("Thời gian bắt đầu không được trong quá khứ (múi giờ Việt Nam)");
        }

        // Validate car exists
        Car car = carRepository.findByLicensePlateNumber(request.getCarLicensePlateNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy xe với biển số: " + request.getCarLicensePlateNumber()));

        // Validate driver exists
        Driver driver = driverRepository.findByName(request.getDriverName())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Không tìm thấy tài xế với tên: " + request.getDriverName()));

        // Check overlapping schedule for car
        if (!scheduleRepository.findOverlappingByCar(car, request.getStartTime(), request.getEstimatedTimeEnd())
                .isEmpty()) {
            throw new ConflictException("Xe đã được đặt cho một chuyến đi khác trong khoảng thời gian này");
        }
        // Check overlapping schedule for driver
        if (!scheduleRepository.findOverlappingByDriver(driver, request.getStartTime(), request.getEstimatedTimeEnd())
                .isEmpty()) {
            throw new ConflictException("Tài xế đã được đặt cho một chuyến đi khác trong khoảng thời gian này");
        }

        // Kiểm tra tài xế có quyền điều khiển loại xe này không
        if (!vehicleLicenseMappingRepository.existsByTypeCarAndLicense(car.getTypeCar(), driver.getLicense())) {
            throw new DriverLicenseMismatchException(driver.getName(), car.getTypeCar().getName(),
                    driver.getLicense().getName());
        }

        // Get default status (assuming "Chưa tới giờ thực hiện nhiệm vụ" status exists)
        StatusSchedule statusSchedule = statusScheduleRepository.findByName("Chưa tới giờ thực hiện nhiệm vụ");
        if (statusSchedule == null) {
            // If no "Chưa tới giờ thực hiện nhiệm vụ" status, get the first available
            // status
            List<StatusSchedule> statuses = statusScheduleRepository.findAll();
            if (statuses.isEmpty()) {
                throw new ResourceNotFoundException("Không tìm thấy trạng thái lịch trình");
            }
            statusSchedule = statuses.get(0);
        }

        // Create new schedule
        Schedule schedule = new Schedule();
        schedule.setTripPurpose(request.getTripPurpose());
        schedule.setCar(car);
        schedule.setDriver(driver);
        schedule.setStatusSchedule(statusSchedule);
        schedule.setDeparturePoint(request.getDeparturePoint());
        schedule.setDestination(request.getDestination());
        schedule.setStartTime(request.getStartTime());
        schedule.setEstimatedTimeEnd(request.getEstimatedTimeEnd());
        schedule.setCreateDay(DateTimeUtil.getCurrentVietnamTime());

        return scheduleRepository.save(schedule);
    }

    // Tài xế hoàn thành lịch trình
    public Schedule completeSchedule(CompleteScheduleRequest request) {
        // Tìm lịch trình
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy lịch trình với ID: " + request.getScheduleId()));

        // Kiểm tra tài xế có phải là tài xế được phân công không
        if (!schedule.getDriver().getId().equals(request.getDriverId())) {
            throw new ConflictException("Bạn không có quyền hoàn thành lịch trình này");
        }

        // Kiểm tra trạng thái hiện tại - chỉ có thể hoàn thành từ trạng thái "Đang thực
        // hiện nhiệm vụ"
        if (!"Đang thực hiện nhiệm vụ".equals(schedule.getStatusSchedule().getName())) {
            throw new ConflictException("Lịch trình này không thể hoàn thành. Trạng thái hiện tại: "
                    + schedule.getStatusSchedule().getName());
        }

        // Cập nhật trạng thái thành "Đã hoàn thành nhiệm vụ"
        StatusSchedule completedStatus = statusScheduleRepository.findByName("Đã hoàn thành nhiệm vụ");
        if (completedStatus == null) {
            throw new ResourceNotFoundException("Không tìm thấy trạng thái 'Đã hoàn thành nhiệm vụ'");
        }

        schedule.setStatusSchedule(completedStatus);
        return scheduleRepository.save(schedule);
    }

}
