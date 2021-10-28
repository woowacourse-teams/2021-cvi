package com.cvi.publicdata.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@DiscriminatorValue(value = "vaccinationStatistic")
@PrimaryKeyJoinColumn(name = "vaccination_statistic_id", foreignKey = @ForeignKey(name = "fk_vaccination_statistic_public_data"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VaccinationStatistic extends PublicData {
    @Column(updatable = false)
    private LocalDate baseDate;
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
    private BigDecimal totalFirstRate;
    @Column(updatable = false)
    private BigDecimal totalSecondRate;

    @Builder
    public VaccinationStatistic(Long id, LocalDateTime createdAt, LocalDateTime lastModifiedAt, RegionPopulation regionPopulation,
                                LocalDate baseDate, long firstCnt, long secondCnt, long totalFirstCnt, long totalSecondCnt, long accumulatedFirstCnt,
                                long accumulatedSecondCnt, BigDecimal totalFirstRate, BigDecimal totalSecondRate) {
        super(id, createdAt, lastModifiedAt, regionPopulation);
        this.baseDate = baseDate;
        this.firstCnt = firstCnt;
        this.secondCnt = secondCnt;
        this.totalFirstCnt = totalFirstCnt;
        this.totalSecondCnt = totalSecondCnt;
        this.accumulatedFirstCnt = accumulatedFirstCnt;
        this.accumulatedSecondCnt = accumulatedSecondCnt;
        assignAccumulatedFirstRate(totalFirstRate);
        assignAccumulatedSecondRate(totalSecondRate);
    }

    private void assignAccumulatedFirstRate(BigDecimal accumulatedFirstRate) {
        if (Objects.nonNull(accumulatedFirstRate)) {
            this.totalFirstRate = accumulatedFirstRate;
            return;
        }
        this.totalFirstRate = calculateRate(this.totalFirstCnt);
    }

    private void assignAccumulatedSecondRate(BigDecimal accumulatedSecondRate) {
        if (Objects.nonNull(accumulatedSecondRate)) {
            this.totalSecondRate = accumulatedSecondRate;
            return;
        }
        this.totalSecondRate = calculateRate(this.totalSecondCnt);
    }

    private BigDecimal calculateRate(long totalCnt) {
        return BigDecimal.valueOf(totalCnt).scaleByPowerOfTen(2)
            .divide(BigDecimal.valueOf(this.regionPopulation.getPopulation()), 1, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getTotalFirstRate() {
        return totalFirstRate.setScale(1, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getTotalSecondRate() {
        return totalSecondRate.setScale(1, RoundingMode.HALF_EVEN);
    }

    public boolean isSameDate(LocalDate baseDate) {
        return this.baseDate.equals(baseDate);
    }
}
