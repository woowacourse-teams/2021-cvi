package com.backjoongwon.cvi.user.ui;


import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.common.exception.InvalidInputException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.user.application.UserService;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.dto.SigninResponse;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    private static final String ACCESS_TOKEN = "asdf";

    @MockBean
    private UserService userService;

    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void init() {
        userRequest = new UserRequest("라이언", AgeRange.TWENTIES);
        userResponse = new UserResponse(1L, "라이언", AgeRange.TWENTIES, false);
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

    @DisplayName("사용자 로그인 - 성공")
    @Test
    void login() throws Exception {
        //given
        UserRequest userRequest = new UserRequest("인비", AgeRange.TEENS);
        UserResponse userResponse = new UserResponse(1L, userRequest.getNickname(), userRequest.getAgeRange(), false);
        SigninResponse signinResponse = new SigninResponse(ACCESS_TOKEN, userResponse);
        willReturn(signinResponse).given(userService).signin(any(UserRequest.class));
        //when
        ResultActions response = 사용자_로그인_요청(userRequest);
        //then
        사용자_로그인_성공함(response, signinResponse);
    }

    @DisplayName("사용자 로그인 - 실패 - 존재하지 않는 닉네임")
    @Test
    void loginFailureWhenNicknameNotExists() throws Exception {
        //given
        UserRequest userRequest = new UserRequest("검프", AgeRange.TEENS);
        //when
        willThrow(new UnAuthorizedException("해당 id의 사용자가 존재하지 않습니다.")).given(userService).signin(any(UserRequest.class));
        ResultActions response = 사용자_로그인_요청(userRequest);
        //then
        사용자_로그인_실패함(response);
    }

    @DisplayName("사용자 정보 조회 - 성공")
    @Test
    void find() throws Exception {
        //given
        willReturn(userResponse).given(userService).findById(1L);
        //when
        ResultActions response = 사용자_조회_요청(userRequest);
        //then
        사용자_조회_성공함(response);
    }

    @DisplayName("사용자 정보 조회 - 실패")
    @Test
    void findFailure() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id의 사용자가 존재하지 않습니다.")).given(userService).findById(1L);
        //when
        ResultActions response = 사용자_조회_요청(userRequest);
        //then
        사용자_조회_실패함(response);
    }

    @DisplayName("사용자 업데이트 -  성공")
    @Test
    void update() throws Exception {
        //given
        willDoNothing().given(userService).update(any(Long.class), any(UserRequest.class));
        //when
        ResultActions response = 사용자_업데이트_요청(userRequest);
        //then
        사용자_업데이트_성공(response);
    }

    @DisplayName("사용자 업데이트 -  실패")
    @Test
    void updateFailure() throws Exception {
        //given
        willThrow(new InvalidInputException("해당 id의 사용자가 존재하지 않습니다.")).given(userService).update(any(Long.class),
                any(UserRequest.class));
        //when
        ResultActions response = 사용자_업데이트_요청(userRequest);
        //then
        사용자_업데이트_실패함(response);
    }

    @DisplayName("사용자 삭제 -  성공")
    @Test
    void deleteUser() throws Exception {
        //given
        willDoNothing().given(userService).delete(any(Long.class));
        //when
        ResultActions response = 사용자_삭제_요청(1L);
        //then
        사용자_삭제_성공(response);
    }

    @DisplayName("사용자 삭제 -  실패")
    @Test
    void deleteFailure() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id의 사용자가 존재하지 않습니다.")).given(userService).delete(any(Long.class));
        //when
        ResultActions response = 사용자_삭제_요청(2L);
        //then
        사용자_삭제_실패(response);
    }

    private ResultActions 사용자_회원가입_요청(UserRequest request) throws Exception {
        return mockMvc.perform(post("/api/v1/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .param("nickname", "인비")
                .content(toJson(request)));
    }

    private void 사용자_회원가입_성공함(ResultActions response, UserResponse userResponse) throws Exception {
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/users/" + 1))
                .andExpect(content().json(toJson(userResponse)))
                .andDo(print())
                .andDo(toDocument("user-signup"));
    }

    private void 사용자_회원가입_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(toDocument("user-signup-failure"));
    }

    private ResultActions 사용자_로그인_요청(UserRequest userRequest) throws Exception {
        return mockMvc.perform(post("/api/v1/users/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(userRequest)));
    }

    private void 사용자_로그인_성공함(ResultActions response, SigninResponse signinResponse) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(signinResponse)))
                .andExpect(header().string("Authorization", "Bearer " + ACCESS_TOKEN))
                .andDo(print())
                .andDo(toDocument("user-signin"));
    }

    private void 사용자_로그인_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(toDocument("user-signin-failure"));
    }

    private ResultActions 사용자_조회_요청(UserRequest request) throws Exception {
        return mockMvc.perform(get("/api/v1/users/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));
    }

    private void 사용자_조회_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(userResponse)))
                .andDo(print())
                .andDo(toDocument("user-find"));
    }

    private void 사용자_조회_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andDo(print())
                .andDo(toDocument("user-find-failure"));
    }


    private ResultActions 사용자_업데이트_요청(UserRequest userRequest) throws Exception {
        return mockMvc.perform(put("/api/v1/users/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(userRequest)));
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

    private ResultActions 사용자_삭제_요청(Long id) throws Exception {
        return mockMvc.perform(delete("/api/v1/users/" + id));
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