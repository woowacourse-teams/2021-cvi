package com.backjoongwon.cvi.publicdata.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue(value = "vaccinationStatistic")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VaccinationStatistic extends PublicData {
    private String baseDate;
    private int firstCnt;
    private int secondCnt;
    private int totalFirstCnt;
    private int totalSecondCnt;
    private int accumulatedFirstCnt;
    private int accumulatedSecondCnt;
    private BigDecimal accumulateFirstRate;

    @Builder
    public VaccinationStatistic(Long id, LocalDateTime createdAt, String sido, String baseDate, int firstCnt, int secondCnt,
                           int totalFirstCnt, int totalSecondCnt, int accumulatedFirstCnt, int accumulatedSecondCnt, BigDecimal accumulateFirstRate) {
        super(id, createdAt, sido);
        this.baseDate = baseDate;
        this.firstCnt = firstCnt;
        this.secondCnt = secondCnt;
        this.totalFirstCnt = totalFirstCnt;
        this.totalSecondCnt = totalSecondCnt;
        this.accumulatedFirstCnt = accumulatedFirstCnt;
        this.accumulatedSecondCnt = accumulatedSecondCnt;
        this.accumulateFirstRate = accumulateFirstRate;
    }
}
