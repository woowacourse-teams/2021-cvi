package com.backjoongwon.cvi.publicdata.dto;

import com.backjoongwon.cvi.dto.RegionVaccinationData;
import com.backjoongwon.cvi.publicdata.domain.VaccinationRate;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VaccinationRateResponse {
    private static final int KOREA_POPULATION =  51_821_669;

    private int accumulatedFirstCnt;
    private int accumulatedSecondCnt;
    private String baseDate;
    private int firstCnt;
    private int secondCnt;
    private String sido;
    private int totalFirstCnt;
    private int totalSecondCnt;
    private int accumulateRate;

    public VaccinationRateResponse(int accumulatedFirstCnt, int accumulatedSecondCnt, String baseDate, int firstCnt,
                                   int secondCnt, String sido, int totalFirstCnt, int totalSecondCnt, int accumulateRate) {
        this.accumulatedFirstCnt = accumulatedFirstCnt;
        this.accumulatedSecondCnt = accumulatedSecondCnt;
        this.baseDate = baseDate;
        this.firstCnt = firstCnt;
        this.secondCnt = secondCnt;
        this.sido = sido;
        this.totalFirstCnt = totalFirstCnt;
        this.totalSecondCnt = totalSecondCnt;
        this.accumulateRate = accumulateRate;
    }

    public static VaccinationRateResponse from(RegionVaccinationData regionVaccinationData) {
        return new VaccinationRateResponse(regionVaccinationData.getAccumulatedFirstCnt(), regionVaccinationData.getAccumulatedSecondCnt(),
                regionVaccinationData.getBaseDate(), regionVaccinationData.getFirstCnt(), regionVaccinationData.getSecondCnt(),
                regionVaccinationData.getSido(), regionVaccinationData.getTotalFirstCnt(), regionVaccinationData.getTotalSecondCnt(),
                calculatePercent(regionVaccinationData.getAccumulatedFirstCnt())
        );
    }

    public static VaccinationRateResponse from(VaccinationRate vaccinationRate) {
        return new VaccinationRateResponse(vaccinationRate.getAccumulatedFirstCnt(), vaccinationRate.getAccumulatedSecondCnt(),
                vaccinationRate.getBaseDate(), vaccinationRate.getFirstCnt(), vaccinationRate.getSecondCnt(),
                vaccinationRate.getSido(), vaccinationRate.getTotalFirstCnt(), vaccinationRate.getTotalSecondCnt(),
                calculatePercent(vaccinationRate.getAccumulatedFirstCnt()));
    }


    private static int calculatePercent(int accumulatedFirstCnt) {
        return accumulatedFirstCnt * 100 / KOREA_POPULATION;
    }

    public VaccinationRate toEntity() {
        return VaccinationRate.builder()
                .accumulatedFirstCnt(accumulatedFirstCnt)
                .accumulatedSecondCnt(accumulatedSecondCnt)
                .baseDate(baseDate)
                .firstCnt(firstCnt)
                .secondCnt(secondCnt)
                .sido(sido)
                .totalFirstCnt(totalFirstCnt)
                .totalSecondCnt(totalSecondCnt)
                .accumulateRate(accumulateRate)
                .build();
    }
}
