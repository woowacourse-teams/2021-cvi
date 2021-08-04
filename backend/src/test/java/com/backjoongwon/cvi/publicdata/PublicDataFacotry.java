package com.backjoongwon.cvi.publicdata;

import com.backjoongwon.cvi.dto.RegionVaccinationData;
import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.publicdata.dto.VaccinationRateResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PublicDataFacotry {
    public static VaccineParserResponse toVaccineParserResponse(LocalDateTime targetDateTime) {
        String expectDateTime = targetDateTime.toLocalDate().toString() + " 00:00:00";
        return new VaccineParserResponse(18, Arrays.asList(
                new RegionVaccinationData(19473657, 7146602, expectDateTime,
                        473850, 35955, "전국", 19947507, 7182557),
                new RegionVaccinationData(3646765, 1329982, expectDateTime,
                        77877, 7123, "서울특별시", 3724642, 1337105),
                new RegionVaccinationData(1352394, 477845, expectDateTime,
                        31252, 2628, "부산광역시", 1383646, 480473),
                new RegionVaccinationData(848397, 309317, expectDateTime,
                        22726, 2688, "대구광역시", 871123, 312005),
                new RegionVaccinationData(523814, 197608, expectDateTime,
                        12990, 959, "대전광역시", 536804, 198567),
                new RegionVaccinationData(1032052, 360923, expectDateTime,
                        27394, 1760, "인천광역시", 1059446, 362683),
                new RegionVaccinationData(549706, 204415, expectDateTime,
                        13700, 830, "광주광역시", 563406, 205245),
                new RegionVaccinationData(396793, 124839, expectDateTime,
                        11275, 471, "울산광역시", 408068, 125310),
                new RegionVaccinationData(4636237, 1636357, expectDateTime,
                        118717, 7895, "경기도", 4754954, 1644252),
                new RegionVaccinationData(668422, 273253, expectDateTime,
                        14908, 1010, "강원도", 683330, 274263),
                new RegionVaccinationData(109202, 37624, expectDateTime,
                        3028, 179, "세종특별자치시", 112230, 37803),
                new RegionVaccinationData(639514, 240704, expectDateTime,
                        15886, 1324, "충청북도", 655400, 242028),
                new RegionVaccinationData(849914, 330591, expectDateTime,
                        22167, 2054, "충청남도", 872081, 332645),
                new RegionVaccinationData(789017, 311961, expectDateTime,
                        17331, 1270, "전라북도", 806348, 313231),
                new RegionVaccinationData(859228, 344275, expectDateTime,
                        19083, 1378, "전라남도", 878311, 345653),
                new RegionVaccinationData(1070878, 409752, expectDateTime,
                        27263, 2016, "경상북도", 1098141, 411768),
                new RegionVaccinationData(1249910, 464110, expectDateTime,
                        31098, 1943, "경상남도", 1281008, 466053),
                new RegionVaccinationData(251414, 93046, expectDateTime,
                        7155, 427, "제주특별자치도", 258569, 93473)
        ), 18, 1, 20, 2634);
    }

    public static List<VaccinationRateResponse> toVaccinationRateResponse(LocalDateTime targetDateTime) {
        String expectDateTime = targetDateTime.toLocalDate().toString() + " 00:00:00";
        return Arrays.asList(
                new VaccinationRateResponse(19473657, 7146602, expectDateTime,
                        473850, 35955, "전국", 19947507, 7182557, 38),
                new VaccinationRateResponse(3646765, 1329982, expectDateTime,
                        77877, 7123, "서울특별시", 3724642, 1337105, 7),
                new VaccinationRateResponse(1352394, 477845, expectDateTime,
                        31252, 2628, "부산광역시", 1383646, 480473, 2),
                new VaccinationRateResponse(848397, 309317, expectDateTime,
                        22726, 2688, "대구광역시", 871123, 312005, 1),
                new VaccinationRateResponse(523814, 197608, expectDateTime,
                        12990, 959, "대전광역시", 536804, 198567, 1),
                new VaccinationRateResponse(1032052, 360923, expectDateTime,
                        27394, 1760, "인천광역시", 1059446, 362683, 2),
                new VaccinationRateResponse(549706, 204415, expectDateTime,
                        13700, 830, "광주광역시", 563406, 205245, 0),
                new VaccinationRateResponse(396793, 124839, expectDateTime,
                        11275, 471, "울산광역시", 408068, 125310, 1),
                new VaccinationRateResponse(4636237, 1636357, expectDateTime,
                        118717, 7895, "경기도", 4754954, 1644252, 0),
                new VaccinationRateResponse(668422, 273253, expectDateTime,
                        14908, 1010, "강원도", 683330, 274263, 9),
                new VaccinationRateResponse(109202, 37624, expectDateTime,
                        3028, 179, "세종특별자치시", 112230, 37803, 1),
                new VaccinationRateResponse(639514, 240704, expectDateTime,
                        15886, 1324, "충청북도", 655400, 242028, 1),
                new VaccinationRateResponse(849914, 330591, expectDateTime,
                        22167, 2054, "충청남도", 872081, 332645, 1),
                new VaccinationRateResponse(789017, 311961, expectDateTime,
                        17331, 1270, "전라북도", 806348, 313231, 1),
                new VaccinationRateResponse(859228, 344275, expectDateTime,
                        19083, 1378, "전라남도", 878311, 345653, 1),
                new VaccinationRateResponse(1070878, 409752, expectDateTime,
                        27263, 2016, "경상북도", 1098141, 411768, 2),
                new VaccinationRateResponse(1249910, 464110, expectDateTime,
                        31098, 1943, "경상남도", 1281008, 466053, 2),
                new VaccinationRateResponse(251414, 93046, expectDateTime,
                        7155, 427, "제주특별자치도", 258569, 93473, 2)
        );
    }
}
