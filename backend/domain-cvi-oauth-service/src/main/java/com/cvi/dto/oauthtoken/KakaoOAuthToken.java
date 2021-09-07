package com.cvi.dto.oauthtoken;

import lombok.Getter;

@Getter
public class KakaoOAuthToken extends OAuthToken {

    private String scope;
    private String refresh_token_expires_in;
}
