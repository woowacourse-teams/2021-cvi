package com.backjoongwon.cvi.user.ui;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.user.application.UserService;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends ApiDocument {

    @MockBean
    private UserService userService;
    private UserRequest request;
    private UserResponse response;

    @BeforeEach
    void init() {
        request = new UserRequest("라이언", 20);
        response = new UserResponse(1L, "라이언", 20);
    }

    @DisplayName("유저 가입 - 성공")
    @Test
    void signup() throws Exception {
        //given
        given(userService.signup(any(UserRequest.class))).willReturn(response);
        //when
        ResultActions response = 사용자_회원가입_요청(request);
        //then
        사용자_회원가입_성공함(response, this.response);
    }

    private void 사용자_회원가입_성공함(ResultActions response, UserResponse userResponse) throws Exception {
        response.andExpect(status().isCreated())
            .andExpect(header().string("Location", "/api/v1/users/" + 1))
            .andExpect(content().json(toJson(userResponse)))
            .andDo(print())
            .andDo(toDocument("user-signup"));
    }

    private ResultActions 사용자_회원가입_요청(UserRequest request) throws Exception {
        return mockMvc.perform(post("/api/v1/users/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)));
    }
}