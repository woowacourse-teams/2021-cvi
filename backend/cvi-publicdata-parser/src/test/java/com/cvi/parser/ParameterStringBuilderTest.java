package com.cvi.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ParameterStringBuilder 기능 테스트")
class ParameterStringBuilderTest {

    @DisplayName("스트링 파라미터 생성 - 성공")
    @Test
    void getParamsString() {
        //given
        Map<String, String> parameters = new HashMap<>();
        //when
        parameters.put("name", "gump");
        parameters.put("age", "23");
        String stringParameters = ParameterStringBuilder.getParamsString(parameters);
        //then
        assertThat(stringParameters).isEqualTo("name=gump&age=23");
    }
}
