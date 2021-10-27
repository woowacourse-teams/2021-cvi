package com.cvi.service.scheduler;

import com.cvi.service.PublicDataService;
import com.cvi.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class ScheduledTasks {

    private static final String PUBLIC_DATA = "Public Data";

    private final PublicDataService publicDataService;
    private final ScheduleService scheduleService;

    @PostConstruct
    private void initializeVaccinationDate() {
        scheduleService.saveSchedule(PUBLIC_DATA);
        scheduleService.activeSchedule(PUBLIC_DATA, scheduleKoreaVaccine("[초기화]"));
        scheduleService.activeSchedule(PUBLIC_DATA, scheduleWorldVaccine("[초기화]"));
    }

    @Scheduled(cron = "0 0 10 * * ?")
    private void scheduleKoreaVaccinationData() {
        scheduleService.activeSchedule(PUBLIC_DATA, scheduleKoreaVaccine("[스케쥴러]"));
    }

    @Scheduled(cron = "0 20 05 * * ?")
    private void scheduleWorldVaccinationData() {
        scheduleService.activeSchedule(PUBLIC_DATA, scheduleWorldVaccine("[스케쥴러]"));
    }

    private Scheduler scheduleKoreaVaccine(String message) {
        return () -> {
            LocalDate today = LocalDate.now();
            log.info("{} 한국 백신접종률 api요청 및 저장 시작. 시간: {}:", message, today);
            publicDataService.saveVaccinationStatistics(today);
            log.info("{} 한국 백신접종률 api요청 완료 및 데이터베이스 저장 완료. 시간: {}", message, today);
        };
    }

    private Scheduler scheduleWorldVaccine(String message) {
        return () -> {
            LocalDate today = LocalDate.now();
            log.info("{} 세계 백신접종률 api요청 및 저장 시작. 시간: {}:", message, today);
            publicDataService.saveWorldVaccinationStatistics(today);
            log.info("{} 세계 api요청 완료 및 데이터베이스 저장 완료. 시간: {}", message, today);
        };
    }
}
