package com.backjoongwon.cvi.publicdata.ui;

import com.backjoongwon.cvi.publicdata.application.PublicDataService;
import com.backjoongwon.cvi.publicdata.dto.VaccinationRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publicdatas")
public class PublicDataController {

    private final PublicDataService publicDataService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/vaccinations")
    public List<VaccinationRateResponse> findVaccinationRate() {
        return publicDataService.findVaccinationRates();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/vaccinations")
    public List<VaccinationRateResponse> saveVaccinationRate() {
        return publicDataService.saveVaccinationRates();
    }
}
