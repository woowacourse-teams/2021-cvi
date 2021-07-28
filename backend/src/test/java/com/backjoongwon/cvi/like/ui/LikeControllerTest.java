package com.backjoongwon.cvi.like.ui;


import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.like.application.LikeService;
import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import com.backjoongwon.cvi.user.domain.RequestUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("게시글 좋아요 컨트롤러 Mock 테스트")
@WebMvcTest(controllers = LikeController.class)
public class LikeControllerTest extends ApiDocument {

    private static final String ACCESS_TOKEN = "{ACCESS TOKEN}";
    private static final String BEARER = "Bearer ";
    private static final Long LIKE_ID = 1L;

    @MockBean
    private LikeService likeService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("게시글 좋아요 삭제 - 성공")
    @Test
    void deleteLike() throws Exception {
        //given
        willDoNothing().given(likeService).delete(any(Long.class), any(RequestUser.class));
        //when
        ResultActions response = 글_좋아요_삭제_요청(LIKE_ID);
        //then
        글_좋아요_삭제_성공함(response);
    }

    @DisplayName("게시글 좋아요 삭제 - 실패 - 토큰이 유효하지 않은 경우")
    @Test
    void deletePostFailure() throws Exception {
        //given
        willThrow(new UnAuthorizedException("유효하지 않은 토큰입니다."))
                .given(likeService).delete(any(Long.class), any(RequestUser.class));
        //when
        ResultActions response = 글_좋아요_삭제_요청(LIKE_ID);
        //then
        글_좋아요_삭제_실패함(response);
    }

    private ResultActions 글_좋아요_삭제_요청(Long id) throws Exception {
        return mockMvc.perform(delete("/api/v1/likes/{id}", id)
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
