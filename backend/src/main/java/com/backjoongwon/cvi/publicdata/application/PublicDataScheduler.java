package com.backjoongwon.cvi.publicdata.application;

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
public class PublicDataScheduler {

    private final PublicDataService publicDataService;

    @PostConstruct
    private void initializeVaccinationDate() {
        LocalDate today = LocalDate.now();
        log.info("백신접종률 api요청 및 저장 시작:");
        publicDataService.saveVaccinationStatistics(today);
        log.info("세계 백신접종률 api요청 및 저장 시작:");
        publicDataService.saveWorldVaccinationStatistics(today);
        log.info("백신접종률 api요청 완료 및 데이터베이스 저장 완료");
    }

    @Scheduled(cron = "0 0 10 * * ?")
    private void scheduleVaccinationData() {
        LocalDate today = LocalDate.now();
        log.info("백신접종률 api요청 및 저장 시작:");
        publicDataService.saveVaccinationStatistics(today);
        log.info("세계 백신접종률 api요청 및 저장 시작:");
        publicDataService.saveWorldVaccinationStatistics(today);
        log.info("백신접종률 api요청 완료 및 데이터베이스 저장 완료");
    }
}