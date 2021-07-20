package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.OAuthToken;
import com.backjoongwon.cvi.auth.domain.Profile;
import com.backjoongwon.cvi.auth.domain.kakao.KakaoProfile;
import com.backjoongwon.cvi.user.domain.SocialProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAuthorization implements Authorization {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Profile requestProfile(String code, String state) {
        OAuthToken oAuthToken = requestToken(code);
        return parseProfile(oAuthToken);
    }

    @Override
    public OAuthToken requestToken(String code) {
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = createTokenRequest(code);
        ResponseEntity<String> response = sendRequest(kakaoTokenRequest, "https://kauth.kakao.com/oauth/token");

        OAuthToken oauthToken = mapToOAuthToken(response);
        return oauthToken;
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> createTokenRequest(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "1a06cf63be2ce0a6ebd8f49cd534e1c9");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        return kakaoTokenRequest;
    }

    @Override
    public OAuthToken mapToOAuthToken(ResponseEntity<String> response) {
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return oauthToken;
    }

    @Override
    public Profile parseProfile(OAuthToken oAuthToken) {
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = createProfileRequest(oAuthToken);
        ResponseEntity<String> response = sendRequest(kakaoProfileRequest, "https://kapi.kakao.com/v2/user/me");

        KakaoProfile socialProfile = mapToProfile(response);
        return new Profile(socialProfile.getId(), socialProfile.getProfileImage());
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> createProfileRequest(OAuthToken oAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
        return kakaoProfileRequest;
    }

    @Override
    public KakaoProfile mapToProfile(ResponseEntity<String> response) {
        KakaoProfile profile = null;
        try {
            profile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return profile;
    }

    @Override
    public ResponseEntity<String> sendRequest(HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest, String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
    }
}
