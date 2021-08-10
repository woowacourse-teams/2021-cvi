package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.oauthtoken.NaverOAuthToken;
import com.backjoongwon.cvi.auth.domain.oauthtoken.OAuthToken;
import com.backjoongwon.cvi.auth.domain.profile.NaverProfile;
import com.backjoongwon.cvi.auth.domain.profile.SocialProfile;
import com.backjoongwon.cvi.auth.domain.profile.UserInformation;
import com.backjoongwon.cvi.common.exception.MappingFailureException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class NaverAuthorization implements Authorization {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${security.auth.naver.client-secret}")
    private String clientSecret;

    @Override
    public UserInformation requestProfile(String code, String state) {
        OAuthToken naverOAuthToken = requestToken(code, state);
        return parseProfile(naverOAuthToken);
    }

    @Override
    public OAuthToken requestToken(String code, String state) {
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = createTokenRequest(code, state);
        ResponseEntity<String> response = sendRequest(naverTokenRequest, "https://nid.naver.com/oauth2.0/token");

        return mapToOAuthToken(response);
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> createTokenRequest(String code, String state) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "nr6cVo7X8bw1cRQCKOQu");
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("state", state);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params, headers);
        return naverTokenRequest;
    }

    @Override
    public OAuthToken mapToOAuthToken(ResponseEntity<String> response) {
        try {
            return objectMapper.readValue(response.getBody(), NaverOAuthToken.class);
        } catch (JsonProcessingException e) {
            log.info("토근 정보를 매핑하는데 실패했습니다. {}", response.getBody());
            throw new MappingFailureException("토큰 정보를 매핑하는데 실패했습니다.");
        }
    }

    @Override
    public UserInformation parseProfile(OAuthToken oAuthToken) {
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = createProfileRequest(oAuthToken);
        ResponseEntity<String> response = sendRequest(naverProfileRequest, "https://openapi.naver.com/v1/nid/me");

        SocialProfile socialProfile = mapToProfile(response);
        return UserInformation.of(socialProfile);
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> createProfileRequest(OAuthToken naverOAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + naverOAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(headers);
        return naverProfileRequest;
    }

    @Override
    public SocialProfile mapToProfile(ResponseEntity<String> response) {
        try {
            return objectMapper.readValue(response.getBody(), NaverProfile.class);
        } catch (JsonProcessingException e) {
            throw new MappingFailureException("프로필을 매핑하는데 실패했습니다.");
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
