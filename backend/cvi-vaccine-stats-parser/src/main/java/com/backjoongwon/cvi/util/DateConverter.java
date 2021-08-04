package com.backjoongwon.cvi.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateConverter {

    public static String withZeroTime(LocalDateTime targetDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        targetDateTime = targetDateTime.truncatedTo(ChronoUnit.DAYS);
        return targetDateTime.format(formatter);
    }

    public static String withZeroTime(LocalDate targetDate) {
        return targetDate.toString() + " 00:00:00";
    }

    public static LocalDateTime toLocalDateTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MAX);
    }
}
