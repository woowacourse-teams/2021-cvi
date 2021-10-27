package com.cvi.config.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Boolean 을 Y/N로 변환 테스트")
class BooleanToYNConverterTest {

    private final BooleanToYNConverter booleanToYNConverter = new BooleanToYNConverter();

    @Test
    @DisplayName("true 입력시 Y 반환")
    void convertToDatabaseColumn() {
        assertThat(booleanToYNConverter.convertToDatabaseColumn(true)).isEqualTo("Y");
    }

    @Test
    @DisplayName("Y 입력시 true 반환")
    void convertToEntityAttribute() {
        assertThat(booleanToYNConverter.convertToEntityAttribute("Y")).isEqualTo(true);
    }
}