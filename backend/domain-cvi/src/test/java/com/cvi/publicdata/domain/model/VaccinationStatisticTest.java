package com.cvi.publicdata.domain.model;

import com.cvi.CustomParameterizedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("백신 접종률 도메인 테스트")
class VaccinationStatisticTest {

    @DisplayName("인구수 비례 1차 접종률 계산 - 성공 - 전국")
    @CustomParameterizedTest
    @CsvSource({
        "2100000, 4.1",
        "21000000, 40.5",
        "11000000, 21.2"
    })
    void accumulateFirstRate(long totalFirstCnt, double actual) {
        //given
        VaccinationStatistic vaccinationStatistic = VaccinationStatistic.builder()
            .regionPopulation(RegionPopulation.REGION_WIDE)
            .totalFirstCnt(totalFirstCnt)
            .build();
        //when
        BigDecimal accumulateFirstRate = vaccinationStatistic.getTotalFirstRate();
        //then
        assertThat(accumulateFirstRate.doubleValue()).isEqualTo(actual);
    }

    @DisplayName("인구수 비례 2차 접종률 계산 - 성공 - 전국")
    @CustomParameterizedTest
    @CsvSource({
        "1100000, 2.1",
        "3000000, 5.8",
        "11000000, 21.2"
    })
    void accumulateSecondRate(long totalSecondCnt, double actual) {
        //given
        VaccinationStatistic vaccinationStatistic = VaccinationStatistic.builder()
            .regionPopulation(RegionPopulation.REGION_WIDE)
            .totalSecondCnt(totalSecondCnt)
            .build();
        //when
        BigDecimal accumulatedSecondRate = vaccinationStatistic.getTotalSecondRate();
        //then
        assertThat(accumulatedSecondRate.doubleValue()).isEqualTo(actual);
    }

    @DisplayName("인구수 비례 1차 접종률 계산 - 성공 - 전세계")
    @CustomParameterizedTest
    @CsvSource({
        "2253618189, 28.6",
        "2276706840, 28.9",
        "2303769251, 29.2"
    })
    void accumulateWorldFirstRate(long totalSecondCnt, double actual) {
        //given
        VaccinationStatistic vaccinationStatistic = VaccinationStatistic.builder()
            .regionPopulation(RegionPopulation.WORLD)
            .totalSecondCnt(totalSecondCnt)
            .build();
        //when
        BigDecimal accumulatedSecondRate = vaccinationStatistic.getTotalSecondRate();
        //then
        assertThat(accumulatedSecondRate.doubleValue()).isEqualTo(actual);
    }

    @DisplayName("인구수 비례 2차 접종률 계산 - 성공 - 전세계")
    @CustomParameterizedTest
    @CsvSource({
        "1153697698, 14.6",
        "1175939230, 14.9",
        "1181952381, 15.0"
    })
    void accumulateWorldSecondRate(long totalSecondCnt, double actual) {
        //given
        VaccinationStatistic vaccinationStatistic = VaccinationStatistic.builder()
            .regionPopulation(RegionPopulation.WORLD)
            .totalSecondCnt(totalSecondCnt)
            .build();
        //when
        BigDecimal accumulatedSecondRate = vaccinationStatistic.getTotalSecondRate();
        //then
        assertThat(accumulatedSecondRate.doubleValue()).isEqualTo(actual);
    }
}
