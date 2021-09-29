package com.cvi.user.domain.model;

public enum SocialProvider {
    NAVER("naverAuthorization"),
    KAKAO("kakaoAuthorization");

    private final String provider;

    SocialProvider(String provider) {
        this.provider = provider;
    }

    public String convertToComponentName() {
        return this.provider;
    }
}
