package com.backjoongwon.cvi.publicdata.dto;

import lombok.Getter;

@Getter
public class VaccinationRateResponse {
    private int accumulatedFirstCnt;
    private int accumulatedSecondCnt;
    private String baseDate;
    private int firstCnt;
    private int secondCnt;
    private String sido;
    private int totalFirstCnt;
    private int totalSecondCnt;

    public VaccinationRateResponse(int accumulatedFirstCnt, int accumulatedSecondCnt, String baseDate, int firstCnt,
                                   int secondCnt, String sido, int totalFirstCnt, int totalSecondCnt) {
        this.accumulatedFirstCnt = accumulatedFirstCnt;
        this.accumulatedSecondCnt = accumulatedSecondCnt;
        this.baseDate = baseDate;
        this.firstCnt = firstCnt;
        this.secondCnt = secondCnt;
        this.sido = sido;
        this.totalFirstCnt = totalFirstCnt;
        this.totalSecondCnt = totalSecondCnt;
    }
}
