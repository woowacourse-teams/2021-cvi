package com.backjoongwon.cvi.publicdata.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccinationStatisticRepository extends JpaRepository<VaccinationStatistic, Long> {

    List<VaccinationStatistic> findByBaseDate(String baseDate);

    List<VaccinationStatistic> findByBaseDateAndRegionPopulation(String baseDate, RegionPopulation regionPopulation);

    boolean existsByBaseDate(String baseDate);
}
