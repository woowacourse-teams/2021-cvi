package com.cvi.service;

import com.cvi.dto.*;
import com.cvi.parser.VaccinationParser;
import com.cvi.properties.PublicDataProperties;
import com.cvi.publicdata.domain.model.RegionPopulation;
import com.cvi.publicdata.domain.model.VaccinationStatistic;
import com.cvi.publicdata.domain.model.VaccinationStatistics;
import com.cvi.publicdata.domain.repository.VaccinationStatisticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = "cache::koreaVaccinationStatistics", key = "#targetDate.toString()")
    public List<VaccinationStatisticResponse> saveVaccinationStatistics(LocalDate targetDate) {
        KoreaVaccineParserResponse koreaVaccineParserResponse = vacinationparser.parseToKoreaPublicData(targetDate, publicDataProperties.getVaccination());

        KoreaVaccinationDataFactory koreaVaccinationDataFactory = new KoreaVaccinationDataFactory(koreaVaccineParserResponse.getData());
        VaccinationStatistics vaccinationStatistics = koreaVaccinationDataFactory.toVaccinationStatistics();
        List<VaccinationStatistic> foundByDate = vaccinationStatisticRepository.findByBaseDate(targetDate);
        List<VaccinationStatistic> unSavedStatistics = vaccinationStatistics.findUnSavedStatistics(foundByDate, targetDate);
        return vaccinationStatisticRepository.saveAll(unSavedStatistics)
            .stream()
            .map(VaccinationStatisticResponse::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "cache::koreaVaccinationStatistics", key = "#targetDate.toString()")
    public List<VaccinationStatisticResponse> findVaccinationStatistics(LocalDate targetDate) {
        List<VaccinationStatistic> foundVaccinationStatistics = vaccinationStatisticRepository.findAll();
        VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(foundVaccinationStatistics);
        List<VaccinationStatistic> recentlyStatistics = vaccinationStatistics.findKoreaRecentlyStatistics(targetDate);
        return recentlyStatistics.stream()
            .map(VaccinationStatisticResponse::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "cache::worldVaccinationStatistics", key = "#targetDate.toString()")
    public List<VaccinationStatisticResponse> saveWorldVaccinationStatistics(LocalDate targetDate) {
        WorldVaccinationParserResponse worldVaccinationParserResponse = vacinationparser.parseToWorldPublicData();

        WorldVaccinationDataFactory worldVaccinationDataFactory = new WorldVaccinationDataFactory(worldVaccinationParserResponse.getData());
        VaccinationStatistics vaccinationStatistics = worldVaccinationDataFactory.toVaccinationStatistics();
        List<VaccinationStatistic> foundByRegionPopulation = vaccinationStatisticRepository.findByRegionPopulation(RegionPopulation.WORLD);
        List<VaccinationStatistic> unSavedStatistics = vaccinationStatistics.findUnSavedStatistics(foundByRegionPopulation, targetDate);
        return vaccinationStatisticRepository.saveAll(unSavedStatistics)
            .stream()
            .map(VaccinationStatisticResponse::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "cache::worldVaccinationStatistics", key = "#targetDate.toString()")
    public List<VaccinationStatisticResponse> findWorldVaccinationStatistics(LocalDate targetDate) {
        List<VaccinationStatistic> foundVaccinationStatistics = vaccinationStatisticRepository.findByRegionPopulation(RegionPopulation.WORLD);
        VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(foundVaccinationStatistics);
        List<VaccinationStatistic> recentlyStatistics = vaccinationStatistics.findWorldRecentlyStatistics(targetDate);
        return recentlyStatistics.stream()
            .map(VaccinationStatisticResponse::toResponse)
            .collect(Collectors.toList());
    }
}
