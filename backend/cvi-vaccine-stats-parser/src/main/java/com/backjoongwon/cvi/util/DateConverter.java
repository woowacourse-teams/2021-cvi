package com.backjoongwon.cvi.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateConverter {

    public static String withZeroTime(LocalDateTime targetDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        targetDateTime = targetDateTime.truncatedTo(ChronoUnit.DAYS);
        return targetDateTime.format(formatter);
    }
}
