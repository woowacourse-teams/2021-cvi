package com.backjoongwon.cvi.publicdata.application;

import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.dto.WorldVaccinationParserResponse;
import com.backjoongwon.cvi.parser.VaccinationParser;
import com.backjoongwon.cvi.publicdata.domain.*;
import com.backjoongwon.cvi.publicdata.dto.RegionVaccinationDataFactory;
import com.backjoongwon.cvi.publicdata.dto.VaccinationStatisticResponse;
import com.backjoongwon.cvi.publicdata.dto.WorldVaccinationDataFactory;
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

    private final VaccinationParser vacinationparser;
    private final VaccinationStatisticRepository vaccinationStatisticRepository;
    private final PublicDataProperties publicDataProperties;

    @Transactional
    public List<VaccinationStatisticResponse> saveVaccinationStatistics(LocalDateTime targetDateTime) {
        VaccineParserResponse vaccineParserResponse = vacinationparser.parseToPublicData(
                targetDateTime,
                publicDataProperties.getVaccination()
        );

        RegionVaccinationDataFactory regionVaccinationDataFactory = new RegionVaccinationDataFactory(vaccineParserResponse.getData());
        VaccinationStatistics vaccinationStatistics = regionVaccinationDataFactory.toVaccinationStatistics();
        List<VaccinationStatistic> foundByDate = vaccinationStatisticRepository.findByBaseDate(DateConverter.convertTimeToZero(targetDateTime));
        List<VaccinationStatistic> unSavedStatistics = vaccinationStatistics.findUnSavedStatistics(foundByDate, targetDateTime);
        return vaccinationStatisticRepository.saveAll(unSavedStatistics)
                .stream()
                .map(VaccinationStatisticResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VaccinationStatisticResponse> findVaccinationStatistics(LocalDateTime targetDateTime) {
        List<VaccinationStatistic> foundVaccinationStatistics = vaccinationStatisticRepository.findAll();
        VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(foundVaccinationStatistics);
        List<VaccinationStatistic> recentlyStatistics = vaccinationStatistics.findRecentlyStatistics(targetDateTime);
        return recentlyStatistics.stream()
                .map(VaccinationStatisticResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VaccinationStatisticResponse> saveWorldVaccinationStatistics(LocalDateTime targetDateTime) {
        WorldVaccinationParserResponse worldVaccinationParserResponse = vacinationparser.parseToWorldPublicData();

        WorldVaccinationDataFactory worldVaccinationDataFactory = new WorldVaccinationDataFactory(worldVaccinationParserResponse.getData());
        VaccinationStatistics vaccinationStatistics = worldVaccinationDataFactory.toVaccinationStatistics();
        List<VaccinationStatistic> foundByRegionPopulation = vaccinationStatisticRepository.findByRegionPopulation(RegionPopulation.WORLD);
        List<VaccinationStatistic> unSavedStatistics = vaccinationStatistics.findUnSavedStatistics(foundByRegionPopulation, targetDateTime);
        return vaccinationStatisticRepository.saveAll(unSavedStatistics)
                .stream()
                .map(VaccinationStatisticResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VaccinationStatisticResponse> findWorldVaccinationStatistics(LocalDateTime targetDateTime) {
        List<VaccinationStatistic> foundVaccinationStatistics = vaccinationStatisticRepository.findByRegionPopulation(RegionPopulation.WORLD);
        VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(foundVaccinationStatistics);
        List<VaccinationStatistic> recentlyStatistics = vaccinationStatistics.findWorldRecentlyStatistics(targetDateTime);
        return recentlyStatistics.stream()
                .map(VaccinationStatisticResponse::from)
                .collect(Collectors.toList());
    }
}
