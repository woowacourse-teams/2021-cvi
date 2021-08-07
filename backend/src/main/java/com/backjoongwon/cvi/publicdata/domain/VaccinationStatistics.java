package com.backjoongwon.cvi.publicdata.domain;

import com.backjoongwon.cvi.util.DateConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VaccinationStatistics {

    private final List<VaccinationStatistic> vaccinationStatistics;

    public VaccinationStatistics(List<VaccinationStatistic> vaccinationStatistics) {
        this.vaccinationStatistics = new ArrayList<>(vaccinationStatistics);
    }

    private LocalDate toLocalDate(String targetDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDate.parse(targetDate, formatter);
    }

    public List<VaccinationStatistic> findUnSavedStatistics(List<VaccinationStatistic> foundByWorld, LocalDateTime targetDateTime) {
        LocalDate targetDate = toLocalDate(DateConverter.convertTimeToZero(targetDateTime));
        return this.vaccinationStatistics.stream()
                .filter(base -> toLocalDate(base.getBaseDate()).isBefore(targetDate) || toLocalDate(base.getBaseDate()).isEqual(targetDate))
                .filter(base -> foundByWorld.stream()
                        .noneMatch(target -> target.isSameDate(base.getBaseDate()) && target.isSameRegionPopulation(base.regionPopulation))
                )
                .collect(Collectors.toList());
    }
}
