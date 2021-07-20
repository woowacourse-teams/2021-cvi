package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.OAuthToken;
import com.backjoongwon.cvi.auth.domain.Profile;
import com.backjoongwon.cvi.auth.domain.kakao.KakaoProfile;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface Authorization {
    Profile requestProfile(String code, String state);

    OAuthToken requestToken(String code);

    HttpEntity<MultiValueMap<String, String>> createTokenRequest(String code);

    OAuthToken mapToOAuthToken(ResponseEntity<String> response);

    Profile parseProfile(OAuthToken oAuthToken);

    HttpEntity<MultiValueMap<String, String>> createProfileRequest(OAuthToken oAuthToken);

    KakaoProfile mapToProfile(ResponseEntity<String> response);

    ResponseEntity<String> sendRequest(HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest, String url);
}
