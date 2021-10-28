package com.cvi.parser;

import com.cvi.dto.oauthtoken.KakaoOAuthToken;
import com.cvi.dto.oauthtoken.OAuthToken;
import com.cvi.dto.profile.SocialProfile;
import com.cvi.dto.profile.UserInformation;
import com.cvi.exception.MappingFailureException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.spy;

@DisplayName("Kakao Authorization 도메인 테스트")
class KakaoAuthorizationTest {
    private static final String TOKEN_RESPONSE = "{\"token_type\":\"bearer\",\"access_token\":\"{ACCESS_TOKEN received from Social Provider}\",\"expires_in\":\"43199\",\"refresh_token\":\"{REFRESH_TOKEN received from Social Provider}\",\"refresh_token_expires_in\":\"25184000\",\"scope\":\"account_email profile\"}";
    private static final String PROFILE_RESPONSE = "{\"id\":1816688137,\"connected_at\":\"2021-07-22T05:43:16Z\",\"properties\":{\"nickname\":\"김영빈\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"김영빈\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg\",\"is_default_image\":true}}}";
    private static final String TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
    private static final String PROFILE_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String CODE = "CODE";
    private static final String KAKAO_SOCIAL_ID = "1816688137";
    private static final String BEARER = "bearer";
    private static final String ACCESS_TOKEN = "{ACCESS_TOKEN received from Social Provider}";
    private static final String EXPIRE_TIME = "43199";
    private static final String REFRESH_TOKEN = "{REFRESH_TOKEN received from Social Provider}";
    private static final String TOKEN_EXPIRE_TIME = "25184000";
    private static final String SCOPE = "account_email profile";
    private static final String REQUEST_ORIGIN = "http://localhost:9000";

    private final RestTemplate restTemplate = spy(RestTemplate.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Authorization kakaoAuthorization = new KakaoAuthorization(restTemplate, objectMapper);
    private HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest;
    private ResponseEntity<String> tokenResponse;
    private ResponseEntity<String> profileResponse;

    @BeforeEach
    void beforeEach() {
        kakaoTokenRequest = kakaoAuthorization.createTokenRequest(CODE, null, REQUEST_ORIGIN);
        tokenResponse = ResponseEntity.ok(TOKEN_RESPONSE);
        profileResponse = ResponseEntity.ok(PROFILE_RESPONSE);

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = kakaoAuthorization.createProfileRequest(kakaoAuthorization.mapToOAuthToken(tokenResponse));

        willReturn(tokenResponse).given(restTemplate).exchange(TOKEN_REQUEST_URL, HttpMethod.POST, kakaoTokenRequest, String.class);
        willReturn(profileResponse).given(restTemplate).exchange(PROFILE_REQUEST_URL, HttpMethod.POST, kakaoProfileRequest, String.class);
    }

    @DisplayName("카카오 프로필 요청 테스트 - 성공")
    @Test
    void requestProfile() {
        //given
        //when
        UserInformation userInformation = kakaoAuthorization.requestProfile(CODE, null, REQUEST_ORIGIN);
        //then
        assertThat(userInformation.getSocialId()).isEqualTo(KAKAO_SOCIAL_ID);
        assertThat(userInformation.getSocialProfileUrl()).isEqualTo("http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg");
    }

    @DisplayName("토큰 요청 테스트 - 성공")
    @Test
    void requestToken() {
        //given
        //when
        KakaoOAuthToken expected = (KakaoOAuthToken) kakaoAuthorization.requestToken(CODE, null, REQUEST_ORIGIN);
        //then
        assertThat(expected.getToken_type()).isEqualTo(BEARER);
        assertThat(expected.getAccess_token()).isEqualTo(ACCESS_TOKEN);
        assertThat(expected.getExpires_in()).isEqualTo(EXPIRE_TIME);
        assertThat(expected.getRefresh_token()).isEqualTo(REFRESH_TOKEN);
        assertThat(expected.getRefresh_token_expires_in()).isEqualTo(TOKEN_EXPIRE_TIME);
        assertThat(expected.getScope()).isEqualTo(SCOPE);
    }

    @DisplayName("토큰 요청 테스트 - 실패")
    @Test
    void requestTokenFailure() {
        //given
        HttpEntity<MultiValueMap<String, String>> invalidToken = kakaoAuthorization.createTokenRequest("INVALID_TOKEN", null, REQUEST_ORIGIN);
        willReturn(new ResponseEntity<>("{\"ERROR\":\"ERROR\"}", HttpStatus.BAD_REQUEST)).given(restTemplate).exchange(TOKEN_REQUEST_URL, HttpMethod.POST, invalidToken, String.class);
        //when
        //then
        assertThatThrownBy(() -> kakaoAuthorization.requestToken("INVALID_TOKEN", null, REQUEST_ORIGIN))
                .isExactlyInstanceOf(MappingFailureException.class);
    }

    @DisplayName("토큰 매핑 테스트 - 성공")
    @Test
    void mapToOAuthToken() {
        //given
        //when
        KakaoOAuthToken expected = (KakaoOAuthToken) kakaoAuthorization.mapToOAuthToken(tokenResponse);
        //then
        assertThat(expected.getToken_type()).isEqualTo(BEARER);
        assertThat(expected.getAccess_token()).isEqualTo(ACCESS_TOKEN);
        assertThat(expected.getExpires_in()).isEqualTo(EXPIRE_TIME);
        assertThat(expected.getRefresh_token()).isEqualTo(REFRESH_TOKEN);
        assertThat(expected.getRefresh_token_expires_in()).isEqualTo(TOKEN_EXPIRE_TIME);
        assertThat(expected.getScope()).isEqualTo(SCOPE);
    }

    @DisplayName("토큰 매핑 테스트 - 실패 - 올바르지 않은 토큰 Response인 경우")
    @Test
    void mapToOAuthTokenFailureWhenNotValidTokenResponse() {
        //given
        //when
        //then
        assertThatThrownBy(() -> kakaoAuthorization.mapToOAuthToken(ResponseEntity.ok("NOT_VALID_TOKEN")))
                .isExactlyInstanceOf(MappingFailureException.class);
    }

    @DisplayName("프로필 요청 테스트 - 성공")
    @Test
    void parseProfile() {
        //given
        //when
        UserInformation userInformation = kakaoAuthorization.parseProfile(kakaoAuthorization.mapToOAuthToken(tokenResponse));
        //then
        assertThat(userInformation.getSocialId()).isEqualTo(KAKAO_SOCIAL_ID);
        assertThat(userInformation.getSocialProfileUrl()).isEqualTo("http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg");
    }

    @DisplayName("프로필 요청 테스트 - 실패")
    @Test
    void parseProfileFailure() {
        //given
        OAuthToken oAuthToken = kakaoAuthorization.mapToOAuthToken(tokenResponse);
        HttpEntity<MultiValueMap<String, String>> invalidProfileRequest = kakaoAuthorization.createProfileRequest(oAuthToken);
        willReturn(new ResponseEntity<>("{\"ERROR\":\"ERROR\"}", HttpStatus.BAD_REQUEST)).given(restTemplate).exchange(PROFILE_REQUEST_URL, HttpMethod.POST, invalidProfileRequest, String.class);
        //when
        //then
        assertThatThrownBy(() -> kakaoAuthorization.parseProfile(oAuthToken))
                .isExactlyInstanceOf(MappingFailureException.class);
    }

    @DisplayName("프로필 매핑 테스트 - 성공")
    @Test
    void mapToProfile() {
        //given
        //when
        SocialProfile socialProfile = kakaoAuthorization.mapToProfile(profileResponse);
        //then
        assertThat(socialProfile.extractSocialId()).isEqualTo(KAKAO_SOCIAL_ID);
        assertThat(socialProfile.extractProfileUrl()).isEqualTo("http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg");
    }

    @DisplayName("프로필 매핑 테스트 - 실패 - 올바르지 않은 프로필 Response 인 경우")
    @Test
    void mapToProfileFailureWhenInvalidProfileResponse() {
        //given
        //when
        //then
        assertThatThrownBy(() -> kakaoAuthorization.mapToProfile(ResponseEntity.ok("NOT_VALID_PROFILE")))
                .isExactlyInstanceOf(MappingFailureException.class);
    }

    @DisplayName("외부요청 테스트 - 성공")
    @Test
    void sendRequest() {
        //given
        //when
        //then
        assertThat(kakaoAuthorization.sendRequest(kakaoTokenRequest, TOKEN_REQUEST_URL)).isEqualTo(tokenResponse);
    }
}
