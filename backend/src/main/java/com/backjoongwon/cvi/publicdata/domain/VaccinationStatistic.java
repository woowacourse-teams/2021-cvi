package com.backjoongwon.cvi.publicdata.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
    @Column(updatable = false)
    private String baseDate;
    @Column(updatable = false)
    private long firstCnt;
    @Column(updatable = false)
    private long secondCnt;
    @Column(updatable = false)
    private long totalFirstCnt;
    @Column(updatable = false)
    private long totalSecondCnt;
    @Column(updatable = false)
    private long accumulatedFirstCnt;
    @Column(updatable = false)
    private long accumulatedSecondCnt;
    @Column(updatable = false)
    private BigDecimal accumulatedFirstRate;
    @Column(updatable = false)
    private BigDecimal accumulatedSecondRate;

    @Builder
    public VaccinationStatistic(Long id, LocalDateTime createdAt, LocalDateTime lastModifiedAt, RegionPopulation regionPopulation,
                                String baseDate, long firstCnt, long secondCnt, long totalFirstCnt, long totalSecondCnt, long accumulatedFirstCnt,
                                long accumulatedSecondCnt, BigDecimal accumulatedFirstRate, BigDecimal accumulatedSecondRate) {
        super(id, createdAt, lastModifiedAt, regionPopulation);
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

    private BigDecimal calculateRate(long totalCnt) {
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
