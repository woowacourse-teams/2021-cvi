package com.backjoongwon.cvi.publicdata.dto;

import com.backjoongwon.cvi.dto.RegionVaccinationData;
import com.backjoongwon.cvi.dto.WorldVaccinationData;
import com.backjoongwon.cvi.dto.WorldVaccinationParserResponse;
import com.backjoongwon.cvi.publicdata.domain.RegionPopulation;
import com.backjoongwon.cvi.publicdata.domain.VaccinationStatistic;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VaccinationStatisticResponse {

    private long accumulatedFirstCnt;
    private long accumulatedSecondCnt;
    private String baseDate;
    private long firstCnt;
    private long secondCnt;
    private String sido;
    private long totalFirstCnt;
    private long totalSecondCnt;
    private BigDecimal accumulatedFirstRate;
    private BigDecimal accumulatedSecondRate;

    public VaccinationStatisticResponse(long accumulatedFirstCnt, long accumulatedSecondCnt, String baseDate, long firstCnt,
                                        long secondCnt, String sido, long totalFirstCnt, long totalSecondCnt, BigDecimal accumulatedFirstRate,
                                        BigDecimal accumulatedSecondRate) {
        this.accumulatedFirstCnt = accumulatedFirstCnt;
        this.accumulatedSecondCnt = accumulatedSecondCnt;
        this.baseDate = baseDate;
        this.firstCnt = firstCnt;
        this.secondCnt = secondCnt;
        this.sido = sido;
        this.totalFirstCnt = totalFirstCnt;
        this.totalSecondCnt = totalSecondCnt;
        this.accumulatedFirstRate = accumulatedFirstRate;
        this.accumulatedSecondRate = accumulatedSecondRate;
    }

    public static VaccinationStatisticResponse from(RegionVaccinationData regionVaccinationData) {
        return new VaccinationStatisticResponse(regionVaccinationData.getAccumulatedFirstCnt(), regionVaccinationData.getAccumulatedSecondCnt(),
                regionVaccinationData.getBaseDate(), regionVaccinationData.getFirstCnt(), regionVaccinationData.getSecondCnt(),
                regionVaccinationData.getSido(), regionVaccinationData.getTotalFirstCnt(), regionVaccinationData.getTotalSecondCnt(),
                null, null);
    }

    public static VaccinationStatisticResponse from(VaccinationStatistic vaccinationStatistic) {
        return new VaccinationStatisticResponse(vaccinationStatistic.getAccumulatedFirstCnt(), vaccinationStatistic.getAccumulatedSecondCnt(),
                vaccinationStatistic.getBaseDate(), vaccinationStatistic.getFirstCnt(), vaccinationStatistic.getSecondCnt(),
                vaccinationStatistic.getRegionPopulation().getRegion(), vaccinationStatistic.getTotalFirstCnt(), vaccinationStatistic.getTotalSecondCnt(),
                vaccinationStatistic.getAccumulatedFirstRate(), vaccinationStatistic.getAccumulatedSecondRate());
    }

    public static VaccinationStatisticResponse from(WorldVaccinationData worldVaccinationData) {
        return new VaccinationStatisticResponse(0L, 0L, worldVaccinationData.getDate() + " 00:00:00", 0L, 0L, RegionPopulation.WORLD.getRegion(),
                worldVaccinationData.getPeople_vaccinated(), worldVaccinationData.getPeople_fully_vaccinated(), null, null
        );
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
                .accumulatedFirstRate(accumulatedFirstRate)
                .accumulatedSecondRate(accumulatedSecondRate)
                .build();
    }
}
