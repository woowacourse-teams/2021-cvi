package com.backjoongwon.cvi.publicdata.application;

import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.dto.WorldVaccinationParserResponse;
import com.backjoongwon.cvi.parser.VaccinationParser;
import com.backjoongwon.cvi.publicdata.domain.*;
import com.backjoongwon.cvi.publicdata.dto.RegionVaccinationDataFactory;
import com.backjoongwon.cvi.publicdata.dto.VaccinationStatisticResponse;
import com.backjoongwon.cvi.publicdata.dto.WorldVaccinationDataFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public List<VaccinationStatisticResponse> saveVaccinationStatistics(LocalDate targetDate) {
        VaccineParserResponse vaccineParserResponse = vacinationparser.parseToPublicData(targetDate, publicDataProperties.getVaccination());

        RegionVaccinationDataFactory regionVaccinationDataFactory = new RegionVaccinationDataFactory(vaccineParserResponse.getData());
        VaccinationStatistics vaccinationStatistics = regionVaccinationDataFactory.toVaccinationStatistics();
        List<VaccinationStatistic> foundByDate = vaccinationStatisticRepository.findByBaseDate(targetDate.toString());
        List<VaccinationStatistic> unSavedStatistics = vaccinationStatistics.findUnSavedStatistics(foundByDate, targetDate);
        return vaccinationStatisticRepository.saveAll(unSavedStatistics)
                .stream()
                .map(VaccinationStatisticResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VaccinationStatisticResponse> findVaccinationStatistics(LocalDate targetDate) {
        List<VaccinationStatistic> foundVaccinationStatistics = vaccinationStatisticRepository.findAll();
        VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(foundVaccinationStatistics);
        List<VaccinationStatistic> recentlyStatistics = vaccinationStatistics.findRecentlyStatistics(targetDate);
        return recentlyStatistics.stream()
                .map(VaccinationStatisticResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<VaccinationStatisticResponse> saveWorldVaccinationStatistics(LocalDate targetDate) {
        WorldVaccinationParserResponse worldVaccinationParserResponse = vacinationparser.parseToWorldPublicData();

        WorldVaccinationDataFactory worldVaccinationDataFactory = new WorldVaccinationDataFactory(worldVaccinationParserResponse.getData());
        VaccinationStatistics vaccinationStatistics = worldVaccinationDataFactory.toVaccinationStatistics();
        List<VaccinationStatistic> foundByRegionPopulation = vaccinationStatisticRepository.findByRegionPopulation(RegionPopulation.WORLD);
        List<VaccinationStatistic> unSavedStatistics = vaccinationStatistics.findUnSavedStatistics(foundByRegionPopulation, targetDate);
        return vaccinationStatisticRepository.saveAll(unSavedStatistics)
                .stream()
                .map(VaccinationStatisticResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VaccinationStatisticResponse> findWorldVaccinationStatistics(LocalDate targetDate) {
        List<VaccinationStatistic> foundVaccinationStatistics = vaccinationStatisticRepository.findByRegionPopulation(RegionPopulation.WORLD);
        VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(foundVaccinationStatistics);
        List<VaccinationStatistic> recentlyStatistics = vaccinationStatistics.findWorldRecentlyStatistics(targetDate);
        return recentlyStatistics.stream()
                .map(VaccinationStatisticResponse::from)
                .collect(Collectors.toList());
    }
}
