package com.backjoongwon.cvi.util;

import com.backjoongwon.cvi.dto.VaccineParserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("JsonMapper 테스트 ")
class JsonMapperTest {

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
        VaccineParserResponse vaccineParserResponse = JsonMapper.toObject(rawData, VaccineParserResponse.class);
        //then
        assertThat(vaccineParserResponse.getData()).isNotEmpty();
    }

    @DisplayName("매핑 - 실패 - 잘못된 json 데이터 입력")
    @Test
    void toObjectFailureWhenWrongData() {
        //given
        String rawData = "Strange Data";
        //when
        //then
        assertThatThrownBy(() -> JsonMapper.toObject(rawData, VaccineParserResponse.class))
                .isInstanceOf(IllegalStateException.class);
    }
}