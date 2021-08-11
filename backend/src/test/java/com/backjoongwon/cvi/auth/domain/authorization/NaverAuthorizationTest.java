package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.dto.oauthtoken.NaverOAuthToken;
import com.backjoongwon.cvi.auth.dto.oauthtoken.OAuthToken;
import com.backjoongwon.cvi.auth.dto.profile.SocialProfile;
import com.backjoongwon.cvi.auth.dto.profile.UserInformation;
import com.backjoongwon.cvi.common.exception.MappingFailureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.spy;

@DisplayName("Naver Authorization 도메인 테스트")
class NaverAuthorizationTest {

    private static final String TOKEN_RESPONSE = "{\"access_token\":\"{ACCESS_TOKEN received from Social Provider}\",\"refresh_token\":\"{REFRESH_TOKEN received from Social Provider}\",\"token_type\":\"bearer\",\"expires_in\":\"3600\"}";
    private static final String PROFILE_RESPONSE = "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":{\"id\":\"NAVER_ID\",\"nickname\":\"yon\",\"profile_image\":\"http://www.naver.com/profile\"}}";
    private static final String TOKEN_REQUEST_URL = "https://nid.naver.com/oauth2.0/token";
    private static final String PROFILE_REQUEST_URL = "https://openapi.naver.com/v1/nid/me";
    private static final String CODE = "CODE";
    private static final String STATE = "STATE";
    private static final String NAVER_ID = "NAVER_ID";
    private static final String NAVER_PROFILE_URL = "http://www.naver.com/profile";
    private static final String BEARER = "bearer";
    private static final String ACCESS_TOKEN = "{ACCESS_TOKEN received from Social Provider}";
    private static final String REFRESH_TOKEN = "{REFRESH_TOKEN received from Social Provider}";
    private static final String EXPIRE_TIME = "3600";

    private NaverAuthorization naverAuthorization = spy(new NaverAuthorization());
    private HttpEntity<MultiValueMap<String, String>> naverTokenRequest;
    private ResponseEntity<String> tokenResponse;
    private ResponseEntity<String> profileResponse;
    private HttpEntity<MultiValueMap<String, String>> naverProfileRequest;

    @BeforeEach
    void beforeEach() {
        naverTokenRequest = naverAuthorization.createTokenRequest(CODE, STATE);
        tokenResponse = ResponseEntity.ok(TOKEN_RESPONSE);
        profileResponse = ResponseEntity.ok(PROFILE_RESPONSE);

        naverProfileRequest = naverAuthorization.createProfileRequest(naverAuthorization.mapToOAuthToken(tokenResponse));

        willReturn(tokenResponse).given(naverAuthorization).sendRequest(naverTokenRequest, TOKEN_REQUEST_URL);
        willReturn(profileResponse).given(naverAuthorization).sendRequest(naverProfileRequest, PROFILE_REQUEST_URL);
    }

    @DisplayName("네이버 프로필 요청 테스트 - 성공")
    @Test
    void requestProfile() {
        //given
        //when
        UserInformation userInformation = naverAuthorization.requestProfile(CODE, STATE);
        //then
        assertThat(userInformation.getSocialId()).isEqualTo(NAVER_ID);
        assertThat(userInformation.getSocialProfileUrl()).isEqualTo(NAVER_PROFILE_URL);
    }

    @DisplayName("토큰 요청 테스트 - 성공")
    @Test
    void requestToken() {
        //given
        //when
        NaverOAuthToken expected = (NaverOAuthToken) naverAuthorization.requestToken(CODE, STATE);
        //then
        assertThat(expected.getToken_type()).isEqualTo(BEARER);
        assertThat(expected.getAccess_token()).isEqualTo(ACCESS_TOKEN);
        assertThat(expected.getRefresh_token()).isEqualTo(REFRESH_TOKEN);
        assertThat(expected.getExpires_in()).isEqualTo(EXPIRE_TIME);
    }

    @DisplayName("토큰 요청 테스트 - 실패")
    @Test
    void requestTokenFailure() {
        //given
        HttpEntity<MultiValueMap<String, String>> invalidToken = naverAuthorization.createTokenRequest("INVALID_TOKEN", STATE);
        willReturn(new ResponseEntity<>("{\"ERROR\":\"ERROR\"}", HttpStatus.BAD_REQUEST)).given(naverAuthorization).sendRequest(invalidToken, TOKEN_REQUEST_URL);
        //when
        //then
        assertThatThrownBy(() -> naverAuthorization.requestToken("INVALID_TOKEN", STATE))
                .isExactlyInstanceOf(MappingFailureException.class);
    }

    @DisplayName("토큰 매핑 테스트 - 성공")
    @Test
    void mapToOAuthToken() {
        //given
        //when
        NaverOAuthToken expected = (NaverOAuthToken) naverAuthorization.mapToOAuthToken(tokenResponse);
        //then
        assertThat(expected.getToken_type()).isEqualTo(BEARER);
        assertThat(expected.getAccess_token()).isEqualTo(ACCESS_TOKEN);
        assertThat(expected.getRefresh_token()).isEqualTo(REFRESH_TOKEN);
        assertThat(expected.getExpires_in()).isEqualTo(EXPIRE_TIME);
    }

    @DisplayName("토큰 매핑 테스트 - 실패 - 올바르지 않은 토큰 Response인 경우")
    @Test
    void mapToOAuthTokenFailureWhenNotValidTokenResponse() {
        //given
        //when
        //then
        assertThatThrownBy(() -> naverAuthorization.mapToOAuthToken(ResponseEntity.ok("NOT_VALID_TOKEN")))
                .isExactlyInstanceOf(MappingFailureException.class);
    }

    @DisplayName("프로필 요청 테스트 - 성공")
    @Test
    void parseProfile() {
        //given
        //when
        UserInformation userInformation = naverAuthorization.parseProfile(naverAuthorization.mapToOAuthToken(tokenResponse));
        //then
        assertThat(userInformation.getSocialId()).isEqualTo(NAVER_ID);
        assertThat(userInformation.getSocialProfileUrl()).isEqualTo("http://www.naver.com/profile");
    }

    @DisplayName("프로필 요청 테스트 - 실패")
    @Test
    void parseProfileFailure() {
        //given
        OAuthToken oAuthToken = naverAuthorization.mapToOAuthToken(tokenResponse);
        HttpEntity<MultiValueMap<String, String>> invalidProfileRequest = naverAuthorization.createProfileRequest(oAuthToken);
        willReturn(new ResponseEntity<>("{\"ERROR\":\"ERROR\"}", HttpStatus.BAD_REQUEST)).given(naverAuthorization).sendRequest(invalidProfileRequest, PROFILE_REQUEST_URL);
        //when
        //then
        assertThatThrownBy(() -> naverAuthorization.parseProfile(oAuthToken))
                .isExactlyInstanceOf(MappingFailureException.class);
    }

    @DisplayName("프로필 매핑 테스트 - 성공")
    @Test
    void mapToProfile() {
        //given
        //when
        SocialProfile socialProfile = naverAuthorization.mapToProfile(profileResponse);
        //then
        assertThat(socialProfile.extractSocialId()).isEqualTo(NAVER_ID);
        assertThat(socialProfile.extractProfileUrl()).isEqualTo("http://www.naver.com/profile");
    }

    @DisplayName("프로필 매핑 테스트 - 실패 - 올바르지 않은 프로필 Response 인 경우")
    @Test
    void mapToProfileFailureWhenInvalidProfileResponse() {
        //given
        //when
        //then
        assertThatThrownBy(() -> naverAuthorization.mapToProfile(ResponseEntity.ok("NOT_VALID_PROFILE")))
                .isExactlyInstanceOf(MappingFailureException.class);
    }
}
