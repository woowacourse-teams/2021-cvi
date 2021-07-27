package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.oauthtoken.KakaoOAuthToken;
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
@DisplayName("Kakao Authorization 도메인 테스트")
class KakaoAuthorizationTest {

    private static final String TOKEN_RESPONSE = "{\"token_type\":\"bearer\",\"access_token\":\"{ACCESS_TOKEN}\",\"expires_in\":\"43199\",\"refresh_token\":\"{REFRESH_TOKEN}\",\"refresh_token_expires_in\":\"25184000\",\"scope\":\"account_email profile\"}";
    private static final String PROFILE_RESPONSE = "{\"id\":1816688137,\"connected_at\":\"2021-07-22T05:43:16Z\",\"properties\":{\"nickname\":\"김영빈\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"김영빈\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg\",\"is_default_image\":true}}}";
    private static final String TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
    private static final String PROFILE_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";

    private KakaoAuthorization kakaoAuthorization = spy(new KakaoAuthorization());
    private HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest;
    private ResponseEntity<String> tokenResponse;
    private ResponseEntity<String> profileResponse;
    private HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest;

    @BeforeEach
    void beforeEach() {
        kakaoTokenRequest = kakaoAuthorization.createTokenRequest("CODE", null);
        tokenResponse = ResponseEntity.ok(TOKEN_RESPONSE);
        profileResponse = ResponseEntity.ok(PROFILE_RESPONSE);

        kakaoProfileRequest = kakaoAuthorization.createProfileRequest(kakaoAuthorization.mapToOAuthToken(tokenResponse));

        willReturn(tokenResponse).given(kakaoAuthorization).sendRequest(kakaoTokenRequest, TOKEN_REQUEST_URL);
        willReturn(profileResponse).given(kakaoAuthorization).sendRequest(kakaoProfileRequest, PROFILE_REQUEST_URL);
    }

    @DisplayName("카카오 프로필 요청 테스트 - 성공")
    @Test
    void requestProfile() {
        //given
        //when
        UserInformation userInformation = kakaoAuthorization.requestProfile("CODE", null);
        //then
        assertThat(userInformation.getSocialId()).isEqualTo("1816688137");
        assertThat(userInformation.getSocialProfileUrl()).isEqualTo("http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg");
    }

    @DisplayName("토큰 요청 테스트 - 성공")
    @Test
    void requestToken() {
        //given
        //when
        KakaoOAuthToken expected = (KakaoOAuthToken) kakaoAuthorization.requestToken("CODE", null);
        //then
        assertThat(expected.getToken_type()).isEqualTo("bearer");
        assertThat(expected.getAccess_token()).isEqualTo("{ACCESS_TOKEN}");
        assertThat(expected.getExpires_in()).isEqualTo("43199");
        assertThat(expected.getRefresh_token()).isEqualTo("{REFRESH_TOKEN}");
        assertThat(expected.getRefresh_token_expires_in()).isEqualTo("25184000");
        assertThat(expected.getScope()).isEqualTo("account_email profile");
    }

    @DisplayName("토큰 요청 테스트 - 실패")
    @Test
    void requestTokenFailure() {
        //given
        HttpEntity<MultiValueMap<String, String>> invalidToken = kakaoAuthorization.createTokenRequest("INVALID_TOKEN", null);
        willReturn(new ResponseEntity<>("{\"ERROR\":\"ERROR\"}", HttpStatus.BAD_REQUEST)).given(kakaoAuthorization).sendRequest(invalidToken, TOKEN_REQUEST_URL);
        //when
        //then
        assertThatThrownBy(() -> kakaoAuthorization.requestToken("INVALID_TOKEN", null))
                .isExactlyInstanceOf(MappingFailureException.class)
                .hasMessage("토큰 정보를 불러오는 데 실패했습니다.");
    }

    @DisplayName("토큰 매핑 테스트 - 성공")
    @Test
    void mapToOAuthToken() {
        //given
        //when
        KakaoOAuthToken expected = (KakaoOAuthToken) kakaoAuthorization.mapToOAuthToken(tokenResponse);
        //then
        assertThat(expected.getToken_type()).isEqualTo("bearer");
        assertThat(expected.getAccess_token()).isEqualTo("{ACCESS_TOKEN}");
        assertThat(expected.getExpires_in()).isEqualTo("43199");
        assertThat(expected.getRefresh_token()).isEqualTo("{REFRESH_TOKEN}");
        assertThat(expected.getRefresh_token_expires_in()).isEqualTo("25184000");
        assertThat(expected.getScope()).isEqualTo("account_email profile");
    }

    @DisplayName("토큰 매핑 테스트 - 실패 - 올바르지 않은 토큰 Response인 경우")
    @Test
    void mapToOAuthTokenFailureWhenNotValidTokenResponse() {
        //given
        //when
        //then
        assertThatThrownBy(() -> kakaoAuthorization.mapToOAuthToken(ResponseEntity.ok("NOT_VALID_TOKEN")))
                .isExactlyInstanceOf(MappingFailureException.class)
                .hasMessage("토큰 정보를 불러오는 데 실패했습니다.");
    }

    @DisplayName("프로필 요청 테스트 - 성공")
    @Test
    void parseProfile() {
        //given
        //when
        UserInformation userInformation = kakaoAuthorization.parseProfile(kakaoAuthorization.mapToOAuthToken(tokenResponse));
        //then
        assertThat(userInformation.getSocialId()).isEqualTo("1816688137");
        assertThat(userInformation.getSocialProfileUrl()).isEqualTo("http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg");
    }

    @DisplayName("프로필 요청 테스트 - 실패")
    @Test
    void parseProfileFailure() {
        //given
        OAuthToken oAuthToken = kakaoAuthorization.mapToOAuthToken(tokenResponse);
        HttpEntity<MultiValueMap<String, String>> invalidProfileRequest = kakaoAuthorization.createProfileRequest(oAuthToken);
        willReturn(new ResponseEntity<>("{\"ERROR\":\"ERROR\"}", HttpStatus.BAD_REQUEST)).given(kakaoAuthorization).sendRequest(invalidProfileRequest, PROFILE_REQUEST_URL);
        //when
        //then
        assertThatThrownBy(() -> kakaoAuthorization.parseProfile(oAuthToken))
                .isExactlyInstanceOf(MappingFailureException.class)
                .hasMessage("프로필을 불러오는 데 실패했습니다.");
    }

    @DisplayName("프로필 매핑 테스트 - 성공")
    @Test
    void mapToProfile() {
        //given
        //when
        SocialProfile socialProfile = kakaoAuthorization.mapToProfile(profileResponse);
        //then
        assertThat(socialProfile.extractSocialId()).isEqualTo("1816688137");
        assertThat(socialProfile.extractProfileUrl()).isEqualTo("http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg");
    }

    @DisplayName("프로필 매핑 테스트 - 실패 - 올바르지 않은 프로필 Response 인 경우")
    @Test
    void mapToProfileFailureWhenInvalidProfileResponse() {
        //given
        //when
        //then
        assertThatThrownBy(() -> kakaoAuthorization.mapToProfile(ResponseEntity.ok("NOT_VALID_PROFILE")))
                .isExactlyInstanceOf(MappingFailureException.class)
                .hasMessage("프로필을 불러오는 데 실패했습니다.");
    }
}
