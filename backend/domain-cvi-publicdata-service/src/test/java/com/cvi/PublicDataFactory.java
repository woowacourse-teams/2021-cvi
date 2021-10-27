package com.cvi;

import com.cvi.dto.KoreaRegionVaccinationData;
import com.cvi.dto.KoreaVaccineParserResponse;
import com.cvi.dto.VaccinationStatisticResponse;
import com.cvi.dto.WorldVaccinationData;
import com.cvi.dto.WorldVaccinationParserResponse;
import com.cvi.util.DateConverter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PublicDataFactory {
    public static final List<String> REGIONS = Arrays.asList("전국", "부산광역시", "대구광역시", "대전광역시", "인천광역시", "광주광역시",
        "울산광역시", "경기도", "강원도", "세종특별자치시", "충청북도", "충청남도",
        "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도");

    public static KoreaVaccineParserResponse toVaccineParserResponse(LocalDate targetDate) {
        String expectDateTime = DateConverter.convertLocalDateToContainsZeroTime(targetDate);
        return new KoreaVaccineParserResponse(18, Arrays.asList(
            new KoreaRegionVaccinationData(19473657, 7146602, expectDateTime,
                473850, 35955, "전국", 19947507, 7182557),
            new KoreaRegionVaccinationData(3646765, 1329982, expectDateTime,
                77877, 7123, "서울특별시", 3724642, 1337105),
            new KoreaRegionVaccinationData(1352394, 477845, expectDateTime,
                31252, 2628, "부산광역시", 1383646, 480473),
            new KoreaRegionVaccinationData(848397, 309317, expectDateTime,
                22726, 2688, "대구광역시", 871123, 312005),
            new KoreaRegionVaccinationData(523814, 197608, expectDateTime,
                12990, 959, "대전광역시", 536804, 198567),
            new KoreaRegionVaccinationData(1032052, 360923, expectDateTime,
                27394, 1760, "인천광역시", 1059446, 362683),
            new KoreaRegionVaccinationData(549706, 204415, expectDateTime,
                13700, 830, "광주광역시", 563406, 205245),
            new KoreaRegionVaccinationData(396793, 124839, expectDateTime,
                11275, 471, "울산광역시", 408068, 125310),
            new KoreaRegionVaccinationData(4636237, 1636357, expectDateTime,
                118717, 7895, "경기도", 4754954, 1644252),
            new KoreaRegionVaccinationData(668422, 273253, expectDateTime,
                14908, 1010, "강원도", 683330, 274263),
            new KoreaRegionVaccinationData(109202, 37624, expectDateTime,
                3028, 179, "세종특별자치시", 112230, 37803),
            new KoreaRegionVaccinationData(639514, 240704, expectDateTime,
                15886, 1324, "충청북도", 655400, 242028),
            new KoreaRegionVaccinationData(849914, 330591, expectDateTime,
                22167, 2054, "충청남도", 872081, 332645),
            new KoreaRegionVaccinationData(789017, 311961, expectDateTime,
                17331, 1270, "전라북도", 806348, 313231),
            new KoreaRegionVaccinationData(859228, 344275, expectDateTime,
                19083, 1378, "전라남도", 878311, 345653),
            new KoreaRegionVaccinationData(1070878, 409752, expectDateTime,
                27263, 2016, "경상북도", 1098141, 411768),
            new KoreaRegionVaccinationData(1249910, 464110, expectDateTime,
                31098, 1943, "경상남도", 1281008, 466053),
            new KoreaRegionVaccinationData(251414, 93046, expectDateTime,
                7155, 427, "제주특별자치도", 258569, 93473)
        ), 18, 1, 20, 2634);
    }

    public static List<VaccinationStatisticResponse> toVaccinationStatisticResponse(LocalDate targetDate) {
        return Arrays.asList(
            new VaccinationStatisticResponse(19473657, 7146602, targetDate,
                473850, 35955, "전국", 19947507, 7182557, BigDecimal.valueOf(39.2), BigDecimal.valueOf(14.3)),
            new VaccinationStatisticResponse(3646765, 1329982, targetDate,
                77877, 7123, "서울특별시", 3724642, 1337105, BigDecimal.valueOf(39.5), BigDecimal.valueOf(14.4)),
            new VaccinationStatisticResponse(1352394, 477845, targetDate,
                31252, 2628, "부산광역시", 1383646, 480473, BigDecimal.valueOf(41.8), BigDecimal.valueOf(14.6)),
            new VaccinationStatisticResponse(848397, 309317, targetDate,
                22726, 2688, "대구광역시", 871123, 312005, BigDecimal.valueOf(37.0), BigDecimal.valueOf(13.4)),
            new VaccinationStatisticResponse(1032052, 360923, targetDate,
                27394, 1760, "인천광역시", 1059446, 362683, BigDecimal.valueOf(39.8), BigDecimal.valueOf(14.7)),
            new VaccinationStatisticResponse(549706, 204415, targetDate,
                13700, 830, "광주광역시", 563406, 205245, BigDecimal.valueOf(37.6), BigDecimal.valueOf(14.7)),
            new VaccinationStatisticResponse(396793, 124839, targetDate,
                11275, 471, "울산광역시", 408068, 125310, BigDecimal.valueOf(37.6), BigDecimal.valueOf(14.0)),
            new VaccinationStatisticResponse(523814, 197608, targetDate,
                12990, 959, "대전광역시", 536804, 198567, BigDecimal.valueOf(36.8), BigDecimal.valueOf(12.7)),
            new VaccinationStatisticResponse(4636237, 1636357, targetDate,
                118717, 7895, "경기도", 4754954, 1644252, BigDecimal.valueOf(31.6), BigDecimal.valueOf(10.9)),
            new VaccinationStatisticResponse(668422, 273253, targetDate,
                14908, 1010, "강원도", 683330, 274263, BigDecimal.valueOf(36.9), BigDecimal.valueOf(11.5)),
            new VaccinationStatisticResponse(109202, 37624, targetDate,
                3028, 179, "세종특별자치시", 112230, 37803, BigDecimal.valueOf(42.7), BigDecimal.valueOf(15.8)),
            new VaccinationStatisticResponse(639514, 240704, targetDate,
                15886, 1324, "충청북도", 655400, 242028, BigDecimal.valueOf(42.1), BigDecimal.valueOf(16.1)),
            new VaccinationStatisticResponse(849914, 330591, targetDate,
                22167, 2054, "충청남도", 872081, 332645, BigDecimal.valueOf(45.6), BigDecimal.valueOf(17.8)),
            new VaccinationStatisticResponse(789017, 311961, targetDate,
                17331, 1270, "전라북도", 806348, 313231, BigDecimal.valueOf(48.4), BigDecimal.valueOf(19.1)),
            new VaccinationStatisticResponse(859228, 344275, targetDate,
                19083, 1378, "전라남도", 878311, 345653, BigDecimal.valueOf(42.7), BigDecimal.valueOf(16.0)),
            new VaccinationStatisticResponse(1070878, 409752, targetDate,
                27263, 2016, "경상북도", 1098141, 411768, BigDecimal.valueOf(40.4), BigDecimal.valueOf(14.8)),
            new VaccinationStatisticResponse(1249910, 464110, targetDate,
                31098, 1943, "경상남도", 1281008, 466053, BigDecimal.valueOf(39.2), BigDecimal.valueOf(14.3)),
            new VaccinationStatisticResponse(251414, 93046, targetDate,
                7155, 427, "제주특별자치도", 258569, 93473, BigDecimal.valueOf(42.7), BigDecimal.valueOf(17.8))
        );
    }

    public static WorldVaccinationParserResponse toWorldVaccinationParserResponse(LocalDate targetDate) {
        return new WorldVaccinationParserResponse("World", "OWID_WRL",
            Arrays.asList(new WorldVaccinationData(targetDate.minusDays(2).toString(), 4327424315L, 2293196690L, 1175939230L,
                    43847311L, 42963523L, 55.52, 29.42, 15.09, 5512L),
                new WorldVaccinationData(targetDate.minusDays(1).toString(), 4399234109L, 2317353271L, 1181952381L,
                    32322341L, 40310055L, 55.93, 29.56, 15.16, 5171L),
                new WorldVaccinationData(targetDate.toString(), 4359746656L, 2303769251L, 1191535085L,
                    33797364L, 40043557L, 56.44, 29.73, 15.29, 5137L)
            )
        );
    }

    public static List<VaccinationStatisticResponse> toVaccinationStatisticResponseOnlyWorldRegion(LocalDate targetDate) {
        return Arrays.asList(
            new VaccinationStatisticResponse(0, 0, targetDate,
                0, 0, "World", 2303769251L, 1191535085L, BigDecimal.valueOf(29.3), BigDecimal.valueOf(15.0)),
            new VaccinationStatisticResponse(0, 0, targetDate.minusDays(1),
                0, 0, "World", 2293196690L, 1175939230L, BigDecimal.valueOf(28.9), BigDecimal.valueOf(14.7))
        );
    }

    public static VaccinationStatisticResponse toSingleWorldVaccinationStatisticResponse(LocalDate targetDate) {
        return new VaccinationStatisticResponse(0, 0, targetDate,
            0, 0, "World", 2303769251L, 1191535085L, BigDecimal.valueOf(29.3), BigDecimal.valueOf(15.0));
    }
}
