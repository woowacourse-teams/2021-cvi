package com.cvi.publicdata.domain.repository;

import com.cvi.publicdata.domain.model.RegionPopulation;
import com.cvi.publicdata.domain.model.VaccinationStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VaccinationStatisticRepository extends JpaRepository<VaccinationStatistic, Long> {

    List<VaccinationStatistic> findByRegionPopulation(RegionPopulation regionPopulation);

    List<VaccinationStatistic> findByBaseDate(LocalDate baseDate);
}
