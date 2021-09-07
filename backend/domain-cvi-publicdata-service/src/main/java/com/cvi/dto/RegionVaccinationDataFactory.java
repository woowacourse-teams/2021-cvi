package com.cvi.dto;

import com.cvi.publicdata.domain.model.VaccinationStatistic;
import com.cvi.publicdata.domain.model.VaccinationStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegionVaccinationDataFactory {

    private final List<KoreaRegionVaccinationData> koreaRegionVaccinationData;

    public RegionVaccinationDataFactory(List<KoreaRegionVaccinationData> koreaRegionVaccinationData) {
        this.koreaRegionVaccinationData = new ArrayList<>(koreaRegionVaccinationData);
    }

    public VaccinationStatistics toVaccinationStatistics() {
        List<VaccinationStatistic> vaccinationStatistics = koreaRegionVaccinationData.stream()
                .map(VaccinationStatisticResponse::from)
                .map(VaccinationStatisticResponse::toEntity)
                .collect(Collectors.toList());
        return new VaccinationStatistics(vaccinationStatistics);
    }
}
