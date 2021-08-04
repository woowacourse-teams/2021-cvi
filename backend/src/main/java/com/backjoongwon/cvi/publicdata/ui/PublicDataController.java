package com.backjoongwon.cvi.publicdata.ui;

import com.backjoongwon.cvi.publicdata.application.PublicDataService;
import com.backjoongwon.cvi.publicdata.dto.VaccinationRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publicdatas")
public class PublicDataController {

    private final PublicDataService publicDataService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/vaccinations")
    public List<VaccinationRateResponse> findVaccinationRate(@RequestParam(name = "targetDate", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDateTime) {
        return publicDataService.findVaccinationRates(targetDateTime);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/vaccinations")
    public List<VaccinationRateResponse> saveVaccinationRate(@RequestParam(name = "targetDate", required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDateTime) {
        return publicDataService.saveVaccinationRates(targetDateTime);
    }
}
