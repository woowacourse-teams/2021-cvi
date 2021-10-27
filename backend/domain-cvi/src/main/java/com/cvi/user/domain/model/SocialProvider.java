package com.cvi.user.domain.model;

import lombok.Getter;

@Getter
public enum SocialProvider {
    NAVER("naverAuthorization"),
    KAKAO("kakaoAuthorization");

    private final String provider;

    SocialProvider(String provider) {
        this.provider = provider;
    }
}
