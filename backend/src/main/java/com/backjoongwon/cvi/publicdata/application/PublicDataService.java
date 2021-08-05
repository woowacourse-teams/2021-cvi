package com.backjoongwon.cvi.publicdata.application;

import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.parser.VacinationParser;
import com.backjoongwon.cvi.publicdata.domain.PublicDataProperties;
import com.backjoongwon.cvi.publicdata.domain.VaccinationStatistic;
import com.backjoongwon.cvi.publicdata.domain.VaccinationStatisticRepository;
import com.backjoongwon.cvi.publicdata.dto.VaccinationStatisticResponse;
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
    private final VaccinationStatisticRepository vaccinationStatisticRepository;
    private final PublicDataProperties publicDataProperties;

    @Transactional(readOnly = true)
    public List<VaccinationStatisticResponse> findVaccinationStatistics(LocalDateTime targetDateTime) {
        List<VaccinationStatistic> vaccinationStatistic = vaccinationStatisticRepository.findByBaseDate(DateConverter.withZeroTime(modifyDate(targetDateTime)));
        return vaccinationStatistic.stream()
                .map(VaccinationStatisticResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VaccinationStatisticResponse> saveVaccinationStatistics(LocalDateTime targetDateTime) {
        targetDateTime = modifyDate(targetDateTime);
        validateExists(targetDateTime);

        VaccineParserResponse vaccineParserResponse = vacinationparser.parseToPublicData(
                targetDateTime,
                publicDataProperties.getVaccination()
        );

        List<VaccinationStatisticResponse> vaccinationStatisticResponse = toVaccinationStatisticResponses(vaccineParserResponse);
        vaccinationStatisticResponse.forEach(response -> vaccinationStatisticRepository.save(response.toEntity()));
        return vaccinationStatisticResponse;
    }

    private LocalDateTime modifyDate(LocalDateTime targetDateTime) {
        if (targetDateTime.toLocalDate().isEqual(LocalDate.now()) &&
                targetDateTime.toLocalTime().isBefore(LocalTime.of(10, 0))) {
            log.info("데이터가 업데이트 되기 전입니다. 전날 기준으로 로직을 실행합니다.");
            return targetDateTime.minusDays(1);
        }
        return targetDateTime;
    }

    private List<VaccinationStatisticResponse> toVaccinationStatisticResponses(VaccineParserResponse vaccineParserResponse) {
        return vaccineParserResponse.getData()
                .stream()
                .map(VaccinationStatisticResponse::from)
                .collect(Collectors.toList());
    }

    private void validateExists(LocalDateTime targetDateTime) {
        if (vaccinationStatisticRepository.existsByBaseDate(DateConverter.withZeroTime(targetDateTime))) {
            log.info("이미 존재하는 날짜의 데이터입니다.");
            throw new DuplicateException("이미 존재하는 날짜의 데이터입니다.");
        }
    }
}
