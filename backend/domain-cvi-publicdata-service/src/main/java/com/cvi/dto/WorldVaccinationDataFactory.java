package com.cvi.dto;

import com.cvi.publicdata.domain.model.VaccinationStatistic;
import com.cvi.publicdata.domain.model.VaccinationStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorldVaccinationDataFactory {

    private final List<WorldVaccinationData> worldVaccinationDataGroup;

    public WorldVaccinationDataFactory(List<WorldVaccinationData> worldVaccinationDataGroup) {
        this.worldVaccinationDataGroup = new ArrayList<>(worldVaccinationDataGroup);
    }

    public VaccinationStatistics toVaccinationStatistics() {
        List<VaccinationStatistic> vaccinationStatistics = worldVaccinationDataGroup.stream()
                .map(VaccinationStatisticResponse::from)
                .map(VaccinationStatisticResponse::toEntity)
                .collect(Collectors.toList());
        return new VaccinationStatistics(vaccinationStatistics);
    }
}
