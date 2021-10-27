package com.cvi.util;

import com.cvi.dto.KoreaVaccineParserResponse;
import com.cvi.dto.WorldVaccinationParserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("JsonMapper 테스트 ")
class JsonMapperTest {

    private JsonMapper jsonMapper;

    @BeforeEach
    void init() {
        jsonMapper = new JsonMapper();
    }

    @DisplayName("매핑 - 성공")
    @Test
    void toObject() {
        //given
        String rawData = "{\n" +
                "  \"currentCount\": 1,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"accumulatedFirstCnt\": 19473657,\n" +
                "      \"accumulatedSecondCnt\": 7146602,\n" +
                "      \"baseDate\": \"2021-08-03 00:00:00\",\n" +
                "      \"firstCnt\": 473850,\n" +
                "      \"secondCnt\": 35955,\n" +
                "      \"sido\": \"전국\",\n" +
                "      \"totalFirstCnt\": 19947507,\n" +
                "      \"totalSecondCnt\": 7182557\n" +
                "    }" +
                "  ],\n" +
                "  \"matchCount\": 18,\n" +
                "  \"page\": 1,\n" +
                "  \"perPage\": 10,\n" +
                "  \"totalCount\": 2634\n" +
                "}";
        //when
        KoreaVaccineParserResponse koreaVaccineParserResponse = jsonMapper.toObject(rawData, KoreaVaccineParserResponse.class);
        //then
        assertThat(koreaVaccineParserResponse.getData()).isNotEmpty();
    }

    @DisplayName("매핑 - 실패 - 잘못된 json 데이터 입력")
    @Test
    void toObjectFailureWhenWrongData() {
        //given
        String rawData = "Strange Data";
        //when
        //then
        assertThatThrownBy(() -> jsonMapper.toObject(rawData, KoreaVaccineParserResponse.class))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("세계 접종 데이터 매핑 - 성공")
    @Test
    void toWorldVaccinationParserResponse() {
        //given
        String rawData = "[\n" +
                "  {\n" +
                "    \"country\": \"Afghanistan\",\n" +
                "    \"iso_code\": \"AFG\",\n" +
                "    \"data\": [\n" +
                "      {\n" +
                "        \"date\": \"2021-02-22\",\n" +
                "        \"total_vaccinations\": 0,\n" +
                "        \"people_vaccinated\": 0,\n" +
                "        \"total_vaccinations_per_hundred\": 0.0,\n" +
                "        \"people_vaccinated_per_hundred\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"date\": \"2021-02-23\",\n" +
                "        \"daily_vaccinations\": 1367,\n" +
                "        \"daily_vaccinations_per_million\": 35\n" +
                "      },\n" +
                "      {\n" +
                "        \"date\": \"2021-02-24\",\n" +
                "        \"daily_vaccinations\": 1367,\n" +
                "        \"daily_vaccinations_per_million\": 35\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n " +
                "  {\n" +
                "    \"country\": \"World\",\n" +
                "    \"iso_code\": \"OWID_WRL\",\n" +
                "    \"data\": [\n" +
                "      {\n" +
                "        \"date\": \"2021-08-01\",\n" +
                "        \"total_vaccinations\": 4190491615,\n" +
                "        \"people_vaccinated\": 2242946782,\n" +
                "        \"people_fully_vaccinated\": 1146556307,\n" +
                "        \"daily_vaccinations_raw\": 39051463,\n" +
                "        \"daily_vaccinations\": 41228010,\n" +
                "        \"total_vaccinations_per_hundred\": 53.76,\n" +
                "        \"people_vaccinated_per_hundred\": 28.77,\n" +
                "        \"people_fully_vaccinated_per_hundred\": 14.71,\n" +
                "        \"daily_vaccinations_per_million\": 5289\n" +
                "      },\n" +
                "      {\n" +
                "        \"date\": \"2021-08-02\",\n" +
                "        \"total_vaccinations\": 4227661997,\n" +
                "        \"people_vaccinated\": 2253618189,\n" +
                "        \"people_fully_vaccinated\": 1153697698,\n" +
                "        \"daily_vaccinations_raw\": 37170382,\n" +
                "        \"daily_vaccinations\": 40480949,\n" +
                "        \"total_vaccinations_per_hundred\": 54.24,\n" +
                "        \"people_vaccinated_per_hundred\": 28.91,\n" +
                "        \"people_fully_vaccinated_per_hundred\": 14.8,\n" +
                "        \"daily_vaccinations_per_million\": 5193\n" +
                "      },\n" +
                "      {\n" +
                "        \"date\": \"2021-08-03\",\n" +
                "        \"total_vaccinations\": 4283577004,\n" +
                "        \"people_vaccinated\": 2276706840,\n" +
                "        \"people_fully_vaccinated\": 1166639059,\n" +
                "        \"daily_vaccinations_raw\": 55915007,\n" +
                "        \"daily_vaccinations\": 42589009,\n" +
                "        \"total_vaccinations_per_hundred\": 54.95,\n" +
                "        \"people_vaccinated_per_hundred\": 29.21,\n" +
                "        \"people_fully_vaccinated_per_hundred\": 14.97,\n" +
                "        \"daily_vaccinations_per_million\": 5464\n" +
                "      },\n" +
                "      {\n" +
                "        \"date\": \"2021-08-04\",\n" +
                "        \"total_vaccinations\": 4327424315,\n" +
                "        \"people_vaccinated\": 2293196690,\n" +
                "        \"people_fully_vaccinated\": 1175939230,\n" +
                "        \"daily_vaccinations_raw\": 43847311,\n" +
                "        \"daily_vaccinations\": 42963523,\n" +
                "        \"total_vaccinations_per_hundred\": 55.52,\n" +
                "        \"people_vaccinated_per_hundred\": 29.42,\n" +
                "        \"people_fully_vaccinated_per_hundred\": 15.09,\n" +
                "        \"daily_vaccinations_per_million\": 5512\n" +
                "      },\n" +
                "      {\n" +
                "        \"date\": \"2021-08-05\",\n" +
                "        \"total_vaccinations\": 4359746656,\n" +
                "        \"people_vaccinated\": 2303769251,\n" +
                "        \"people_fully_vaccinated\": 1181952381,\n" +
                "        \"daily_vaccinations_raw\": 32322341,\n" +
                "        \"daily_vaccinations\": 40310055,\n" +
                "        \"total_vaccinations_per_hundred\": 55.93,\n" +
                "        \"people_vaccinated_per_hundred\": 29.56,\n" +
                "        \"people_fully_vaccinated_per_hundred\": 15.16,\n" +
                "        \"daily_vaccinations_per_million\": 5171\n" +
                "      }\n" +
                "    ]\n" +
                "    }\n" +
                "]";
        //when
        List<WorldVaccinationParserResponse> worldVaccinationParserResponses = jsonMapper.toWorldVaccinationParserResponse(rawData);
        //then
        assertThat(worldVaccinationParserResponses).extracting(WorldVaccinationParserResponse::getCountry).contains("Afghanistan", "World");
        assertThat(worldVaccinationParserResponses).extracting(WorldVaccinationParserResponse::getData).isNotEmpty();
    }
}
