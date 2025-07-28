package com.example.manager_garage.config;

import com.example.manager_garage.entity.schedule.StatusSchedule;
import com.example.manager_garage.repository.StatusScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StatusScheduleDataSeeder implements CommandLineRunner {

    @Autowired
    private StatusScheduleRepository statusScheduleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (statusScheduleRepository.count() == 0) {
            // Create default status schedules
            StatusSchedule pending = new StatusSchedule();
            pending.setName("Chưa tới giờ thực hiện nhiệm vụ");
            pending.setCreateDay(LocalDateTime.now());
            statusScheduleRepository.save(pending);

            StatusSchedule completed = new StatusSchedule();
            completed.setName("Đang thực hiện nhiệm vụ");
            completed.setCreateDay(LocalDateTime.now());
            statusScheduleRepository.save(completed);

            StatusSchedule cancelled = new StatusSchedule();
            cancelled.setName("Đã hoàn thành nhiệm vụ");
            cancelled.setCreateDay(LocalDateTime.now());
            statusScheduleRepository.save(cancelled);

            System.out.println("StatusSchedule data seeded successfully!");
        }
    }
}