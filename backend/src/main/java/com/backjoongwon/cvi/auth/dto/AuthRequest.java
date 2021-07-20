package com.backjoongwon.cvi.auth.dto;

import com.backjoongwon.cvi.user.domain.SocialProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthRequest {

    private SocialProvider provider;
    private String code;
    private String state;

    public AuthRequest(SocialProvider provider, String code, String state) {
        this.provider = provider;
        this.code = code;
        this.state = state;
    }
}
