package com.cvi.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateConverter() {
    }

    public static String convertLocalDateToContainsZeroTime(LocalDate targetDate) {
        LocalDateTime targetDateTime = targetDate.atStartOfDay();
        return targetDateTime.format(FORMATTER);
    }

    public static LocalDate convertLocalDateStringToLocalDate(String targetDate) {
        return LocalDate.parse(targetDate);
    }

    public static LocalDate convertLocalDateTimeStringToLocalDate(String targetLocalDateTime) {
        return LocalDate.parse(targetLocalDateTime, FORMATTER);
    }
}
