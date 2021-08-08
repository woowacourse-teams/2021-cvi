package com.backjoongwon.cvi.post.ui;

import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.post.dto.LikeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("게시글 컨트롤러 Mock 테스트- 좋아요")
public class PostLikeControllerTest extends PreprocessPostControllerTest {

    @DisplayName("게시글 좋아요 생성 - 성공")
    @Test
    void createLike() throws Exception {
        //given
        LikeResponse likeResponse = LikeResponse.from(LIKE_ID);
        willReturn(likeResponse).given(postService).createLike(any(Long.class), any());
        //when
        ResultActions actualResponse = 글_좋아요_생성_요청(postResponse.getId());
        //then
        글_좋아요_생성_성공(actualResponse, LIKE_ID);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 게시글이 없는 경우")
    @Test
    void createLikeFailureWhenPostNotExists() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id의 게시글이 존재하지 않습니다.")).given(postService).createLike(any(Long.class), any());
        //when
        ResultActions response = 글_좋아요_생성_요청(postResponse.getId());
        //then
        글_좋아요_생성_실패(response);
    }

    @DisplayName("게시글 좋아요 삭제 - 성공")
    @Test
    void deleteLike() throws Exception {
        //given
        willDoNothing().given(postService).delete(any(Long.class), any());
        //when
        ResultActions response = 글_좋아요_삭제_요청();
        //then
        글_좋아요_삭제_성공함(response);
    }

    @DisplayName("게시글 좋아요 삭제 - 실패 - 토큰이 유효하지 않은 경우")
    @Test
    void deleteLikeFailureWhenNotTokenValid() throws Exception {
        //given
        willThrow(new UnAuthorizedException("유효하지 않은 토큰입니다."))
                .given(postService).deleteLike(any(Long.class), any());
        //when
        ResultActions response = 글_좋아요_삭제_요청();
        //then
        글_좋아요_삭제_실패함(response);
    }

    private ResultActions 글_좋아요_생성_요청(Long postId) throws Exception {
        return mockMvc.perform(post("/api/v1/posts/{postId}/likes", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 글_좋아요_생성_성공(ResultActions actualResponse, Long likeId) throws Exception {
        actualResponse.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/likes/" + likeId))
                .andDo(print())
                .andDo(toDocument("like-create"));
    }

    private void 글_좋아요_생성_실패(ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andDo(print())
                .andDo(toDocument("like-create-failure"));
    }

    private ResultActions 글_좋아요_삭제_요청() throws Exception {
        return mockMvc.perform(delete("/api/v1/posts/{postId}/likes", POST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 글_좋아요_삭제_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("like-delete"));
    }

    private void 글_좋아요_삭제_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(toDocument("like-delete-failure"));
    }
}
