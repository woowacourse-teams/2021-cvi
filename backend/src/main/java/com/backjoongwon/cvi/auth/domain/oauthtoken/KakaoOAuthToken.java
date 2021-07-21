package com.backjoongwon.cvi.auth.domain.oauthtoken;

import lombok.Getter;

//빼고 돌려보기
@Getter
public class KakaoOAuthToken extends OAuthToken {

    private String scope;
    private String refresh_token_expires_in;
}
