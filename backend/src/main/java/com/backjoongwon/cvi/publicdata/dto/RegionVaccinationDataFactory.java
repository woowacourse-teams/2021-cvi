package com.backjoongwon.cvi.publicdata.dto;

import com.backjoongwon.cvi.dto.RegionVaccinationData;
import com.backjoongwon.cvi.publicdata.domain.VaccinationStatistic;
import com.backjoongwon.cvi.publicdata.domain.VaccinationStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegionVaccinationDataFactory {

    private final List<RegionVaccinationData> regionVaccinationData;

    public RegionVaccinationDataFactory(List<RegionVaccinationData> regionVaccinationData) {
        this.regionVaccinationData = new ArrayList<>(regionVaccinationData);
    }

    public VaccinationStatistics toVaccinationStatistics() {
        List<VaccinationStatistic> vaccinationStatistics = regionVaccinationData.stream()
                .map(VaccinationStatisticResponse::from)
                .map(VaccinationStatisticResponse::toEntity)
                .collect(Collectors.toList());
        return new VaccinationStatistics(vaccinationStatistics);
    }
}
