package com.backjoongwon.cvi.publicdata.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccinationStatisticRepository extends JpaRepository<VaccinationStatistic, Long> {

    List<VaccinationStatistic> findByBaseDateAndRegionPopulationNot(String baseDate, RegionPopulation regionPopulation);

    List<VaccinationStatistic> findByRegionPopulation(RegionPopulation world);

    boolean existsByBaseDateAndRegionPopulationNot(String baseDate, RegionPopulation regionPopulation);
}
