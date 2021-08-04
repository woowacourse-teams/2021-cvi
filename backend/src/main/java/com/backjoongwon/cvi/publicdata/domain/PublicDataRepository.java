package com.backjoongwon.cvi.publicdata.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicDataRepository extends JpaRepository<PublicData, Long> {
    List<VaccinationRate> findByBaseDate(String baseDate);
}
