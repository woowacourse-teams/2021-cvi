package com.cvi.service;

import com.cvi.exception.NotFoundException;
import com.cvi.schedule.domain.model.Schedule;
import com.cvi.schedule.domain.repository.ScheduleRepository;
import com.cvi.service.scheduler.Scheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * Exception을 던지지 않는 이유는, 현재 스케쥴러 저장을 @PostConstruct에서 하고 잇는데,
     * 이때 예외가 발생하면, 애플리케이션 실행이 되지 않기 때문입니다.
     */
    @Transactional
    public void saveSchedule(String name) {
        if (scheduleRepository.existsByName(name)) {
            log.info("이미 존재하는 스케쥴러이기에 저장되지 않았습니다.. 입력 값: {}", name);
            return;
        }
        final Schedule build = Schedule.builder()
                .name(name)
                .build();
        scheduleRepository.save(build);
    }

    @Transactional
    public void activeSchedule(String name, Scheduler scheduler) {
        final Schedule schedule = findScheduleByName(name);
        schedule.reversRunningState();
        scheduleRepository.flush();
        if (schedule.isRunning()) {
            scheduler.doTask();
            schedule.reversRunningState();
        }
    }

    private Schedule findScheduleByName(String name) {
        final Optional<Schedule> schedule = scheduleRepository.findByName(name);
        if (schedule.isPresent()) {
            return schedule.get();
        }
        log.info("존재하지 않는 스케쥴러 입니다. 입력 값: {}", name);
        throw new NotFoundException("존재하지 않는 스케쥴러 입니다. 입력 값: " + name);
    }
}
