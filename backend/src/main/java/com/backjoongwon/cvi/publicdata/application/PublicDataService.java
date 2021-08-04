package com.backjoongwon.cvi.publicdata.application;

import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.parser.VacinationParser;
import com.backjoongwon.cvi.publicdata.domain.PublicDataProperties;
import com.backjoongwon.cvi.publicdata.domain.VaccinationRate;
import com.backjoongwon.cvi.publicdata.domain.VaccinationRateRepository;
import com.backjoongwon.cvi.publicdata.dto.VaccinationRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicDataService {

    private final VacinationParser vacinationparser;
    private final VaccinationRateRepository vaccinationRateRepository;
    private final PublicDataProperties publicDataProperties;

    @Transactional(readOnly = true)
    public List<VaccinationRateResponse> findVaccinationRates(LocalDate targetDate) {
        LocalDateTime nowDateTime = LocalDateTime.now();

        if (targetDate.isEqual(nowDateTime.toLocalDate()) &&
                nowDateTime.toLocalTime().isBefore(LocalTime.of(10, 0))) {
            targetDate = targetDate.minusDays(1);
        }

        List<VaccinationRate> vaccinationRates = vaccinationRateRepository.findByBaseDate(targetDate + " 00:00:00");
        return vaccinationRates.stream()
                .map(VaccinationRateResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VaccinationRateResponse> saveVaccinationRates(LocalDate targetDate) {
        LocalDateTime nowDateTime = LocalDateTime.now();

        if (targetDate.isEqual(nowDateTime.toLocalDate()) &&
                nowDateTime.toLocalTime().isBefore(LocalTime.of(10, 0))) {
            targetDate = targetDate.minusDays(1);
        }

        if (vaccinationRateRepository.existsByBaseDate(targetDate + " 00:00:00")) {
            throw new DuplicateException("이미 존재하는 날짜의 데이터입니다.");
        }

        VaccineParserResponse vaccineParserResponse = vacinationparser.parseToPublicData(LocalDateTime.now(), publicDataProperties.getVaccination());
        List<VaccinationRateResponse> vaccinationRateResponses = toVaccinationRateResponses(vaccineParserResponse);

        List<VaccinationRate> vaccinationRates = vaccinationRateResponses.stream()
                .map(VaccinationRateResponse::toEntity)
                .collect(Collectors.toList());
        vaccinationRateRepository.saveAll(vaccinationRates);
        return vaccinationRateResponses;
    }

    private List<VaccinationRateResponse> toVaccinationRateResponses(VaccineParserResponse vaccineParserResponse) {
        return vaccineParserResponse.getData()
                .stream()
                .map(VaccinationRateResponse::from)
                .collect(Collectors.toList());
    }
}
