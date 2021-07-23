package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.profile.UserInformation;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;

import java.util.Arrays;

public enum SocialProvider {

    NAVER(new NaverAuthorization()),
    KAKAO(new KakaoAuthorization());

    private final Authorization authorization;

    SocialProvider(Authorization authorization) {
        this.authorization = authorization;
    }

    public static UserInformation requestProfile(SocialProvider provider, String code, String state) {
        Authorization authorization = findAuthorization(provider);

        return authorization.requestProfile(code, state);
    }

    private static Authorization findAuthorization(SocialProvider provider) {
        return Arrays.stream(values())
                .filter(sp -> sp.equals(provider))
                .findFirst()
                .map(SocialProvider::getAuthorization)
                .orElseThrow(() -> new InvalidOperationException("잘못된 로그인 요청입니다."));
    }

    public Authorization getAuthorization() {
        return authorization;
    }
}
