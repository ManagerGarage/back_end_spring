package com.example.manager_garage.repository;

import com.example.manager_garage.entity.schedule.Schedule;
import com.example.manager_garage.entity.schedule.StatusSchedule;
import com.example.manager_garage.entity.car.Car;
import com.example.manager_garage.entity.driver.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
        @Query("SELECT s FROM Schedule s WHERE s.car = :car AND ((:startTime < s.estimatedTimeEnd) AND (:estimatedTimeEnd > s.startTime))")
        List<Schedule> findOverlappingByCar(@Param("car") Car car, @Param("startTime") LocalDateTime startTime,
                        @Param("estimatedTimeEnd") LocalDateTime estimatedTimeEnd);

        @Query("SELECT s FROM Schedule s WHERE s.driver = :driver AND ((:startTime < s.estimatedTimeEnd) AND (:estimatedTimeEnd > s.startTime))")
        List<Schedule> findOverlappingByDriver(@Param("driver") Driver driver,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("estimatedTimeEnd") LocalDateTime estimatedTimeEnd);

        // Tìm lịch trình theo trạng thái và thời gian bắt đầu
        List<Schedule> findByStatusScheduleAndStartTimeBefore(StatusSchedule statusSchedule, LocalDateTime startTime);

        // Tìm lịch trình theo trạng thái và thời gian kết thúc
        List<Schedule> findByStatusScheduleAndEstimatedTimeEndBefore(StatusSchedule statusSchedule,
                        LocalDateTime estimatedTimeEnd);
}
