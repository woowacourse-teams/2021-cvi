package com.cvi.publicdata.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.cvi.publicdata.domain.model.VaccinationStatisticFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("VaccinationStatistic 일급 컬렉션 기능 테스트")
class VaccinationStatisticsTest {

    @DisplayName("새로운 한국 접종률 기준, DB에 저장되어 있지 않은 데이터 반환 - 성공 - 저장되어 있는 데이터의 날짜와 새로운 데이터의 날짜가 다를 때")
    @Test
    void findUnSavedStatisticsWhenAnotherDate() {
        //given
        final VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(KOREA_VACCINATION_STATISTICS_FROM_API);
        //when
        final List<VaccinationStatistic> unSavedStatistics = vaccinationStatistics.findUnSavedStatistics(
            VACCINATION_STATISTICS_FROM_DB, TARGET_DATE
        );
        //then
        assertThat(unSavedStatistics.size()).isEqualTo(RegionPopulation.size() - 1);
        assertThat(unSavedStatistics).noneMatch(it -> it.getRegionPopulation() == RegionPopulation.WORLD);
    }

    @DisplayName("새로운 한국 접종률 기준, DB에 저장되어 있지 않은 데이터 반환 - 성공 - 저장되어 있는 데이터의 날짜와 새로운 데이터의 날짜가 같을 때")
    @Test
    void findUnSavedStatisticsWhenSameDate() {
        //given
        final VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(KOREA_VACCINATION_STATISTICS_FROM_API);
        //when
        final List<VaccinationStatistic> unSavedStatistics = vaccinationStatistics.findUnSavedStatistics(
            KOREA_VACCINATION_STATISTICS_FROM_API, TARGET_DATE
        );
        //then
        assertThat(unSavedStatistics).isEmpty();
    }

    @DisplayName("새로운 세계 접종률 기준, DB에 저장되어 있지 않은 기준 날짜 포함 이전 데이터 반환- 성공")
    @Test
    void findWorldUnSavedStatisticsWhenTargetDate() {
        //given
        final VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(WORLD_VACCINATION_STATISTICS_FROM_API);
        //when
        final List<VaccinationStatistic> unSavedStatistics = vaccinationStatistics.findUnSavedStatistics(
            VACCINATION_STATISTICS_FROM_DB, TARGET_DATE.minusDays(1)
        );
        //then
        assertThat(unSavedStatistics.size()).isEqualTo(WORLD_VACCINATION_STATISTICS_FROM_API.size() - 1);
        assertThat(unSavedStatistics).allMatch(it -> it.getRegionPopulation() == RegionPopulation.WORLD);
    }

    @DisplayName("타겟 날짜 이전의 데이터가 DB에 저장되어 있을때(타겟날짜에 데이터가 저장되어 있지 않음) 이전 날짜의 데이터 반환 - 성공 - 한국 데이터")
    @Test
    void findWorldRecentlyStatistics() {
        //given
        final VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(VACCINATION_STATISTICS_FROM_DB);
        //when
        final List<VaccinationStatistic> koreaRecentlyStatistics = vaccinationStatistics.findKoreaRecentlyStatistics(TARGET_DATE);
        //then
        assertThat(koreaRecentlyStatistics.size()).isEqualTo(RegionPopulation.size() - 1);
        assertThat(koreaRecentlyStatistics).noneMatch(it -> it.getRegionPopulation() == RegionPopulation.WORLD);
        assertThat(koreaRecentlyStatistics).extracting(VaccinationStatistic::getBaseDate)
            .contains(TARGET_DATE.minusDays(1));
    }

    @DisplayName("타겟 날짜 이전의 데이터가 DB에 저장되어 있을때(타겟날짜에 데이터가 저장되어 있지 않음) 이전 날짜의 데이터 반환 - 성공 - 세계 데이터")
    @Test
    void findKoreaRecentlyStatistics() {
        //given
        final VaccinationStatistics vaccinationStatistics = new VaccinationStatistics(VACCINATION_STATISTICS_FROM_DB);
        //when
        final List<VaccinationStatistic> worldRecentlyStatistics = vaccinationStatistics.findWorldRecentlyStatistics(TARGET_DATE);
        //then
        assertThat(worldRecentlyStatistics).allMatch(it -> it.getRegionPopulation() == RegionPopulation.WORLD);
        assertThat(worldRecentlyStatistics).extracting(VaccinationStatistic::getBaseDate)
            .doesNotContain(TARGET_DATE);
    }
}
