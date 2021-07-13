package com.backjoongwon.cvi.user.dto;

import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {

    private String nickname;
    private int ageRange;

    public UserRequest(String nickname, int ageRange) {
        this.nickname = nickname;
        this.ageRange = ageRange;
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .ageRange(AgeRange.convertFromValue(ageRange))
                .build();
    }
}
