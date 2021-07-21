package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.oauthtoken.OAuthToken;
import com.backjoongwon.cvi.auth.domain.profile.SocialProfile;
import com.backjoongwon.cvi.auth.domain.profile.UserInformation;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface Authorization {
    UserInformation requestProfile(String code, String state);

    OAuthToken requestToken(String code, String state);

    HttpEntity<MultiValueMap<String, String>> createTokenRequest(String code, String state);

    OAuthToken mapToOAuthToken(ResponseEntity<String> response);

    UserInformation parseProfile(OAuthToken oAuthToken);

    HttpEntity<MultiValueMap<String, String>> createProfileRequest(OAuthToken oAuthToken);

    SocialProfile mapToProfile(ResponseEntity<String> response);

    ResponseEntity<String> sendRequest(HttpEntity<MultiValueMap<String, String>> profileRequest, String url);
}
