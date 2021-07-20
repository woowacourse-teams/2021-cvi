package com.backjoongwon.cvi.user.domain;

import lombok.Getter;

@Getter
public enum AgeRange {

    TEENS("10대", 10, 20),
    TWENTIES("20대", 20, 30),
    THIRTIES("30대", 30, 40),
    FORTIES("40대", 40, 50),
    FIFTIES("50대", 50, 60),
    OVER_SIXTIES("60대", 60, Integer.MAX_VALUE);

    private final String meaning;
    private final int minAge;
    private final int maxAge;

    AgeRange(String meaning, int minAge, int maxAge) {
        this.meaning = meaning;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }
}
