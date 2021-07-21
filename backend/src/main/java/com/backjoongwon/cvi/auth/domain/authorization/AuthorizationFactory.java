package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.user.domain.SocialProvider;

public class AuthorizationFactory {

    public Authorization of(SocialProvider provider) {

        if (provider == SocialProvider.KAKAO) {
            return new KakaoAuthorization();
        }

        if (provider == SocialProvider.NAVER) {
            return new NaverAuthorization();
        }

        return null;
    }
}
