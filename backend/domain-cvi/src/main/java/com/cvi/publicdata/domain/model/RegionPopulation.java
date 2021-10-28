package com.cvi.publicdata.domain.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum RegionPopulation {
    REGION_WIDE("전국", 51_821_669L),
    SEOUL("서울특별시", 9_587_711L),
    BUSAN("부산광역시", 3_369_704L),
    DAEGU("대구광역시", 2_406_296L),
    INCHEON("인천광역시", 2_936_214L),
    GWANGJU("광주광역시", 1_444_787L),
    ULSAN("울산광역시", 1_128_163L),
    DAEJEON("대전광역시", 1_457_610L),
    GYEONGGI_DO("경기도", 13_479_798L),
    GANGWOND_DO("강원도", 1_536_175L),
    SEJONG("세종특별자치시", 362_995L),
    CHUNGCHEONGBUK_DO("충청북도", 1_566_303L),
    CHUNGCHEONGNAM_DO("충청남도", 2_116_452L),
    JEOLLAKBUK_DO("전라북도", 1_796_331L),
    JEOLLANAM_DO("전라남도", 1_844_148L),
    GYEONGSANGBUK_DO("경상북도", 2_635_896L),
    GYEONGSANGNAME_DO("경상남도", 3_229_623L),
    JEJU_OD("제주특별자치도", 675_293L),
    WORLD("World", 7_882_955_909L);

    private static final Map<String, RegionPopulation> regions = new HashMap<>();

    static {
        Arrays.stream(values())
            .forEach(it -> regions.put(it.region, it));
    }

    private final String region;
    private final long population;

    RegionPopulation(String region, long population) {
        this.region = region;
        this.population = population;
    }

    public static RegionPopulation findByRegion(String region) {
        return regions.get(region);
    }

    public static long size() {
        return regions.size();
    }
}
