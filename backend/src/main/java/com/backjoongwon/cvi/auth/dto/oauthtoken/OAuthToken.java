package com.backjoongwon.cvi.auth.dto.oauthtoken;

import lombok.Getter;

@Getter
public class OAuthToken {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
}
