package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.dto.oauthtoken.OAuthToken;
import com.backjoongwon.cvi.auth.dto.profile.SocialProfile;
import com.backjoongwon.cvi.auth.dto.profile.UserInformation;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface Authorization {

    UserInformation requestProfile(String code, String state, String requestOrigin);

    UserInformation parseProfile(OAuthToken oAuthToken);

    OAuthToken requestToken(String code, String state, String requestOrigin);

    OAuthToken mapToOAuthToken(ResponseEntity<String> response);

    SocialProfile mapToProfile(ResponseEntity<String> response);

    HttpEntity<MultiValueMap<String, String>> createTokenRequest(String code, String state, String requestOrigin);

    HttpEntity<MultiValueMap<String, String>> createProfileRequest(OAuthToken oAuthToken);

    ResponseEntity<String> sendRequest(HttpEntity<MultiValueMap<String, String>> request, String url);
}
