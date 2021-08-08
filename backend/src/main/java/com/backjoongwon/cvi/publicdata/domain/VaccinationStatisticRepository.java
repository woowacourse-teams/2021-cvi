package com.backjoongwon.cvi.publicdata.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccinationStatisticRepository extends JpaRepository<VaccinationStatistic, Long> {

    List<VaccinationStatistic> findByRegionPopulation(RegionPopulation regionPopulation);

    List<VaccinationStatistic> findByBaseDate(String baseDate);
}
