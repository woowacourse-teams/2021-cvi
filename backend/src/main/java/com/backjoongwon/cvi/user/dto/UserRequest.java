package com.backjoongwon.cvi.user.dto;

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
}
