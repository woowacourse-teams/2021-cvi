package com.cvi.config.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Boolean 을 Y/N로 변환 테스트")
class BooleanToYNConverterTest {

    private final BooleanToYNConverter booleanToYNConverter = new BooleanToYNConverter();

    @Test
    @DisplayName("true 입력시 Y 반환 - 성공")
    void convertToDatabaseColumnTrueToY() {
        assertThat(booleanToYNConverter.convertToDatabaseColumn(true)).isEqualTo("Y");
    }

    @Test
    @DisplayName("false 입력시 N 반환 - 성공")
    void convertToDatabaseColumnFalseToN() {
        assertThat(booleanToYNConverter.convertToDatabaseColumn(false)).isEqualTo("N");
    }

    @Test
    @DisplayName("Y 입력시 true 반환 - 성공")
    void convertToEntityAttributeYToTrue() {
        assertThat(booleanToYNConverter.convertToEntityAttribute("Y")).isEqualTo(true);
    }

    @Test
    @DisplayName("N 입력시 false 반환 - 성공")
    void convertToEntityAttributeNToFalse() {
        assertThat(booleanToYNConverter.convertToEntityAttribute("N")).isEqualTo(false);
    }
}
