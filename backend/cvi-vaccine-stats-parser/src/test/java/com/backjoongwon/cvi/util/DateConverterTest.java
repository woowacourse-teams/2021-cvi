package com.backjoongwon.cvi.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("날짜 변환 기능 테스트")
class DateConverterTest {

    public static final String ZERO_TIME = " 00:00:00";

    @DisplayName("LocalDateTime 변환 - 성공")
    @Test
    void localDateTimeWithZero() {
        //given
        LocalDateTime now = LocalDateTime.now();
        //when
        String actual = DateConverter.withZeroTime(now);
        //then
        assertThat(actual).isEqualTo(LocalDate.now() + ZERO_TIME);
    }

    @DisplayName("LocalDate 변환 - 성공")
    @Test
    void localDateWithZero() {
        //given
        LocalDate now = LocalDate.now();
        //when
        String actual = DateConverter.withZeroTime(now);
        //then
        assertThat(actual).isEqualTo(LocalDate.now() + ZERO_TIME);
    }
}