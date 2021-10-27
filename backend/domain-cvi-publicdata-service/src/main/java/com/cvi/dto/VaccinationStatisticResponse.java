package com.cvi.dto;

import com.cvi.publicdata.domain.model.RegionPopulation;
import com.cvi.publicdata.domain.model.VaccinationStatistic;
import com.cvi.util.DateConverter;
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

    public static VaccinationStatisticResponse from(KoreaRegionVaccinationData koreaRegionVaccinationData) {
        return new VaccinationStatisticResponse(koreaRegionVaccinationData.getAccumulatedFirstCnt(), koreaRegionVaccinationData.getAccumulatedSecondCnt(),
            DateConverter.convertLocalDateTimeStringToLocalDate(koreaRegionVaccinationData.getBaseDate()), koreaRegionVaccinationData.getFirstCnt(), koreaRegionVaccinationData.getSecondCnt(),
            koreaRegionVaccinationData.getSido(), koreaRegionVaccinationData.getTotalFirstCnt(), koreaRegionVaccinationData.getTotalSecondCnt(),
            null, null);
    }

    public static VaccinationStatisticResponse from(WorldVaccinationData worldVaccinationData) {
        return new VaccinationStatisticResponse(0L, 0L, DateConverter.convertLocalDateStringToLocalDate(worldVaccinationData.getDate()), 0L, 0L, RegionPopulation.WORLD.getRegion(),
            worldVaccinationData.getPeopleVaccinated(), worldVaccinationData.getPeopleFullyVaccinated(), null, null
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
