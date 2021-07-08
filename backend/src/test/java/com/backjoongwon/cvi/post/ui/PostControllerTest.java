package com.backjoongwon.cvi.post.ui;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.post.application.PostService;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.dto.UserResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest extends ApiDocument {

    private static final Long USER_ID = 1L;
    private static final Long POST_ID = 100L;

    @MockBean
    private PostService postService;

    @DisplayName("글 등록 - 성공")
    @Test
    void createPost() throws Exception {
        //given
        PostRequest request = new PostRequest("글 내용", "화이자");
        PostResponse expectedResponse = new PostResponse(POST_ID);
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

    @DisplayName("글 조회 - 성공")
    @Test
    void find() throws Exception {
        //given
        UserResponse expectedUserResponse = new UserResponse(USER_ID, "인비", 10, true);
        PostResponse expectedPostResponse = new PostResponse(POST_ID, expectedUserResponse, "글 내용", 55, "화이자", LocalDateTime.now());

        given(postService.find(any(Long.class))).willReturn(expectedPostResponse);
        //when
        ResultActions response = 글_조회_요청(POST_ID);
        //then
        글_조회_성공함(response, expectedPostResponse);
    }

    private ResultActions 글_조회_요청(Long postId) throws Exception {
        return mockMvc.perform(get("/api/v1/posts/{postId}", postId)
            .contentType(MediaType.APPLICATION_JSON));
    }

    private void 글_조회_성공함(ResultActions response, PostResponse expectedResponse) throws Exception {
        response.andExpect(status().isOk())
            .andExpect(content().json(toJson(expectedResponse)))
            .andDo(print())
            .andDo(toDocument("post-find"));
    }
}