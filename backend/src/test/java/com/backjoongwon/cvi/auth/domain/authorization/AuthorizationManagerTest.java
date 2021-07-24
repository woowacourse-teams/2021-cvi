package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.profile.KakaoProfile;
import com.backjoongwon.cvi.auth.domain.profile.NaverProfile;
import com.backjoongwon.cvi.auth.domain.profile.UserInformation;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.willReturn;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("Authorization 매니저 도메인 테스트")
class AuthorizationManagerTest {

    private static final String NAVER_PROFILE_RESPONSE = "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":{\"id\":\"NAVER_ID\",\"nickname\":\"yon\",\"profile_image\":\"naver.com/profile\"}}";
    private static final String KAKAO_PROFILE_RESPONSE = "{\"id\":1816688137,\"connected_at\":\"2021-07-22T05:43:16Z\",\"properties\":{\"nickname\":\"김영빈\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"김영빈\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg\",\"is_default_image\":true}}}";
    public static final String STATE = "STATE";
    public static final String SOCIAL_CODE = "CODE";

    @Autowired
    private AuthorizationManager authorizationManager;

    @MockBean
    private NaverAuthorization naverAuthorization;

    @MockBean
    private KakaoAuthorization kakaoAuthorization;

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserInformation naverUserInfo;
    private UserInformation kakaoUserInfo;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        NaverProfile naverProfile = objectMapper.readValue(NAVER_PROFILE_RESPONSE, NaverProfile.class);
        naverUserInfo = UserInformation.of(naverProfile);
        KakaoProfile kakaoProfile = objectMapper.readValue(KAKAO_PROFILE_RESPONSE, KakaoProfile.class);
        kakaoUserInfo = UserInformation.of(kakaoProfile);
    }

    @DisplayName("Naver Authorization 매니저 유저 정보 요청 - 성공")
    @Test
    void requestNaverUserInfo() {
        //given
        willReturn(naverUserInfo).given(naverAuthorization).requestProfile(SOCIAL_CODE, STATE);
        //when
        //then
        assertThat(authorizationManager.requestUserInfo(SocialProvider.NAVER, SOCIAL_CODE, STATE)).isEqualTo(naverUserInfo);
    }

    @DisplayName("Kakao Authorization 매니저 유저 정보 요청 - 성공")
    @Test
    void requestKakaoUserInfo() {
        //given
        willReturn(kakaoUserInfo).given(kakaoAuthorization).requestProfile(SOCIAL_CODE, null);
        //when
        //then
        assertThat(authorizationManager.requestUserInfo(SocialProvider.KAKAO, SOCIAL_CODE, null)).isEqualTo(kakaoUserInfo);
    }

    @DisplayName("Authorization 매니저 유저 정보 요청 - 실패 - Provider가 유효하지 않은 경우 - Naver")
    @Test
    void requestUserInfoFailureWhenInvalidSocialProviderNaver() {
        //given
        //when
        //then
        assertThatThrownBy(() -> authorizationManager.requestUserInfo(null, SOCIAL_CODE, STATE))
                .isExactlyInstanceOf(InvalidOperationException.class)
                .hasMessage("해당 OAuth 제공자가 존재하지 않습니다");
    }
}