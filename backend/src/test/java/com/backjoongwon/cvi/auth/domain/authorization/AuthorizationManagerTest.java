package com.backjoongwon.cvi.auth.domain.authorization;

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

    @Autowired
    private AuthorizationManager authorizationManager;

    @MockBean
    private NaverAuthorization naverAuthorization;

    @MockBean
    private KakaoAuthorization kakaoAuthorization;

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserInformation userInfo;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        NaverProfile naverProfile = objectMapper.readValue(NAVER_PROFILE_RESPONSE, NaverProfile.class);
        userInfo = UserInformation.of(naverProfile);
    }

    @DisplayName("Naver Authorization 매니저 유저 정보 요청 - 성공")
    @Test
    void requestUserInfo() {
        //given
        willReturn(userInfo).given(naverAuthorization).requestProfile("aa", "aa");
        //when
        //then
        assertThat(authorizationManager.requestUserInfo(SocialProvider.NAVER, "aa", "aa")).isEqualTo(userInfo);
    }

    @DisplayName("Authorization 매니저 유저 정보 요청 - 실패 - Provider가 유효하지 않은 경우")
    @Test
    void requestUserInfoFailureWhenInvalidSocialProvider() {
        //given
        //when
        //then
        assertThatThrownBy(() -> authorizationManager.requestUserInfo(null, "aa", "aa"))
                .isExactlyInstanceOf(InvalidOperationException.class)
                .hasMessage("해당 OAuth 제공자가 존재하지 않습니다");
    }
}