package com.backjoongwon.cvi.publicdata.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccinationStatisticRepository extends JpaRepository<VaccinationStatistic, Long> {

    List<VaccinationStatistic> findByBaseDateAndRegionPopulation(String baseDate, RegionPopulation regionPopulation);

    List<VaccinationStatistic> findByBaseDateAndRegionPopulationNot(String baseDate, RegionPopulation regionPopulation);

    boolean existsByBaseDateAndRegionPopulation(String baseDate, RegionPopulation regionPopulation);

    boolean existsByBaseDateAndRegionPopulationNot(String baseDate, RegionPopulation regionPopulation);

}
