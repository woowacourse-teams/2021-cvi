package com.backjoongwon.cvi.parser;

import com.backjoongwon.cvi.dto.RegionVaccinationData;
import com.backjoongwon.cvi.dto.VaccineParserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

@DisplayName("공공 데이터 api 요청 테스트")
class VaccineParserTest {

    public static final String API_SECRET_KEY = "api_secret_key";
    public static final List<String> REGIONS = Arrays.asList("전국", "부산광역시", "대구광역시", "대전광역시", "인천광역시", "광주광역시",
            "울산광역시", "경기도", "강원도", "세종특별자치시", "충청북도", "충청남도",
            "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도");

    private static Stream<Arguments> parseToPublicDataToday() {
        return Stream.of(
                Arguments.of("2021-03-11"),
                Arguments.of(LocalDate.now().toString())
        );
    }

    private static Stream<Arguments> parseToPublicDataWhenNotUpdate() {
        return Stream.of(
                Arguments.of("2021-03-10"),
                Arguments.of(LocalDate.now().plusDays(1).toString())
        );
    }

    @DisplayName("백신 접종 데이터 가져오기 - 성공 - 오늘 날짜")
    @ParameterizedTest
    @MethodSource
    void parseToPublicDataToday(String targetDate) {
        //given
        VaccineParser mockVaccineParser = mock(VaccineParser.class);
        VaccineParserResponse vaccineParserResponse = toVaccineParserResponse(targetDate);
        //when
        willReturn(vaccineParserResponse).given(mockVaccineParser).parseToPublicData(targetDate, API_SECRET_KEY);
        List<RegionVaccinationData> regionVaccinationData = vaccineParserResponse.getData();
        //then
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getAccumulatedFirstCnt)
                .isNotEmpty();
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getAccumulatedSecondCnt)
                .isNotEmpty();
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getBaseDate)
                .contains(targetDate + " 00:00:00");
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getSido)
                .containsAll(REGIONS);
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getFirstCnt)
                .isNotEmpty();
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getTotalFirstCnt)
                .isNotEmpty();
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getTotalSecondCnt)
                .isNotEmpty();
    }

    @DisplayName("백신 접종 데이터 가져오기 - 성공 - 공공데이터 업데이트 전 요청")
    @ParameterizedTest
    @MethodSource
    void parseToPublicDataWhenNotUpdate(String targetDate) {
        //given
        VaccineParser vaccineParser = new VaccineParser(new Parser());
        //when
        VaccineParserResponse vaccineParserResponse = vaccineParser.parseToPublicData(targetDate, API_SECRET_KEY);
        //then
        assertThat(vaccineParserResponse.getCurrentCount()).isEqualTo(0);
        assertThat(vaccineParserResponse.getData()).isEmpty();
        assertThat(vaccineParserResponse.getMatchCount()).isEqualTo(0);
        assertThat(vaccineParserResponse.getPage()).isEqualTo(1);
        assertThat(vaccineParserResponse.getPerPage()).isEqualTo(20);
        assertThat(vaccineParserResponse.getTotalCount()).isEqualTo(0);
    }

    private VaccineParserResponse toVaccineParserResponse(String targetDate) {
        targetDate = targetDate + " 00:00:00";
        return new VaccineParserResponse(18, Arrays.asList(
                new RegionVaccinationData(19473657, 7146602, targetDate,
                        473850, 35955, "전국", 19947507, 7182557),
                new RegionVaccinationData(3646765, 1329982, targetDate,
                        77877, 7123, "서울특별시", 3724642, 1337105),
                new RegionVaccinationData(1352394, 477845, targetDate,
                        31252, 2628, "부산광역시", 1383646, 480473),
                new RegionVaccinationData(848397, 309317, targetDate,
                        22726, 2688, "대구광역시", 871123, 312005),
                new RegionVaccinationData(523814, 197608, targetDate,
                        12990, 959, "대전광역시", 536804, 198567),
                new RegionVaccinationData(1032052, 360923, targetDate,
                        27394, 1760, "인천광역시", 1059446, 362683),
                new RegionVaccinationData(549706, 204415, targetDate,
                        13700, 830, "광주광역시", 563406, 205245),
                new RegionVaccinationData(396793, 124839, targetDate,
                        11275, 471, "울산광역시", 408068, 125310),
                new RegionVaccinationData(4636237, 1636357, targetDate,
                        118717, 7895, "경기도", 4754954, 1644252),
                new RegionVaccinationData(668422, 273253, targetDate,
                        14908, 1010, "강원도", 683330, 274263),
                new RegionVaccinationData(109202, 37624, targetDate,
                        3028, 179, "세종특별자치시", 112230, 37803),
                new RegionVaccinationData(639514, 240704, targetDate,
                        15886, 1324, "충청북도", 655400, 242028),
                new RegionVaccinationData(849914, 330591, targetDate,
                        22167, 2054, "충청남도", 872081, 332645),
                new RegionVaccinationData(789017, 311961, targetDate,
                        17331, 1270, "전라북도", 806348, 313231),
                new RegionVaccinationData(859228, 344275, targetDate,
                        19083, 1378, "전라남도", 878311, 345653),
                new RegionVaccinationData(1070878, 409752, targetDate,
                        27263, 2016, "경상북도", 1098141, 411768),
                new RegionVaccinationData(1249910, 464110, targetDate,
                        31098, 1943, "경상남도", 1281008, 466053),
                new RegionVaccinationData(251414, 93046, targetDate,
                        7155, 427, "제주특별자치도", 258569, 93473)
        ), 18, 1, 20, 2634);
    }
}