package com.backjoongwon.cvi.dto;

public class RegionVaccinationData {

    private int accumulatedFirstCnt;
    private int accumulatedSecondCnt;
    private String baseDate;
    private int firstCnt;
    private int secondCnt;
    private String sido;
    private int totalFirstCnt;
    private int totalSecondCnt;

    public RegionVaccinationData() {
    }

    public RegionVaccinationData(int accumulatedFirstCnt, int accumulatedSecondCnt, String baseDate, int firstCnt,
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

    public int getAccumulatedFirstCnt() {
        return accumulatedFirstCnt;
    }

    public int getAccumulatedSecondCnt() {
        return accumulatedSecondCnt;
    }

    public String getBaseDate() {
        return baseDate;
    }

    public int getFirstCnt() {
        return firstCnt;
    }

    public int getSecondCnt() {
        return secondCnt;
    }

    public String getSido() {
        return sido;
    }

    public int getTotalFirstCnt() {
        return totalFirstCnt;
    }

    public int getTotalSecondCnt() {
        return totalSecondCnt;
    }
}
