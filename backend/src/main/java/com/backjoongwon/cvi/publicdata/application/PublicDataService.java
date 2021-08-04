package com.backjoongwon.cvi.publicdata.application;

import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.parser.VacinationParser;
import com.backjoongwon.cvi.publicdata.domain.PublicDataProperties;
import com.backjoongwon.cvi.publicdata.domain.PublicDataRepository;
import com.backjoongwon.cvi.publicdata.domain.VaccinationRate;
import com.backjoongwon.cvi.publicdata.dto.VaccinationRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicDataService {

    private final VacinationParser vacinationparser;
    private final PublicDataRepository publicDataRepository;
    private final PublicDataProperties publicDataProperties;

    @Transactional(readOnly = true)
    public List<VaccinationRateResponse> findVaccinationRates() {
        List<VaccinationRate> vaccinationRates = publicDataRepository.findByBaseDate(LocalDate.now() + " 00:00:00");
        return vaccinationRates.stream()
                .map(VaccinationRateResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VaccinationRateResponse> saveVaccinationRates() {
        VaccineParserResponse vaccineParserResponse = vacinationparser.parseToPublicData(LocalDateTime.now(), publicDataProperties.getVaccination());
        List<VaccinationRateResponse> vaccinationRateResponses = toVaccinationRateResponses(vaccineParserResponse);

        List<VaccinationRate> vaccinationRates = vaccinationRateResponses.stream()
                .map(VaccinationRateResponse::toEntity)
                .collect(Collectors.toList());
        publicDataRepository.saveAll(vaccinationRates);
        return vaccinationRateResponses;
    }

    private List<VaccinationRateResponse> toVaccinationRateResponses(VaccineParserResponse vaccineParserResponse) {
        return vaccineParserResponse.getData()
                .stream()
                .map(VaccinationRateResponse::from)
                .collect(Collectors.toList());
    }
}
