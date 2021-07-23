package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.oauthtoken.KakaoOAuthToken;
import com.backjoongwon.cvi.auth.domain.oauthtoken.OAuthToken;
import com.backjoongwon.cvi.auth.domain.profile.KakaoProfile;
import com.backjoongwon.cvi.auth.domain.profile.SocialProfile;
import com.backjoongwon.cvi.auth.domain.profile.UserInformation;
import com.backjoongwon.cvi.common.exception.MappingFailureException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class KakaoAuthorization implements Authorization {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserInformation requestProfile(String code, String state) {
        OAuthToken kakaoOAuthToken = requestToken(code, state);
        return parseProfile(kakaoOAuthToken);
    }

    @Override
    public OAuthToken requestToken(String code, String state) {
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = createTokenRequest(code, state);
        ResponseEntity<String> response = sendRequest(kakaoTokenRequest, "https://kauth.kakao.com/oauth/token");

        return mapToOAuthToken(response);
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> createTokenRequest(String code, String state) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "1a06cf63be2ce0a6ebd8f49cd534e1c9");
        params.add("redirect_uri", "http://localhost:9000/auth/kakao/callback");
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        return kakaoTokenRequest;
    }

    @Override
    public OAuthToken mapToOAuthToken(ResponseEntity<String> response) {
        try {
            return objectMapper.readValue(response.getBody(), KakaoOAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new MappingFailureException("토큰 정보를 불러오는 데 실패했습니다.");
        }
    }

    @Override
    public UserInformation parseProfile(OAuthToken oAuthToken) {
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = createProfileRequest(oAuthToken);
        ResponseEntity<String> response = sendRequest(kakaoProfileRequest, "https://kapi.kakao.com/v2/user/me");

        SocialProfile socialProfile = mapToProfile(response);
        return UserInformation.of(socialProfile);
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> createProfileRequest(OAuthToken kakaoOAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoOAuthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
        return kakaoProfileRequest;
    }

    @Override
    public SocialProfile mapToProfile(ResponseEntity<String> response) {
        try {
            return objectMapper.readValue(response.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new MappingFailureException("프로필을 불러오는 데 실패했습니다.");
        }
    }

    @Override
    public ResponseEntity<String> sendRequest(HttpEntity<MultiValueMap<String, String>> request, String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );
    }
}
