package com.backjoongwon.cvi.publicdata.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccinationRateRepository extends JpaRepository<VaccinationRate, Long> {

    List<VaccinationRate> findByBaseDate(String baseDate);

    boolean existsByBaseDate(String baseDate);
}
