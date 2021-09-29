package com.cvi.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("날짜 변환 기능 테스트")
class DateConverterTest {

    public static final String ZERO_TIME = " 00:00:00";

    @DisplayName("LocalDate를 00:00:00을 포한함 문자열로 변경  - 성공")
    @Test
    void convertDateToContainsZeroTime() {
        //given
        LocalDate expect = LocalDate.now();
        //when
        String actual = DateConverter.convertDateToContainsZeroTime(expect);
        //then
        assertThat(actual).isEqualTo(LocalDate.now() + ZERO_TIME);
    }

    @DisplayName("00:00:00을 포한함 날짜 문자열, LocalDate로 변경 - 성공")
    @Test
    void convertLocalDateTimeStringToLocalDate() {
        //given
        String expect = LocalDate.now() + ZERO_TIME;
        //when
        LocalDate actual = DateConverter.convertLocalDateTimeStringToLocalDate(expect);
        //then
        assertThat(actual).isEqualTo(LocalDate.now());
    }

    @Test
    void toLocalDate() {
        //given
        //when
        //then
    }
}