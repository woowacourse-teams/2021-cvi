package com.backjoongwon.cvi.publicdata.application;

import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.parser.VacinationParser;
import com.backjoongwon.cvi.publicdata.domain.PublicDataProperties;
import com.backjoongwon.cvi.publicdata.domain.VaccinationRate;
import com.backjoongwon.cvi.publicdata.domain.VaccinationRateRepository;
import com.backjoongwon.cvi.publicdata.dto.VaccinationRateResponse;
import com.backjoongwon.cvi.util.DateConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicDataService {

    private final VacinationParser vacinationparser;
    private final VaccinationRateRepository vaccinationRateRepository;
    private final PublicDataProperties publicDataProperties;

    @Transactional(readOnly = true)
    public List<VaccinationRateResponse> findVaccinationRates(LocalDateTime targetDateTime) {
        List<VaccinationRate> vaccinationRates = vaccinationRateRepository.findByBaseDate(DateConverter.withZeroTime(modifyDate(targetDateTime)));
        return vaccinationRates.stream()
                .map(VaccinationRateResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VaccinationRateResponse> saveVaccinationRates(LocalDateTime targetDateTime) {
        targetDateTime = modifyDate(targetDateTime);
        validateExists(targetDateTime);

        VaccineParserResponse vaccineParserResponse = vacinationparser.parseToPublicData(
                targetDateTime,
                publicDataProperties.getVaccination()
        );

        List<VaccinationRateResponse> vaccinationRateResponses = toVaccinationRateResponses(vaccineParserResponse);
        vaccinationRateResponses.forEach(response -> vaccinationRateRepository.save(response.toEntity()));
        return vaccinationRateResponses;
    }

    private LocalDateTime modifyDate(LocalDateTime targetDateTime) {
        if (targetDateTime.toLocalDate().isEqual(LocalDate.now()) &&
                targetDateTime.toLocalTime().isBefore(LocalTime.of(10, 0))) {
            log.info("데이터가 업데이트 되기 전입니다. 전날 기준으로 로직을 실행합니다.");
            return targetDateTime.minusDays(1);
        }
        return targetDateTime;
    }

    private List<VaccinationRateResponse> toVaccinationRateResponses(VaccineParserResponse vaccineParserResponse) {
        return vaccineParserResponse.getData()
                .stream()
                .map(VaccinationRateResponse::from)
                .collect(Collectors.toList());
    }

    private void validateExists(LocalDateTime targetDateTime) {
        if (vaccinationRateRepository.existsByBaseDate(DateConverter.withZeroTime(targetDateTime))) {
            log.info("이미 존재하는 날짜의 데이터입니다.");
            throw new DuplicateException("이미 존재하는 날짜의 데이터입니다.");
        }
    }
}
