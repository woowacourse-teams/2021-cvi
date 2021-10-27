package com.cvi.parser;

import com.cvi.CustomParameterizedTest;
import com.cvi.dto.KoreaRegionVaccinationData;
import com.cvi.dto.KoreaVaccineParserResponse;
import com.cvi.dto.WorldVaccinationData;
import com.cvi.dto.WorldVaccinationParserResponse;
import com.cvi.util.DateConverter;
import com.cvi.util.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

@DisplayName("공공 데이터 api 요청 테스트")
class VaccineParserTest {

    public static final String API_SECRET_KEY = "api_secret_key";
    public static final List<String> REGIONS = Arrays.asList("전국", "부산광역시", "대구광역시", "대전광역시", "인천광역시", "광주광역시",
            "울산광역시", "경기도", "강원도", "세종특별자치시", "충청북도", "충청남도",
            "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도");

    private JsonMapper jsonMapper;
    private Parser parser;
    private VaccinationParser vaccineParser;

    private static Stream<Arguments> parseToPublicDataToBeforeTenAndAnyDate() {
        return Stream.of(
                Arguments.of(LocalDate.now()),
                Arguments.of(LocalDate.now().minusDays(1)),
                Arguments.of(LocalDate.of(2021, 3, 11))
        );
    }

    private static Stream<Arguments> parseToPublicDataWhenNotUpdate() {
        return Stream.of(
                Arguments.of(LocalDate.of(2021, 3, 10)),
                Arguments.of(LocalDate.now().plusDays(1))
        );
    }

    @BeforeEach
    void init() {
        jsonMapper = mock(JsonMapper.class);
        parser = mock(Parser.class);
        vaccineParser = new VaccinationParser(parser, jsonMapper);
    }

    @DisplayName("백신 접종 데이터 가져오기 - 성공")
    @CustomParameterizedTest
    @MethodSource
    void parseToPublicDataToBeforeTenAndAnyDate(LocalDate targetDate) throws JsonProcessingException {
        //given
        KoreaVaccineParserResponse expect = toVaccineParserResponse(targetDate);
        String rawData = new ObjectMapper().writeValueAsString(expect);
        //when
        willReturn(expect).given(jsonMapper).toObject(anyString(), any());
        willReturn(rawData).given(parser).parse(anyString());
        KoreaVaccineParserResponse actual = vaccineParser.parseToKoreaPublicData(targetDate, API_SECRET_KEY);
        List<KoreaRegionVaccinationData> koreaRegionVaccinationData = actual.getData();
        //then
        assertThat(koreaRegionVaccinationData).extracting(KoreaRegionVaccinationData::getAccumulatedFirstCnt)
                .isNotEmpty();
        assertThat(koreaRegionVaccinationData).extracting(KoreaRegionVaccinationData::getAccumulatedSecondCnt)
                .isNotEmpty();
        assertThat(koreaRegionVaccinationData).extracting(KoreaRegionVaccinationData::getBaseDate)
                .contains(DateConverter.convertLocalDateToContainsZeroTime(targetDate));
        assertThat(koreaRegionVaccinationData).extracting(KoreaRegionVaccinationData::getSido)
                .containsAll(REGIONS);
        assertThat(koreaRegionVaccinationData).extracting(KoreaRegionVaccinationData::getFirstCnt)
                .isNotEmpty();
        assertThat(koreaRegionVaccinationData).extracting(KoreaRegionVaccinationData::getSecondCnt)
                .isNotEmpty();
        assertThat(koreaRegionVaccinationData).extracting(KoreaRegionVaccinationData::getTotalFirstCnt)
                .isNotEmpty();
        assertThat(koreaRegionVaccinationData).extracting(KoreaRegionVaccinationData::getTotalSecondCnt)
                .isNotEmpty();
    }

    @DisplayName("백신 접종 데이터 가져오기 - 성공 - 공공데이터 업데이트 전 요청")
    @CustomParameterizedTest
    @MethodSource
    void parseToPublicDataWhenNotUpdate(LocalDate targetDate) throws JsonProcessingException {
        //given
        KoreaVaccineParserResponse expect = KoreaVaccineParserResponse.empty();
        String rawData = new ObjectMapper().writeValueAsString(expect);
        //when
        willReturn(expect).given(jsonMapper).toObject(anyString(), any());
        willReturn(rawData).given(parser).parse(anyString());
        KoreaVaccineParserResponse actual = vaccineParser.parseToKoreaPublicData(targetDate, API_SECRET_KEY);
        //then
        assertThat(actual.getCurrentCount()).isEqualTo(0);
        assertThat(actual.getData()).isEmpty();
        assertThat(actual.getMatchCount()).isEqualTo(0);
        assertThat(actual.getPage()).isEqualTo(1);
        assertThat(actual.getPerPage()).isEqualTo(20);
        assertThat(actual.getTotalCount()).isEqualTo(0);
    }

    @DisplayName("세계 백신 접종 데이터 - 성공")
    @Test
    void parseToPWorldPublicData() throws JsonProcessingException {
        //given
        List<WorldVaccinationParserResponse> expect = toWorldVaccinationParserResponse();
        String rawData = new ObjectMapper().writeValueAsString(expect);
        //when
        willReturn(expect).given(jsonMapper).toWorldVaccinationParserResponse(anyString());
        willReturn(rawData).given(parser).parse(anyString());
        WorldVaccinationParserResponse actual = vaccineParser.parseToWorldPublicData();
        List<WorldVaccinationData> actualData = actual.getData();
        //then
        assertThat(actual.getCountry()).isEqualTo("World");
        assertThat(actualData).extracting("date").contains("2021-08-04", "2021-08-05");
        assertThat(actualData).extracting(WorldVaccinationData::getTotalVaccinations).isNotEmpty();
        assertThat(actualData).extracting(WorldVaccinationData::getPeopleVaccinated).isNotEmpty();
        assertThat(actualData).extracting(WorldVaccinationData::getPeopleFullyVaccinated).isNotEmpty();
        assertThat(actualData).extracting(WorldVaccinationData::getDailyVaccinationsRaw).isNotEmpty();
        assertThat(actualData).extracting(WorldVaccinationData::getDailyVaccinations).isNotEmpty();
        assertThat(actualData).extracting(WorldVaccinationData::getTotalVaccinationsPerHundred).isNotEmpty();
        assertThat(actualData).extracting(WorldVaccinationData::getPeopleVaccinatedPerHundred).isNotEmpty();
        assertThat(actualData).extracting(WorldVaccinationData::getPeopleFullyVaccinatedPerHundred).isNotEmpty();
        assertThat(actualData).extracting(WorldVaccinationData::getDailyVaccinationsPerMillion).isNotEmpty();
    }

    @DisplayName("세계 백신 접종 데이터 - 성공 - 데이터 업데이트 전 요청")
    @Test
    void parseToPWorldPublicDataWhenNotUpdate() throws JsonProcessingException {
        //given
        List<WorldVaccinationParserResponse> expect = Collections.singletonList(WorldVaccinationParserResponse.empty());
        String rawData = new ObjectMapper().writeValueAsString(expect);
        //when
        willReturn(expect).given(jsonMapper).toWorldVaccinationParserResponse(anyString());
        willReturn(rawData).given(parser).parse(anyString());
        WorldVaccinationParserResponse actual = vaccineParser.parseToWorldPublicData();
        List<WorldVaccinationData> actualData = actual.getData();
        //then
        assertThat(actual.getCountry()).isEqualTo("World");
        assertThat(actualData).isEmpty();
    }

    private KoreaVaccineParserResponse toVaccineParserResponse(LocalDate targetDate) {
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

    private List<WorldVaccinationParserResponse> toWorldVaccinationParserResponse() {
        return Arrays.asList(
                new WorldVaccinationParserResponse("Afghanistan", "AFG",
                        Collections.singletonList(new WorldVaccinationData("2021-08-01", 1442250L, 763936L, 33692L, 0,
                                0, 0, 3.7, 1.96, 865L))
                ),
                new WorldVaccinationParserResponse("World", "OWID_WRL",
                        Arrays.asList(new WorldVaccinationData("2021-08-04", 4327424315L, 2293196690L, 1175939230L,
                                        43847311L, 42963523L, 55.52, 29.42, 15.09, 5512L),
                                new WorldVaccinationData("2021-08-05", 4359746656L, 2303769251L, 1181952381L,
                                        32322341L, 40310055L, 55.93, 29.56, 15.16, 5171L)
                        )
                )
        );
    }
}
