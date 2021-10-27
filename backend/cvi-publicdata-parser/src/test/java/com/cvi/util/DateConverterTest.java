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
        String actual = DateConverter.convertLocalDateToContainsZeroTime(expect);
        //then
        assertThat(actual).isEqualTo(LocalDate.now() + ZERO_TIME);
    }

    @DisplayName("yyyy-MM-dd 형식의 문자열 LocalDate로 변경 - 성공")
    @Test
    void convertLocalDateStringToLocalDate() {
        //given
        final String expect = "2021-11-26";
        //when
        final LocalDate actual = DateConverter.convertLocalDateStringToLocalDate(expect);
        //then
        assertThat(actual).isEqualTo(LocalDate.parse(expect));
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
}
