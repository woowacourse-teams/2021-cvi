package com.backjoongwon.cvi.auth.ui;

import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.auth.application.AuthService;
import com.backjoongwon.cvi.auth.dto.AuthRequest;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.SocialProvider;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("OAuth 컨트롤러 테스트")
@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest extends ApiDocument {

    private static final String ACCESS_TOKEN = "{ACCESS TOKEN}";
    private static final String BEARER = "Bearer ";
    private static final String NICKNAME = "인비";
    private static final String PROFILE_URL = "kakao.com/profile";
    private static final SocialProvider SOCIAL_PROVIDER = SocialProvider.KAKAO;
    private static final String SOCIAL_ID = "KAKAO_ID";

    @MockBean
    private AuthService authService;

    private User user;
    private UserResponse userResponse;
    private AuthRequest authRequest;

    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .id(1L)
                .nickname(NICKNAME)
                .ageRange(AgeRange.TWENTIES)
                .socialProvider(SOCIAL_PROVIDER)
                .socialId(SOCIAL_ID)
                .profileUrl(PROFILE_URL)
                .build();
        authRequest = new AuthRequest(SocialProvider.KAKAO, "sadnkfsadnfk", null);
        userResponse = UserResponse.of(user, ACCESS_TOKEN);
    }

    @DisplayName("OAuth 로그인 - 성공")
    @Test
    void authenticate() throws Exception {
        //given
        willReturn(userResponse).given(authService).authenticate(any(AuthRequest.class));
        //when
        ResultActions response = 사용자_OAuth_요청(authRequest);
        //then
        사용자_OAuth_성공함(response, userResponse);
    }

    @DisplayName("OAuth 로그인 - 실패")
    @Test
    void authenticateFailure() throws Exception {
        //given
        willThrow(new UnAuthorizedException("OAuth 인증에 실패하였습니다.")).given(authService).authenticate(any(AuthRequest.class));
        //when
        ResultActions response = 사용자_OAuth_요청(authRequest);
        //then
        사용자_OAuth_실패함(response);
    }

    private ResultActions 사용자_OAuth_요청(AuthRequest authRequest) throws Exception {
        return mockMvc.perform(post("/api/v1/users/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(authRequest)));
    }

    private void 사용자_OAuth_성공함(ResultActions response, UserResponse userResponse) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(userResponse)))
                .andExpect(header().string("Authorization", BEARER + ACCESS_TOKEN))
                .andDo(print())
                .andDo(toDocument("user-auth"));
    }

    private void 사용자_OAuth_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(toDocument("user-auth-failure"));
    }
}
