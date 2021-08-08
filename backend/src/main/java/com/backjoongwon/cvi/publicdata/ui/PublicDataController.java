package com.backjoongwon.cvi.publicdata.ui;

import com.backjoongwon.cvi.publicdata.application.PublicDataService;
import com.backjoongwon.cvi.publicdata.dto.VaccinationStatisticResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publicdata")
public class PublicDataController {

    private final PublicDataService publicDataService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/vaccinations")
    public List<VaccinationStatisticResponse> saveVaccinationStatistics(@RequestParam(name = "targetDate", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDateTime) {
        return publicDataService.saveVaccinationStatistics(targetDateTime);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/vaccinations")
    public List<VaccinationStatisticResponse> findVaccinationStatistics(@RequestParam(name = "targetDate", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDateTime) {
        return publicDataService.findVaccinationStatistics(targetDateTime);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/vaccinations/world")
    public List<VaccinationStatisticResponse> saveWorldVaccinationStatistics(@RequestParam(name = "targetDate", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDateTime) {
        return publicDataService.saveWorldVaccinationStatistics(targetDateTime);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/vaccinations/world")
    public List<VaccinationStatisticResponse> findWorldVaccinationStatistics(@RequestParam(name = "targetDate", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDateTime) {
        return publicDataService.findWorldVaccinationStatistics(targetDateTime);
    }
}
