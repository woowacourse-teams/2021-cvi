package com.backjoongwon.cvi.publicdata.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VaccinationStatistics {

    private final List<VaccinationStatistic> vaccinationStatistics;

    public VaccinationStatistics(List<VaccinationStatistic> vaccinationStatistics) {
        this.vaccinationStatistics = new ArrayList<>(vaccinationStatistics);
    }

    public List<VaccinationStatistic> findUnSavedStatistics(List<VaccinationStatistic> foundVaccinationStatistics, LocalDate targetDate) {
        return this.vaccinationStatistics.stream()
                .filter(base -> LocalDate.parse(base.getBaseDate()).isEqual(targetDate) || LocalDate.parse(base.getBaseDate()).isBefore(targetDate))
                .filter(base -> foundVaccinationStatistics.stream()
                        .noneMatch(target -> target.isSameDate(base.getBaseDate()) && target.isSameRegionPopulation(base.regionPopulation)))
                .collect(Collectors.toList());
    }

    public List<VaccinationStatistic> findWorldRecentlyStatistics(LocalDate targetDate) {
        return this.vaccinationStatistics.stream()
                .filter(base -> LocalDate.parse(base.getBaseDate()).isEqual(targetDate) || LocalDate.parse(base.getBaseDate()).isBefore(targetDate))
                .sorted(Comparator.comparing(VaccinationStatistic::getBaseDate).reversed())
                .limit(1)
                .collect(Collectors.toList());
    }

    public List<VaccinationStatistic> findRecentlyStatistics(LocalDate targetDate) {
        return this.vaccinationStatistics.stream()
                .filter(base -> LocalDate.parse(base.getBaseDate()).isEqual(targetDate) || LocalDate.parse(base.getBaseDate()).isBefore(targetDate))
                .sorted(Comparator.comparing(VaccinationStatistic::getBaseDate).reversed())
                .limit(RegionPopulation.size())
                .collect(Collectors.toList());
    }
}
