package com.backjoongwon.cvi.parser;

import com.backjoongwon.cvi.dto.RegionVaccinationData;
import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.util.DateConverter;
import com.backjoongwon.cvi.util.JsonMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("공공 데이터 api 요청 테스트")
class VaccineParserTest {

    public static final String API_SECRET_KEY = "api_secret_key";
    public static final List<String> REGIONS = Arrays.asList("전국", "부산광역시", "대구광역시", "대전광역시", "인천광역시", "광주광역시",
            "울산광역시", "경기도", "강원도", "세종특별자치시", "충청북도", "충청남도",
            "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도");

    private MockedStatic<JsonMapper> mockJsonMapper;

    private static Stream<Arguments> parseToPublicDataToBeforeTenAndAnyDate() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0, 0))),
                Arguments.of(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(9, 59, 59))),
                Arguments.of(LocalDateTime.of(LocalDate.of(2021, 3, 11), LocalTime.MIN))
        );
    }

    private static Stream<Arguments> parseToPublicDataWhenNotUpdate() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 59, 59))),
                Arguments.of(LocalDateTime.of(LocalDate.of(2021, 3, 10), LocalTime.MAX)),
                Arguments.of(LocalDateTime.now().plusDays(1))
        );
    }

    @BeforeEach
    void init() {
        mockJsonMapper = mockStatic(JsonMapper.class);
    }

    @AfterEach
    void close() {
        mockJsonMapper.close();
    }

    @DisplayName("백신 접종 데이터 가져오기 - 성공 - 10시 이후의 오늘 날짜 및 2021-03-10이후의 날짜")
    @ParameterizedTest
    @MethodSource
    void parseToPublicDataToBeforeTenAndAnyDate(LocalDateTime targetDateTime) {
        //given
        VaccineParserResponse vaccineParserResponse = toVaccineParserResponse(targetDateTime);
        //when
        when(JsonMapper.toObject(anyString(), any())).thenReturn(vaccineParserResponse);
        List<RegionVaccinationData> regionVaccinationData = vaccineParserResponse.getData();
        //then
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getAccumulatedFirstCnt)
                .isNotEmpty();
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getAccumulatedSecondCnt)
                .isNotEmpty();
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getBaseDate)
                .contains(DateConverter.convertTimeToZero(targetDateTime));
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getSido)
                .containsAll(REGIONS);
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getFirstCnt)
                .isNotEmpty();
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getSecondCnt)
                .isNotEmpty();
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getTotalFirstCnt)
                .isNotEmpty();
        assertThat(regionVaccinationData).extracting(RegionVaccinationData::getTotalSecondCnt)
                .isNotEmpty();
    }

    @DisplayName("백신 접종 데이터 가져오기 - 성공 - 공공데이터 업데이트 전 요청")
    @ParameterizedTest
    @MethodSource
    void parseToPublicDataWhenNotUpdate(LocalDateTime targetDateTime) {
        //given
        VaccinationParser vaccineParser = new VaccinationParser(new Parser());
        //when
        VaccineParserResponse vaccineParserResponse = vaccineParser.parseToPublicData(targetDateTime, API_SECRET_KEY);
        //then
        assertThat(vaccineParserResponse.getCurrentCount()).isEqualTo(0);
        assertThat(vaccineParserResponse.getData()).isEmpty();
        assertThat(vaccineParserResponse.getMatchCount()).isEqualTo(0);
        assertThat(vaccineParserResponse.getPage()).isEqualTo(1);
        assertThat(vaccineParserResponse.getPerPage()).isEqualTo(20);
        assertThat(vaccineParserResponse.getTotalCount()).isEqualTo(0);
    }

    private VaccineParserResponse toVaccineParserResponse(LocalDateTime targetDateTime) {
        String expectDateTime = DateConverter.convertTimeToZero(targetDateTime);
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
}
