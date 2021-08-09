package com.backjoongwon.cvi.publicdata.dto;

import com.backjoongwon.cvi.dto.RegionVaccinationData;
import com.backjoongwon.cvi.dto.WorldVaccinationData;
import com.backjoongwon.cvi.publicdata.domain.RegionPopulation;
import com.backjoongwon.cvi.publicdata.domain.VaccinationStatistic;
import com.backjoongwon.cvi.util.DateConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VaccinationStatisticResponse {

    private long accumulatedFirstCnt;
    private long accumulatedSecondCnt;
    private LocalDate baseDate;
    private long firstCnt;
    private long secondCnt;
    private String sido;
    private long totalFirstCnt;
    private long totalSecondCnt;
    private BigDecimal totalFirstRate;
    private BigDecimal totalSecondRate;

    public VaccinationStatisticResponse(long accumulatedFirstCnt, long accumulatedSecondCnt, LocalDate baseDate, long firstCnt,
                                        long secondCnt, String sido, long totalFirstCnt, long totalSecondCnt, BigDecimal totalFirstRate,
                                        BigDecimal totalSecondRate) {
        this.accumulatedFirstCnt = accumulatedFirstCnt;
        this.accumulatedSecondCnt = accumulatedSecondCnt;
        this.baseDate = baseDate;
        this.firstCnt = firstCnt;
        this.secondCnt = secondCnt;
        this.sido = sido;
        this.totalFirstCnt = totalFirstCnt;
        this.totalSecondCnt = totalSecondCnt;
        this.totalFirstRate = totalFirstRate;
        this.totalSecondRate = totalSecondRate;
    }

    public static VaccinationStatisticResponse from(RegionVaccinationData regionVaccinationData) {
        return new VaccinationStatisticResponse(regionVaccinationData.getAccumulatedFirstCnt(), regionVaccinationData.getAccumulatedSecondCnt(),
                DateConverter.convertLocalDateTimeStringToLocalDate(regionVaccinationData.getBaseDate()), regionVaccinationData.getFirstCnt(), regionVaccinationData.getSecondCnt(),
                regionVaccinationData.getSido(), regionVaccinationData.getTotalFirstCnt(), regionVaccinationData.getTotalSecondCnt(),
                null, null);
    }

    public static VaccinationStatisticResponse from(WorldVaccinationData worldVaccinationData) {
        return new VaccinationStatisticResponse(0L, 0L, DateConverter.toLocalDate(worldVaccinationData.getDate()), 0L, 0L, RegionPopulation.WORLD.getRegion(),
                worldVaccinationData.getPeople_vaccinated(), worldVaccinationData.getPeople_fully_vaccinated(), null, null
        );
    }

    public static VaccinationStatisticResponse toResponse(VaccinationStatistic vaccinationStatistic) {
        return new VaccinationStatisticResponse(vaccinationStatistic.getAccumulatedFirstCnt(), vaccinationStatistic.getAccumulatedSecondCnt(),
                vaccinationStatistic.getBaseDate(), vaccinationStatistic.getFirstCnt(), vaccinationStatistic.getSecondCnt(),
                vaccinationStatistic.getRegionPopulation().getRegion(), vaccinationStatistic.getTotalFirstCnt(), vaccinationStatistic.getTotalSecondCnt(),
                vaccinationStatistic.getTotalFirstRate(), vaccinationStatistic.getTotalSecondRate());
    }

    public VaccinationStatistic toEntity() {
        return VaccinationStatistic.builder()
                .accumulatedFirstCnt(accumulatedFirstCnt)
                .accumulatedSecondCnt(accumulatedSecondCnt)
                .baseDate(baseDate)
                .firstCnt(firstCnt)
                .secondCnt(secondCnt)
                .regionPopulation(RegionPopulation.findByRegion(sido))
                .totalFirstCnt(totalFirstCnt)
                .totalSecondCnt(totalSecondCnt)
                .totalFirstRate(totalFirstRate)
                .totalSecondRate(totalSecondRate)
                .build();
    }
}
