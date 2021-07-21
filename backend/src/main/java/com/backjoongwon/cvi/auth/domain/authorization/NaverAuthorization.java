package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.oauthtoken.NaverOAuthToken;
import com.backjoongwon.cvi.auth.domain.oauthtoken.OAuthToken;
import com.backjoongwon.cvi.auth.domain.profile.NaverProfile;
import com.backjoongwon.cvi.auth.domain.profile.SocialProfile;
import com.backjoongwon.cvi.auth.domain.profile.UserInformation;
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
public class NaverAuthorization implements Authorization {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        params.add("client_id", "8nbRZEMcC6ZKkwCgUqNr");
        params.add("redirect_uri", "http://localhost:9000/auth/naver/callback");
        params.add("code", code);
        params.add("state", state);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params, headers);
        return naverTokenRequest;
    }

    @Override
    public OAuthToken mapToOAuthToken(ResponseEntity<String> response) {
        NaverOAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), NaverOAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return oauthToken;
    }

    @Override
    public UserInformation parseProfile(OAuthToken oAuthToken) {
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = createProfileRequest(oAuthToken);
        ResponseEntity<String> response = sendRequest(naverProfileRequest, "https://openapi.naver.com/v1/nid/me");

        SocialProfile socialProfile = mapToProfile(response);
        return new UserInformation(socialProfile.getId(), socialProfile.getProfileImage());
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
        NaverProfile profile = null;
        try {
            return objectMapper.readValue(response.getBody(), NaverProfile.class);
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
