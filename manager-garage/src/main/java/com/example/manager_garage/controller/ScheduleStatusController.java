package com.example.manager_garage.controller;

import com.example.manager_garage.entity.schedule.Schedule;
import com.example.manager_garage.entity.schedule.StatusSchedule;
import com.example.manager_garage.repository.ScheduleRepository;
import com.example.manager_garage.repository.StatusScheduleRepository;
import com.example.manager_garage.service.schedule.ScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedule-status")
public class ScheduleStatusController {

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private StatusScheduleRepository statusScheduleRepository;

    @PostMapping("/trigger-update")
    public ResponseEntity<Map<String, Object>> triggerStatusUpdate() {
        Map<String, Object> response = new HashMap<>();

        try {
            // Trigger manual update
            scheduledTaskService.updateScheduleStatus();
            response.put("message", "Đã trigger cập nhật trạng thái lịch trình thành công");
            response.put("success", true);
        } catch (Exception e) {
            response.put("message", "Lỗi khi trigger cập nhật: " + e.getMessage());
            response.put("success", false);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getScheduleStatusSummary() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<StatusSchedule> allStatuses = statusScheduleRepository.findAll();
            Map<String, Long> statusCounts = new HashMap<>();

            for (StatusSchedule status : allStatuses) {
                long count = scheduleRepository.countByStatusSchedule(status);
                statusCounts.put(status.getName(), count);
            }

            response.put("statusCounts", statusCounts);
            response.put("totalSchedules", scheduleRepository.count());
            response.put("success", true);
        } catch (Exception e) {
            response.put("message", "Lỗi khi lấy thống kê: " + e.getMessage());
            response.put("success", false);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-status/{statusName}")
    public ResponseEntity<List<Schedule>> getSchedulesByStatus(@PathVariable String statusName) {
        StatusSchedule status = statusScheduleRepository.findByName(statusName);
        if (status == null) {
            return ResponseEntity.notFound().build();
        }

        List<Schedule> schedules = scheduleRepository.findByStatusSchedule(status);
        return ResponseEntity.ok(schedules);
    }
}