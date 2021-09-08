package com.backjoongwon.cvi.publicdata.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class PublicDataScheduler {

    private final PublicDataService publicDataService;

    @PostConstruct
    private void initializeVaccinationDate() {
        LocalDate today = LocalDate.now();
        log.info("한국 백신접종률 api요청 및 저장 시작:");
        publicDataService.saveVaccinationStatistics(today);
        log.info("한국 백신접종률 api요청 완료 및 데이터베이스 저장 완료");
    }

    @Scheduled(cron = "0 0 10 * * ?")
    private void scheduleKoreaVaccinationData() {
        LocalDate today = LocalDate.now();
        log.info("[스케쥴러] 한국 백신접종률 api요청 및 저장 시작:");
        publicDataService.saveVaccinationStatistics(today);
        log.info("[스케쥴러] 한국 백신접종률 api요청 완료 및 데이터베이스 저장 완료");
    }

    @Scheduled(cron = "0 20 05 * * ?")
    private void scheduleWorldVaccinationData() {
        LocalDateTime today = LocalDateTime.now();
        log.info("[스케쥴러] 세계 백신접종률 api요청 및 저장 시작. 시간: {}", today);
        publicDataService.saveWorldVaccinationStatistics(today.toLocalDate());
        log.info("[스케쥴러] 세계 api요청 완료 및 데이터베이스 저장 완료. 시간: {}", today);
    }
}
