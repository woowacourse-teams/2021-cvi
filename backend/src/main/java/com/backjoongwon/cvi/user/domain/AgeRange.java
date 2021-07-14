package com.backjoongwon.cvi.user.domain;

import com.backjoongwon.cvi.common.exception.InvalidInputException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AgeRange {
    TEENS("10대", 10, 20),
    TWENTIES("20대", 20, 30),
    THIRTIES("30대", 30, 40),
    FORTIES("40대", 40, 50),
    FIFTIES("50대", 50, 60),
    OVER_SIXTIES("60대", 60, Integer.MAX_VALUE);

    private final String meaning;
    private final int minValue;
    private final int maxValue;

    AgeRange(String meaning, int minValue, int maxValue) {
        this.meaning = meaning;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public static AgeRange convertFromValue(int value) {
        return Arrays.stream(values())
                .filter(ageRange -> ageRange.minValue == value)
                .findAny()
                .orElseThrow(() -> new InvalidInputException("해당 연령대가 존재하지 않습니다."));
    }

    public static int convertToValue(AgeRange toConvert) {
        return Arrays.stream(values())
                .filter(ageRange -> ageRange == toConvert)
                .map(ageRange -> ageRange.minValue)
                .findAny()
                .orElseThrow(() -> new InvalidInputException("해당 연령대가 존재하지 않습니다."));
    }
}
