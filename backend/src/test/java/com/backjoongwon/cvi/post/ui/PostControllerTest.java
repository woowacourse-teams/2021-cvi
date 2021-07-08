package com.backjoongwon.cvi.post.ui;


import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.post.application.PostService;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest extends ApiDocument {

    private static final Long USER_ID = 1L;
    private static final Long POST_ID = 100L;

    private PostRequest request;
    private PostResponse expectedResponse;
    @MockBean
    private PostService postService;

    @BeforeEach
    void init() {
        request = new PostRequest("글 내용", "화이자");
        expectedResponse = new PostResponse(POST_ID);
    }

    @DisplayName("글 등록 - 성공")
    @Test
    void createPost() throws Exception {
        //given
        given(postService.create(any(Long.class), any(PostRequest.class))).willReturn(expectedResponse);
        //when
        ResultActions response = 글_등록_요청(USER_ID, request);
        //then
        글_등록_성공함(response, expectedResponse);
    }

    private ResultActions 글_등록_요청(Long userId, PostRequest request) throws Exception {
        return mockMvc.perform(post("/api/v1/posts/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)));
    }

    private void 글_등록_성공함(ResultActions response, PostResponse expectedResponse) throws Exception {
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/posts/" + expectedResponse.getId()))
                .andDo(print())
                .andDo(toDocument("post-create"));
    }
}