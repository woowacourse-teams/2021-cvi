package com.cvi.controller;

import com.cvi.dto.VaccinationStatisticResponse;
import com.cvi.service.PublicDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publicdata")
public class PublicDataController {

    private final PublicDataService publicDataService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/vaccinations")
    public List<VaccinationStatisticResponse> saveVaccinationStatistics(@RequestParam(name = "targetDate", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {
        return publicDataService.saveVaccinationStatistics(targetDate);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/vaccinations")
    public List<VaccinationStatisticResponse> findVaccinationStatistics(@RequestParam(name = "targetDate", required = false, defaultValue = "#{T(java.time.LocalDate).now()}")
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {
        return publicDataService.findVaccinationStatistics(targetDate);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/vaccinations/world")
    public List<VaccinationStatisticResponse> saveWorldVaccinationStatistics(@RequestParam(name = "targetDate", required = false, defaultValue = "#{T(java.time.LocalDate).now()}")
                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {
        return publicDataService.saveWorldVaccinationStatistics(targetDate);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/vaccinations/world")
    public List<VaccinationStatisticResponse> findWorldVaccinationStatistics(@RequestParam(name = "targetDate", required = false, defaultValue = "#{T(java.time.LocalDate).now()}")
                                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {
        return publicDataService.findWorldVaccinationStatistics(targetDate);
    }
}
