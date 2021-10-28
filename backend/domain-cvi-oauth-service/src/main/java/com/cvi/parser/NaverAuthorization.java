package com.cvi.parser;

import com.cvi.dto.oauthtoken.NaverOAuthToken;
import com.cvi.dto.oauthtoken.OAuthToken;
import com.cvi.dto.profile.NaverProfile;
import com.cvi.dto.profile.SocialProfile;
import com.cvi.dto.profile.UserInformation;
import com.cvi.exception.MappingFailureException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class NaverAuthorization implements Authorization {

    private static final String PROFILE_REQUEST_URL = "https://openapi.naver.com/v1/nid/me";
    private static final String TOKEN_REQUEST_URL = "https://nid.naver.com/oauth2.0/token";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${security.auth.naver.client-secret}")
    private String clientSecret;

    @Override
    public UserInformation requestProfile(String code, String state, String requestOrigin) {
        OAuthToken naverOAuthToken = requestToken(code, state, requestOrigin);
        return parseProfile(naverOAuthToken);
    }

    @Override
    public UserInformation parseProfile(OAuthToken oAuthToken) {
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = createProfileRequest(oAuthToken);
        ResponseEntity<String> response = sendRequest(naverProfileRequest, PROFILE_REQUEST_URL);
        return UserInformation.of(mapToProfile(response));
    }

    @Override
    public OAuthToken requestToken(String code, String state, String requestOrigin) {
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = createTokenRequest(code, state, requestOrigin);
        ResponseEntity<String> response = sendRequest(naverTokenRequest, TOKEN_REQUEST_URL);
        return mapToOAuthToken(response);
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
    public SocialProfile mapToProfile(ResponseEntity<String> response) {
        try {
            return objectMapper.readValue(response.getBody(), NaverProfile.class);
        } catch (JsonProcessingException e) {
            log.info("토근 정보를 매핑하는데 실패했습니다. {}", response.getBody());
            throw new MappingFailureException("프로필을 매핑하는데 실패했습니다.");
        }
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> createTokenRequest(String code, String state, String requestOrigin) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "nr6cVo7X8bw1cRQCKOQu");
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("state", state);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(params, headers);
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> createProfileRequest(OAuthToken naverOAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + naverOAuthToken.getAccess_token());
        return new HttpEntity<>(headers);
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
