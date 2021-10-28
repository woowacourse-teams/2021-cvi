package com.cvi.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.spy;

import com.cvi.dto.profile.KakaoProfile;
import com.cvi.dto.profile.NaverProfile;
import com.cvi.dto.profile.UserInformation;
import com.cvi.exception.InvalidOperationException;
import com.cvi.user.domain.model.SocialProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

@DisplayName("Authorization 매니저 도메인 테스트")
class AuthorizationManagerTest {

    private static final String NAVER_PROFILE_RESPONSE = "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":{\"id\":\"NAVER_ID\",\"nickname\":\"yon\",\"profile_image\":\"naver.com/profile\"}}";
    private static final String KAKAO_PROFILE_RESPONSE = "{\"id\":1816688137,\"connected_at\":\"2021-07-22T05:43:16Z\",\"properties\":{\"nickname\":\"김영빈\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"김영빈\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg\",\"is_default_image\":true}}}";
    private static final String STATE = "STATE";
    private static final String SOCIAL_CODE = "CODE";
    private static final String REQUEST_ORIGIN = "http://localhost:9000";

    private Map<String, Authorization> authorizationMap = new HashMap<>();
    private AuthorizationManager authorizationManager = new AuthorizationManager(authorizationMap);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private final Authorization naverAuthorization = spy(new NaverAuthorization(restTemplate, objectMapper));
    private final Authorization kakaoAuthorization = spy(new KakaoAuthorization(restTemplate, objectMapper));


    private UserInformation naverUserInfo;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        authorizationMap.put("naverAuthorization", naverAuthorization);
        authorizationMap.put("kakaoAuthorization", kakaoAuthorization);

        NaverProfile naverProfile = objectMapper.readValue(NAVER_PROFILE_RESPONSE, NaverProfile.class);
        naverUserInfo = UserInformation.of(naverProfile);
    }

    @DisplayName("Naver Authorization 매니저 유저 정보 요청 - 성공")
    @Test
    void requestNaverUserInfo() {
        //given
        willReturn(naverUserInfo).given(naverAuthorization).requestProfile(SOCIAL_CODE, STATE, REQUEST_ORIGIN);
        //when
        UserInformation expected = authorizationManager.requestUserInfo(SocialProvider.NAVER, SOCIAL_CODE, STATE, REQUEST_ORIGIN);
        //then
        assertThat(expected).isEqualTo(naverUserInfo);
    }

    @DisplayName("Authorization 매니저 유저 정보 요청 - 실패 - Provider가 Null인 경우")
    @Test
    void requestUserInfoFailureWhenNullSocialProvider() {
        //given
        //when
        //then
        assertThatThrownBy(() -> authorizationManager.requestUserInfo(null, SOCIAL_CODE, STATE, REQUEST_ORIGIN))
                .isExactlyInstanceOf(InvalidOperationException.class)
                .hasMessage("해당 OAuth 제공자가 존재하지 않습니다 입력값: null");
    }

    @DisplayName("Authorization 매니저 유저 정보 요청 - 실패 - Provider가 유효하지 않은 경우")
    @Test
    void requestUserInfoFailureWhenInvalidSocialProvider() {
        //given
        //when
        authorizationMap = spy(HashMap.class);
        authorizationManager = new AuthorizationManager(authorizationMap);
        willReturn(false).given(authorizationMap).containsKey(any());
        //then
        assertThatThrownBy(() -> authorizationManager.requestUserInfo(SocialProvider.KAKAO, SOCIAL_CODE, STATE, REQUEST_ORIGIN))
                .isExactlyInstanceOf(InvalidOperationException.class)
                .hasMessage("해당 OAuth 제공자가 존재하지 않습니다 입력값: kakaoAuthorization");
    }
}
