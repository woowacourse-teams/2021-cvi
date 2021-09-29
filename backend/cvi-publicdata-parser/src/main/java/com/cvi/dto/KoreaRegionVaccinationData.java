package com.cvi.dto;

public class KoreaRegionVaccinationData {

    private long accumulatedFirstCnt;
    private long accumulatedSecondCnt;
    private String baseDate;
    private long firstCnt;
    private long secondCnt;
    private String sido;
    private long totalFirstCnt;
    private long totalSecondCnt;

    public KoreaRegionVaccinationData() {
    }

    public KoreaRegionVaccinationData(long accumulatedFirstCnt, long accumulatedSecondCnt, String baseDate, long firstCnt,
                                      long secondCnt, String sido, long totalFirstCnt, long totalSecondCnt) {
        this.accumulatedFirstCnt = accumulatedFirstCnt;
        this.accumulatedSecondCnt = accumulatedSecondCnt;
        this.baseDate = baseDate;
        this.firstCnt = firstCnt;
        this.secondCnt = secondCnt;
        this.sido = sido;
        this.totalFirstCnt = totalFirstCnt;
        this.totalSecondCnt = totalSecondCnt;
    }

    public long getAccumulatedFirstCnt() {
        return accumulatedFirstCnt;
    }

    public long getAccumulatedSecondCnt() {
        return accumulatedSecondCnt;
    }

    public String getBaseDate() {
        return baseDate;
    }

    public long getFirstCnt() {
        return firstCnt;
    }

    public long getSecondCnt() {
        return secondCnt;
    }

    public String getSido() {
        return sido;
    }

    public long getTotalFirstCnt() {
        return totalFirstCnt;
    }

    public long getTotalSecondCnt() {
        return totalSecondCnt;
    }
}
