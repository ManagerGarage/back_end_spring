package com.example.manager_garage.controller.schedule;

import com.example.manager_garage.dto.request.CreateScheduleRequest;
import com.example.manager_garage.dto.request.CompleteScheduleRequest;
import com.example.manager_garage.entity.schedule.Schedule;
import com.example.manager_garage.service.schedule.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody CreateScheduleRequest request) {
        Schedule createdSchedule = scheduleService.createSchedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }

    // Tài xế hoàn thành lịch trình
    @PostMapping("/complete")
    public ResponseEntity<Schedule> completeSchedule(@Valid @RequestBody CompleteScheduleRequest request) {
        Schedule completedSchedule = scheduleService.completeSchedule(request);
        return ResponseEntity.ok(completedSchedule);
    }
}