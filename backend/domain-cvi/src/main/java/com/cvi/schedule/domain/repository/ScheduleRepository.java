package com.cvi.schedule.domain.repository;

import com.cvi.schedule.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    boolean existsByName(String name);

    Optional<Schedule> findByName(String name);
}
