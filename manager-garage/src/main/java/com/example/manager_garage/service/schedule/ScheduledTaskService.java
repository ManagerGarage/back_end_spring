package com.example.manager_garage.service.schedule;

import com.example.manager_garage.entity.schedule.Schedule;
import com.example.manager_garage.entity.schedule.StatusSchedule;
import com.example.manager_garage.entity.car.Car;
import com.example.manager_garage.entity.car.StatusCar;
import com.example.manager_garage.entity.driver.Driver;
import com.example.manager_garage.entity.driver.StatusDriver;
import com.example.manager_garage.repository.ScheduleRepository;
import com.example.manager_garage.repository.StatusScheduleRepository;
import com.example.manager_garage.repository.CarRepository;
import com.example.manager_garage.repository.DriverRepository;
import com.example.manager_garage.repository.StatusCarRepository;
import com.example.manager_garage.repository.StatusDriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.example.manager_garage.util.DateTimeUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskService {

    private final ScheduleRepository scheduleRepository;
    private final StatusScheduleRepository statusScheduleRepository;
    private final CarRepository carRepository;
    private final DriverRepository driverRepository;
    private final StatusCarRepository statusCarRepository;
    private final StatusDriverRepository statusDriverRepository;

    // Chạy mỗi phút để kiểm tra và cập nhật trạng thái lịch trình
    @Scheduled(fixedRate = 60000) // 60000ms = 1 phút
    public void updateScheduleStatus() {
        LocalDateTime now = DateTimeUtil.getCurrentVietnamTime();

        try {
            // 1. Chuyển trạng thái từ "Chưa tới giờ thực hiện nhiệm vụ" -> "Đang thực hiện
            // nhiệm vụ"
            updateToInProgress(now);

            // 2. Chuyển trạng thái từ "Đang thực hiện nhiệm vụ" -> "Đã hoàn thành nhiệm vụ"
            // (tự động)
            updateToCompleted(now);

        } catch (Exception e) {
            log.error("Lỗi khi cập nhật trạng thái lịch trình tự động: {}", e.getMessage(), e);
        }
    }

    // Chuyển trạng thái thành "Đang thực hiện nhiệm vụ" khi tới giờ startTime
    private void updateToInProgress(LocalDateTime now) {
        StatusSchedule inProgressStatus = statusScheduleRepository.findByName("Đang thực hiện nhiệm vụ");
        StatusSchedule waitingStatus = statusScheduleRepository.findByName("Chưa tới giờ thực hiện nhiệm vụ");

        if (inProgressStatus == null || waitingStatus == null) {
            log.warn("Không tìm thấy trạng thái cần thiết cho việc cập nhật");
            return;
        }

        // Tìm các lịch trình có trạng thái "Chưa tới giờ thực hiện nhiệm vụ" và đã tới
        // giờ startTime
        List<Schedule> schedulesToUpdate = scheduleRepository.findByStatusScheduleAndStartTimeBefore(waitingStatus,
                now);

        for (Schedule schedule : schedulesToUpdate) {
            // Cập nhật trạng thái lịch trình
            schedule.setStatusSchedule(inProgressStatus);
            scheduleRepository.save(schedule);

            // Cập nhật trạng thái xe và tài xế sang "Đang công tác"
            updateCarAndDriverStatus(schedule, "Đang công tác");

            log.info(
                    "Đã tự động chuyển lịch trình ID {} từ 'Chưa tới giờ thực hiện nhiệm vụ' sang 'Đang thực hiện nhiệm vụ' và cập nhật trạng thái xe/tài xế sang 'Đang công tác'",
                    schedule.getId());
        }

        if (!schedulesToUpdate.isEmpty()) {
            log.info("Đã cập nhật {} lịch trình sang trạng thái 'Đang thực hiện nhiệm vụ'", schedulesToUpdate.size());
        }
    }

    // Cập nhật trạng thái xe và tài xế
    private void updateCarAndDriverStatus(Schedule schedule, String statusName) {
        try {
            // Cập nhật trạng thái xe
            StatusCar carStatus = statusCarRepository.findByName(statusName).orElse(null);
            if (carStatus != null) {
                Car car = schedule.getCar();
                car.setStatusCar(carStatus);
                carRepository.save(car);
                log.info("Đã cập nhật trạng thái xe {} sang '{}'", car.getLicensePlateNumber(), statusName);
            }

            // Cập nhật trạng thái tài xế
            StatusDriver driverStatus = statusDriverRepository.findByName(statusName).orElse(null);
            if (driverStatus != null) {
                Driver driver = schedule.getDriver();
                driver.setStatusDriver(driverStatus);
                driverRepository.save(driver);
                log.info("Đã cập nhật trạng thái tài xế {} sang '{}'", driver.getName(), statusName);
            }
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật trạng thái xe/tài xế: {}", e.getMessage(), e);
        }
    }

    // Chuyển trạng thái thành "Đã hoàn thành nhiệm vụ" khi quá giờ estimatedTimeEnd
    // + 30 phút buffer
    private void updateToCompleted(LocalDateTime now) {
        StatusSchedule completedStatus = statusScheduleRepository.findByName("Đã hoàn thành nhiệm vụ");
        StatusSchedule inProgressStatus = statusScheduleRepository.findByName("Đang thực hiện nhiệm vụ");

        if (completedStatus == null || inProgressStatus == null) {
            log.warn("Không tìm thấy trạng thái cần thiết cho việc cập nhật");
            return;
        }

        // Tính thời gian buffer: estimatedTimeEnd + 30 phút
        LocalDateTime bufferTime = now.minusMinutes(30);

        // Tìm các lịch trình có trạng thái "Đang thực hiện nhiệm vụ" và đã quá giờ
        // estimatedTimeEnd + 30 phút
        List<Schedule> schedulesToComplete = scheduleRepository
                .findByStatusScheduleAndEstimatedTimeEndBefore(inProgressStatus, bufferTime);

        for (Schedule schedule : schedulesToComplete) {
            // Cập nhật trạng thái lịch trình
            schedule.setStatusSchedule(completedStatus);
            scheduleRepository.save(schedule);

            // Cập nhật trạng thái xe và tài xế về "Rảnh"
            updateCarAndDriverStatus(schedule, "Rảnh");

            log.info(
                    "Đã tự động chuyển lịch trình ID {} từ 'Đang thực hiện nhiệm vụ' sang 'Đã hoàn thành nhiệm vụ' và cập nhật trạng thái xe/tài xế về 'Rảnh' (quá giờ + 30 phút buffer)",
                    schedule.getId());
        }

        if (!schedulesToComplete.isEmpty()) {
            log.info("Đã tự động hoàn thành {} lịch trình (quá giờ + 30 phút buffer)", schedulesToComplete.size());
        }
    }
}