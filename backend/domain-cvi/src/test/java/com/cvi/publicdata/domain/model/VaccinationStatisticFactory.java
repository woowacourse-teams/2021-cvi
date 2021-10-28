package com.cvi.publicdata.domain.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class VaccinationStatisticFactory {

    public static final LocalDate TARGET_DATE = LocalDate.now();

    public static final List<VaccinationStatistic> KOREA_VACCINATION_STATISTICS_FROM_API = Arrays.asList(
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.REGION_WIDE).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.SEOUL).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.BUSAN).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.DAEGU).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.INCHEON).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.GWANGJU).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.ULSAN).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.DAEJEON).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.GYEONGGI_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.GANGWOND_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.SEJONG).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.CHUNGCHEONGBUK_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.CHUNGCHEONGNAM_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.JEOLLAKBUK_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.JEOLLANAM_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.GYEONGSANGBUK_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.GYEONGSANGNAME_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.JEJU_OD).build()

    );

    public static final List<VaccinationStatistic> WORLD_VACCINATION_STATISTICS_FROM_API = Arrays.asList(
        VaccinationStatistic.builder().baseDate(TARGET_DATE).regionPopulation(RegionPopulation.WORLD).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.WORLD).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(2)).regionPopulation(RegionPopulation.WORLD).build()
    );

    public static final List<VaccinationStatistic> VACCINATION_STATISTICS_FROM_DB = Arrays.asList(
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.REGION_WIDE).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.SEOUL).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.BUSAN).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.DAEGU).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.INCHEON).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.GWANGJU).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.ULSAN).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.DAEJEON).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.GYEONGGI_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.GANGWOND_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.SEJONG).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.CHUNGCHEONGBUK_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.CHUNGCHEONGNAM_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.JEOLLAKBUK_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.JEOLLANAM_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.GYEONGSANGBUK_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.GYEONGSANGNAME_DO).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(1)).regionPopulation(RegionPopulation.JEJU_OD).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(3)).regionPopulation(RegionPopulation.WORLD).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(4)).regionPopulation(RegionPopulation.WORLD).build(),
        VaccinationStatistic.builder().baseDate(TARGET_DATE.minusDays(5)).regionPopulation(RegionPopulation.WORLD).build()
    );
}
