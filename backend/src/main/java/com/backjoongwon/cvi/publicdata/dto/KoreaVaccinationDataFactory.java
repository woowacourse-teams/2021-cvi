package com.backjoongwon.cvi.publicdata.dto;

import com.backjoongwon.cvi.dto.KoreaRegionVaccinationData;
import com.backjoongwon.cvi.publicdata.domain.VaccinationStatistic;
import com.backjoongwon.cvi.publicdata.domain.VaccinationStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KoreaVaccinationDataFactory {

    private final List<KoreaRegionVaccinationData> koreaRegionVaccinationData;

    public KoreaVaccinationDataFactory(List<KoreaRegionVaccinationData> koreaRegionVaccinationData) {
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
