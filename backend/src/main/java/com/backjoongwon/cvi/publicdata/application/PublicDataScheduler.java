package com.backjoongwon.cvi.publicdata.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublicDataScheduler {

    private final PublicDataService publicDataService;

    @Scheduled(cron = "0 0 10 * * ?")
    private void scheduleVaccinationData() {
        log.info("공공데이터 api요청 시작:");
        publicDataService.saveVaccinationStatistics(LocalDateTime.now());
        log.info("공공데이터 api요청 완료 및 데이터베이스 저장 완료");
    }
}
