package com.cvi.publicdata.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VaccinationStatistics {

    private final List<VaccinationStatistic> vaccinationStatistics;

    public VaccinationStatistics(List<VaccinationStatistic> vaccinationStatistics) {
        this.vaccinationStatistics = new ArrayList<>(vaccinationStatistics);
    }

    public List<VaccinationStatistic> findUnSavedStatistics(List<VaccinationStatistic> foundVaccinationStatistics, LocalDate targetDate) {
        return this.vaccinationStatistics.stream()
            .filter(isSameOrBefore(targetDate))
            .filter(base -> foundVaccinationStatistics.stream()
                .noneMatch(target -> target.isSameDate(base.getBaseDate()) && target.isSameRegionPopulation(base.regionPopulation)))
            .collect(Collectors.toList());
    }

    public List<VaccinationStatistic> findKoreaRecentlyStatistics(LocalDate targetDate) {
        return this.vaccinationStatistics.stream()
            .filter(isSameOrBefore(targetDate).and(isWorldRegion().negate()))
            .sorted(Comparator.comparing(VaccinationStatistic::getBaseDate).reversed())
            .limit(RegionPopulation.size() - 1)
            .sorted(Comparator.comparing(VaccinationStatistic::getRegionPopulation))
            .collect(Collectors.toList());
    }

    public List<VaccinationStatistic> findWorldRecentlyStatistics(LocalDate targetDate) {
        return this.vaccinationStatistics.stream()
            .filter(isSameOrBefore(targetDate).and(isWorldRegion()))
            .sorted(Comparator.comparing(VaccinationStatistic::getBaseDate).reversed())
            .limit(1)
            .collect(Collectors.toList());
    }

    private Predicate<VaccinationStatistic> isSameOrBefore(LocalDate targetDate) {
        return vaccinationStatistic -> vaccinationStatistic.getBaseDate().isEqual(targetDate) || vaccinationStatistic.getBaseDate().isBefore(targetDate);
    }

    private Predicate<VaccinationStatistic> isWorldRegion() {
        return vaccinationStatistic -> vaccinationStatistic.isSameRegionPopulation(RegionPopulation.WORLD);
    }
}
