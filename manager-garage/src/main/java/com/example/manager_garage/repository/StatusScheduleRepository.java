package com.example.manager_garage.repository;

import com.example.manager_garage.entity.schedule.StatusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusScheduleRepository extends JpaRepository<StatusSchedule, Long> {
    StatusSchedule findByName(String name);
}