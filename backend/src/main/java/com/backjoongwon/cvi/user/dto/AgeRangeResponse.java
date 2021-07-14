package com.backjoongwon.cvi.user.dto;

import com.backjoongwon.cvi.user.domain.AgeRange;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgeRangeResponse {

    private String meaning;
    private int minAge;
    private int maxAge;

    public AgeRangeResponse(AgeRange ageRange) {
        this.meaning = ageRange.getMeaning();
        this.minAge = ageRange.getMinAge();
        this.maxAge = ageRange.getMaxAge();
    }
}
