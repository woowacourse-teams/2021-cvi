package com.backjoongwon.cvi.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("날짜 변환 기능 테스트")
class DateConverterTest {

    public static final String ZERO_TIME = " 00:00:00";

    @DisplayName("LocalDateTime 변환 - 성공")
    @Test
    void localDateTimeWithZero() {
        //given
        LocalDate expect = LocalDate.now();
        //when
        String actual = DateConverter.convertDateToContainsZeroTime(expect);
        //then
        assertThat(actual).isEqualTo(LocalDate.now() + ZERO_TIME);
    }

    @Test
    void play() {
        //String localDateTime = DateConverter.convertTimeWithZero(LocalDate.now());
        LocalDate localDate = DateConverter.convertLocalDateTimeStringToLocalDate("2021-08-01" + ZERO_TIME);
        System.out.println("localDate.toString() = " + localDate);
    }
}
