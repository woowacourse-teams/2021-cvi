package com.cvi.service;

import com.cvi.auth.JwtTokenProvider;
import com.cvi.dto.AuthRequest;
import com.cvi.dto.UserResponse;
import com.cvi.dto.profile.NaverProfile;
import com.cvi.dto.profile.UserInformation;
import com.cvi.exception.MappingFailureException;
import com.cvi.parser.AuthorizationManager;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;

@SpringBootTest
@TestPropertySource(properties = {"security.auth.naver.client-secret"})
@DisplayName("소셜로그인 비즈니스 흐름 테스트")
class AuthServiceTest {

    private static final String NAVER_PROFILE_RESPONSE = "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":{\"id\":\"NAVER_ID\",\"nickname\":\"yon\",\"profile_image\":\"naver.com/profile\"}}";
    private static final String SOCIAL_CODE = "SOCIAL_CODE";
    private static final String STATE = "STATE";
    private static final String NAVER_ID = "NAVER_ID";
    private static final String NAVER_PROFILE_URL = "naver.com/profile";

    @Autowired
    private AuthService authService;

    @MockBean
    private AuthorizationManager authorizationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PublicDataScheduler publicDataScheduler;

    private AuthRequest authRequest;
    private User user;
    private String token;
    private UserInformation userInfo;
    private UserResponse userResponse;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        authRequest = new AuthRequest(SocialProvider.NAVER, SOCIAL_CODE, STATE);
        user = User.builder()
                .id(1L)
                .nickname("test_user")
                .ageRange(AgeRange.TEENS)
                .socialId(NAVER_ID)
                .profileUrl(NAVER_PROFILE_URL)
                .socialProvider(SocialProvider.NAVER)
                .build();
        token = "naver_auth_token";

        NaverProfile naverProfile = objectMapper.readValue(NAVER_PROFILE_RESPONSE, NaverProfile.class);
        userInfo = UserInformation.of(naverProfile);

        userResponse = UserResponse.of(user, token);
    }

    @DisplayName("네이버 소셜 로그인 - 성공 - 이미 가입한 유저인 경우")
    @Test
    void authenticateWhenUserIsExists() {
        //given
        willReturn(Optional.of(user)).given(userRepository).findBySocialProviderAndSocialId(SocialProvider.NAVER, NAVER_ID);
        willReturn(token).given(jwtTokenProvider).createToken(user.getId());
        willReturn(userInfo).given(authorizationManager).requestUserInfo(authRequest.getProvider(), authRequest.getCode(), authRequest.getState());
        //when
        UserResponse expected = authService.authenticate(authRequest);
        //then
        assertThat(expected.getNickname()).isEqualTo(userResponse.getNickname());
    }

    @DisplayName("네이버 소셜 로그인 - 성공 - 신규 유저인 경우")
    @Test
    void authenticateWhenUserIsNotExists() {
        //given
        willReturn(Optional.of(user)).given(userRepository).findBySocialProviderAndSocialId(SocialProvider.NAVER, "NEW_ID");
        willReturn(token).given(jwtTokenProvider).createToken(user.getId());
        willReturn(userInfo).given(authorizationManager).requestUserInfo(authRequest.getProvider(), authRequest.getCode(), authRequest.getState());
        //when
        UserResponse expected = authService.authenticate(authRequest);
        //then
        assertThat(expected.getNickname()).isNull();
        assertThat(expected.getId()).isNull();
    }

    @DisplayName("네이버 소셜 로그인 - 실패 - 잘못된 CODE인 경우")
    @Test
    void authenticateFailureWhenNotValidCode() {
        //given
        AuthRequest invalidRequest = new AuthRequest(SocialProvider.NAVER, "INVALID_SOCIAL_CODE", STATE);

        willReturn(Optional.of(user)).given(userRepository).findBySocialProviderAndSocialId(SocialProvider.NAVER, NAVER_ID);
        willReturn(token).given(jwtTokenProvider).createToken(user.getId());
        willThrow(new MappingFailureException("토큰 정보를 불러오는 데 실패했습니다.")).given(authorizationManager).requestUserInfo(invalidRequest.getProvider(), invalidRequest.getCode(), invalidRequest.getState());
        //when
        //then
        assertThatThrownBy(() -> authService.authenticate(invalidRequest))
                .isExactlyInstanceOf(MappingFailureException.class);
    }

    @DisplayName("네이버 소셜 로그인 - 실패 - 잘못된 STATE인 경우")
    @Test
    void authenticateFailureWhenNotValidState() {
        //given
        AuthRequest invalidRequest = new AuthRequest(SocialProvider.NAVER, SOCIAL_CODE, "INVALID_STATE");

        willReturn(Optional.of(user)).given(userRepository).findBySocialProviderAndSocialId(SocialProvider.NAVER, NAVER_ID);
        willReturn(token).given(jwtTokenProvider).createToken(user.getId());
        willThrow(new MappingFailureException("토큰 정보를 불러오는 데 실패했습니다.")).given(authorizationManager).requestUserInfo(invalidRequest.getProvider(), invalidRequest.getCode(), invalidRequest.getState());
        //when
        //then
        assertThatThrownBy(() -> authService.authenticate(invalidRequest))
                .isExactlyInstanceOf(MappingFailureException.class);
    }
}
