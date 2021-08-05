package com.backjoongwon.cvi.publicdata.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

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
    private BigDecimal accumulatedFirstRate;
    private BigDecimal accumulatedSecondRate;

    @Builder
    public VaccinationStatistic(Long id, LocalDateTime createdAt, RegionPopulation regionPopulation, String baseDate,
                                int firstCnt, int secondCnt, int totalFirstCnt, int totalSecondCnt, int accumulatedFirstCnt,
                                int accumulatedSecondCnt, BigDecimal accumulatedFirstRate, BigDecimal accumulatedSecondRate) {
        super(id, createdAt, regionPopulation);
        this.baseDate = baseDate;
        this.firstCnt = firstCnt;
        this.secondCnt = secondCnt;
        this.totalFirstCnt = totalFirstCnt;
        this.totalSecondCnt = totalSecondCnt;
        this.accumulatedFirstCnt = accumulatedFirstCnt;
        this.accumulatedSecondCnt = accumulatedSecondCnt;
        assignAccumulatedFirstRate(accumulatedFirstRate);
        assignAccumulatedSecondRate(accumulatedSecondRate);
    }

    private void assignAccumulatedFirstRate(BigDecimal accumulatedFirstRate) {
        if (Objects.nonNull(accumulatedFirstRate)) {
            this.accumulatedFirstRate = accumulatedFirstRate;
            return;
        }
        this.accumulatedFirstRate = calculateRate(this.totalFirstCnt);
    }

    private void assignAccumulatedSecondRate(BigDecimal accumulatedSecondRate) {
        if (Objects.nonNull(accumulatedSecondRate)) {
            this.accumulatedSecondRate = accumulatedSecondRate;
            return;
        }
        this.accumulatedSecondRate = calculateRate(this.totalSecondCnt);
    }

    private BigDecimal calculateRate(int totalCnt) {
        return BigDecimal.valueOf(totalCnt).scaleByPowerOfTen(2)
                .divide(BigDecimal.valueOf(this.regionPopulation.getPopulation()), 1, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getAccumulatedFirstRate() {
        return accumulatedFirstRate.setScale(1, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getAccumulatedSecondRate() {
        return accumulatedSecondRate.setScale(1, RoundingMode.HALF_EVEN);
    }
}
