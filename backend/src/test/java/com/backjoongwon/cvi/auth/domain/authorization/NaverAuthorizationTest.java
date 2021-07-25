package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.oauthtoken.NaverOAuthToken;
import com.backjoongwon.cvi.auth.domain.oauthtoken.OAuthToken;
import com.backjoongwon.cvi.auth.domain.profile.SocialProfile;
import com.backjoongwon.cvi.auth.domain.profile.UserInformation;
import com.backjoongwon.cvi.common.exception.MappingFailureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.spy;

@ActiveProfiles("test")
@DisplayName("Naver Authorization 도메인 테스트")
class NaverAuthorizationTest {

    private static final String TOKEN_RESPONSE = "{\"access_token\":\"{ACCESS_TOKEN}\",\"refresh_token\":\"{REFRESH_TOKEN}\",\"token_type\":\"bearer\",\"expires_in\":\"3600\"}";
    private static final String PROFILE_RESPONSE = "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":{\"id\":\"NAVER_ID\",\"nickname\":\"yon\",\"profile_image\":\"http://www.naver.com/profile\"}}";
    private static final String TOKEN_REQUEST_URL = "https://nid.naver.com/oauth2.0/token";
    private static final String PROFILE_REQUEST_URL = "https://openapi.naver.com/v1/nid/me";

    private NaverAuthorization naverAuthorization = spy(new NaverAuthorization());
    private HttpEntity<MultiValueMap<String, String>> naverTokenRequest;
    private ResponseEntity<String> tokenResponse;
    private ResponseEntity<String> profileResponse;
    private HttpEntity<MultiValueMap<String, String>> naverProfileRequest;

    @BeforeEach
    void beforeEach() {
        naverTokenRequest = naverAuthorization.createTokenRequest("CODE", "STATE");
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
        UserInformation userInformation = naverAuthorization.requestProfile("CODE", "STATE");
        //then
        assertThat(userInformation.getSocialId()).isEqualTo("NAVER_ID");
        assertThat(userInformation.getSocialProfileUrl()).isEqualTo("http://www.naver.com/profile");
    }

    @DisplayName("토큰 요청 테스트 - 성공")
    @Test
    void requestToken() {
        //given
        //when
        NaverOAuthToken expected = (NaverOAuthToken) naverAuthorization.requestToken("CODE", "STATE");
        //then
        assertThat(expected.getToken_type()).isEqualTo("bearer");
        assertThat(expected.getAccess_token()).isEqualTo("{ACCESS_TOKEN}");
        assertThat(expected.getRefresh_token()).isEqualTo("{REFRESH_TOKEN}");
        assertThat(expected.getExpires_in()).isEqualTo("3600");
    }

    @DisplayName("토큰 요청 테스트 - 실패")
    @Test
    void requestTokenFailure() {
        //given
        HttpEntity<MultiValueMap<String, String>> invalidToken = naverAuthorization.createTokenRequest("INVALID_TOKEN", "STATE");
        willReturn(new ResponseEntity<>("{\"ERROR\":\"ERROR\"}", HttpStatus.BAD_REQUEST)).given(naverAuthorization).sendRequest(invalidToken, TOKEN_REQUEST_URL);
        //when
        //then
        assertThatThrownBy(() -> naverAuthorization.requestToken("INVALID_TOKEN", "STATE"))
                .isExactlyInstanceOf(MappingFailureException.class)
                .hasMessage("토큰 정보를 불러오는 데 실패했습니다.");
    }

    @DisplayName("토큰 매핑 테스트 - 성공")
    @Test
    void mapToOAuthToken() {
        //given
        //when
        NaverOAuthToken expected = (NaverOAuthToken) naverAuthorization.mapToOAuthToken(tokenResponse);
        //then
        assertThat(expected.getToken_type()).isEqualTo("bearer");
        assertThat(expected.getAccess_token()).isEqualTo("{ACCESS_TOKEN}");
        assertThat(expected.getRefresh_token()).isEqualTo("{REFRESH_TOKEN}");
        assertThat(expected.getExpires_in()).isEqualTo("3600");
    }

    @DisplayName("토큰 매핑 테스트 - 실패 - 올바르지 않은 토큰 Response인 경우")
    @Test
    void mapToOAuthTokenFailureWhenNotValidTokenResponse() {
        //given
        //when
        //then
        assertThatThrownBy(() -> naverAuthorization.mapToOAuthToken(ResponseEntity.ok("NOT_VALID_TOKEN")))
                .isExactlyInstanceOf(MappingFailureException.class)
                .hasMessage("토큰 정보를 불러오는 데 실패했습니다.");
    }

    @DisplayName("프로필 요청 테스트 - 성공")
    @Test
    void parseProfile() {
        //given
        //when
        UserInformation userInformation = naverAuthorization.parseProfile(naverAuthorization.mapToOAuthToken(tokenResponse));
        //then
        assertThat(userInformation.getSocialId()).isEqualTo("NAVER_ID");
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
                .isExactlyInstanceOf(MappingFailureException.class)
                .hasMessage("프로필을 불러오는 데 실패했습니다.");
    }

    @DisplayName("프로필 매핑 테스트 - 성공")
    @Test
    void mapToProfile() {
        //given
        //when
        SocialProfile socialProfile = naverAuthorization.mapToProfile(profileResponse);
        //then
        assertThat(socialProfile.extractSocialId()).isEqualTo("NAVER_ID");
        assertThat(socialProfile.extractProfileUrl()).isEqualTo("http://www.naver.com/profile");
    }

    @DisplayName("프로필 매핑 테스트 - 실패 - 올바르지 않은 프로필 Response 인 경우")
    @Test
    void mapToProfileFailureWhenInvalidProfileResponse() {
        //given
        //when
        //then
        assertThatThrownBy(() -> naverAuthorization.mapToProfile(ResponseEntity.ok("NOT_VALID_PROFILE")))
                .isExactlyInstanceOf(MappingFailureException.class)
                .hasMessage("프로필을 불러오는 데 실패했습니다.");
    }
}