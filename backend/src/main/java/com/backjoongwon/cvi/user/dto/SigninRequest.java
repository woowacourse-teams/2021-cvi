package com.backjoongwon.cvi.user.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SigninRequest {

    private String nickname;

    public SigninRequest(String nickname) {
        this.nickname = nickname;
    }
}
