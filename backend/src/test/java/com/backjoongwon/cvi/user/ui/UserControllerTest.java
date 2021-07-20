package com.backjoongwon.cvi.user.ui;


import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.common.exception.InvalidInputException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.user.application.UserService;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.SocialProvider;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("사용자 컨트롤러 Mock 테스트")
@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends ApiDocument {

    private static final String ACCESS_TOKEN = "{ACCESS TOKEN}";
    private static final String BEARER = "Bearer ";
    private static final String NICKNAME = "인비";
    private static final AgeRange AGE_RANGE = AgeRange.TWENTIES;
    private static final String PROFILE_URL = "kakao.com/profile";
    private static final SocialProvider SOCIAL_PROVIDER = SocialProvider.KAKAO;
    private static final String SOCIAL_ID = "KAKAO_ID";

    @MockBean
    private UserService userService;

    private User user;
    private UserRequest userRequest;
    private UserResponse userResponse;

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
        userRequest = new UserRequest(NICKNAME, AGE_RANGE, SOCIAL_PROVIDER, SOCIAL_ID, PROFILE_URL);
        userResponse = UserResponse.of(user, ACCESS_TOKEN);
        given(userService.findUserByAccessToken(ACCESS_TOKEN))
                .willReturn(user);
    }

    @DisplayName("사용자 가입 - 성공")
    @Test
    void signup() throws Exception {
        //given
        willReturn(userResponse).given(userService).signup(any(UserRequest.class));
        //when
        ResultActions response = 사용자_회원가입_요청(userRequest);
        //then
        사용자_회원가입_성공함(response, userResponse);
    }

    @DisplayName("사용자 가입 - 실패")
    @Test
    void signupFailure() throws Exception {
        //given
        willThrow(new InvalidInputException("중복된 닉네임이 존재합니다.")).given(userService).signup(any(UserRequest.class));
        //when
        ResultActions response = 사용자_회원가입_요청(userRequest);
        //then
        사용자_회원가입_실패함(response);
    }

    @DisplayName("사용자 내 정보 조회 - 성공")
    @Test
    void findMe() throws Exception {
        //given
        willReturn(userResponse).given(userService).findMeById(any(Long.class));
        //when
        ResultActions response = 사용자_내_정보_조회_요청();
        //then
        사용자_내_정보_조회_성공함(response, userResponse);
    }

    @DisplayName("사용자 내 정보 조회 - 실패")
    @Test
    void findMeFailure() throws Exception {
        //given
        willThrow(new UnAuthorizedException("존재하지 않는 사용자입니다.")).given(userService).findMeById(any(Long.class));
        //when
        ResultActions response = 사용자_내_정보_조회_요청();
        //then
        사용자_내_정보_조회_실패함(response);
    }

    @DisplayName("사용자 정보 조회 - 성공")
    @Test
    void find() throws Exception {
        //given
        User otherUser = User.builder()
                .id(1L)
                .nickname(NICKNAME)
                .ageRange(AgeRange.TWENTIES)
                .socialProvider(SOCIAL_PROVIDER)
                .profileUrl(PROFILE_URL)
                .build();
        UserResponse userResponseWithoutPrivacy = UserResponse.of(otherUser, null);
        willReturn(userResponseWithoutPrivacy).given(userService).findById(any(Long.class));
        //when
        ResultActions response = 사용자_조회_요청(userResponseWithoutPrivacy.getId());
        //then
        사용자_조회_성공함(response, userResponseWithoutPrivacy);
    }

    @DisplayName("사용자 정보 조회 - 실패")
    @Test
    void findFailure() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id의 사용자가 존재하지 않습니다.")).given(userService).findById(any(Long.class));
        //when
        ResultActions response = 사용자_조회_요청(userResponse.getId());
        //then
        사용자_조회_실패함(response);
    }

    @DisplayName("사용자 업데이트 -  성공")
    @Test
    void update() throws Exception {
        //given
        UserRequest updateRequest = new UserRequest(NICKNAME, AGE_RANGE, null, null, PROFILE_URL);
        willDoNothing().given(userService).update(any(Long.class), any(UserRequest.class));
        //when
        ResultActions response = 사용자_업데이트_요청(updateRequest);
        //then
        사용자_업데이트_성공(response);
    }

    @DisplayName("사용자 업데이트 -  실패")
    @Test
    void updateFailure() throws Exception {
        //given
        UserRequest updateRequest = new UserRequest(NICKNAME, AGE_RANGE, null, null, PROFILE_URL);
        willThrow(new InvalidInputException("중복된 닉네임이 존재합니다.")).given(userService).update(any(Long.class),
                any(UserRequest.class));
        //when
        ResultActions response = 사용자_업데이트_요청(updateRequest);
        //then
        사용자_업데이트_실패함(response);
    }

    @DisplayName("사용자 삭제 -  성공")
    @Test
    void deleteUser() throws Exception {
        //given
        willDoNothing().given(userService).delete(any(Long.class));
        //when
        ResultActions response = 사용자_삭제_요청();
        //then
        사용자_삭제_성공(response);
    }

    @DisplayName("사용자 삭제 -  실패")
    @Test
    void deleteFailure() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id의 사용자가 존재하지 않습니다.")).given(userService).delete(any(Long.class));
        //when
        ResultActions response = 사용자_삭제_요청();
        //then
        사용자_삭제_실패(response);
    }

    private ResultActions 사용자_회원가입_요청(UserRequest request) throws Exception {
        return mockMvc.perform(post("/api/v1/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));
    }

    private void 사용자_회원가입_성공함(ResultActions response, UserResponse userResponse) throws Exception {
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/users/" + userResponse.getId()))
                .andExpect(content().json(toJson(userResponse)))
                .andDo(print())
                .andDo(toDocument("user-signup"));
    }

    private void 사용자_회원가입_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(toDocument("user-signup-failure"));
    }

    private ResultActions 사용자_로그인_요청() throws Exception {
        return mockMvc.perform(post("/api/v1/users/signin")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 사용자_로그인_성공함(ResultActions response, UserResponse signinResponse) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(signinResponse)))
                .andExpect(header().string("Authorization", BEARER + ACCESS_TOKEN))
                .andDo(print())
                .andDo(toDocument("user-signin"));
    }

    private void 사용자_로그인_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(toDocument("user-signin-failure"));
    }

    private ResultActions 사용자_내_정보_조회_요청() throws Exception {
        return mockMvc.perform(get("/api/v1/users/me")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 사용자_내_정보_조회_성공함(ResultActions response, UserResponse userResponse) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(userResponse)))
                .andDo(print())
                .andDo(toDocument("user-find-me"));
    }

    private void 사용자_내_정보_조회_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(toDocument("user-find-me-failure"));
    }

    private ResultActions 사용자_조회_요청(Long id) throws Exception {
        return mockMvc.perform(get("/api/v1/users/" + id)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private void 사용자_조회_성공함(ResultActions response, UserResponse otherUserResponse) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(otherUserResponse)))
                .andDo(print())
                .andDo(toDocument("user-find"));
    }

    private void 사용자_조회_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andDo(print())
                .andDo(toDocument("user-find-failure"));
    }


    private ResultActions 사용자_업데이트_요청(UserRequest userRequest) throws Exception {
        return mockMvc.perform(put("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(userRequest))
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 사용자_업데이트_성공(ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("user-update"));
    }

    private void 사용자_업데이트_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(toDocument("user-update-failure"));
    }

    private ResultActions 사용자_삭제_요청() throws Exception {
        return mockMvc.perform(delete("/api/v1/users")
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 사용자_삭제_성공(ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("user-delete"));
    }

    private void 사용자_삭제_실패(ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andDo(print())
                .andDo(toDocument("user-delete-failure"));
    }
}