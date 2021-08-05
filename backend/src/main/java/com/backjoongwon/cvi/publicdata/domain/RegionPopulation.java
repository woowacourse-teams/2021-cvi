package com.backjoongwon.cvi.publicdata.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum RegionPopulation {
    REGION_WIDE("전국", 51_821_669),
    SEOUL("서울특별시", 9_587_711),
    BUSAN("부산광역시", 3_369_704),
    DAEGU("대구광역시", 2_406_296),
    DAEJEON("대전광역시", 1_457_610),
    INCHEON("인천광역시", 2_936_214),
    GWANGJU("광주광역시", 1_444_787),
    ULSAN("울산광역시", 1_128_163),
    GYEONGGI_DO("경기도", 13_479_798),
    GANGWOND_DO("강원도", 1_536_175),
    SEJONG("세종특별자치시", 362_995),
    CHUNGCHEONGBUK_DO("충청북도", 1_566_303),
    CHUNGCHEONGNAM_DO("충청남도", 2_116_452),
    JEOLLAKBUK_DO("전라북도", 1_796_331),
    JEOLLANAM_DO("전라남도", 1_844_148),
    GYEONGSANGBUK_DO("경상북도", 2_635_896),
    GYEONGSANGNAME_DO("경상남도", 3_229_623),
    JEJU_OD("제주특별자치도", 675_293);

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
}
