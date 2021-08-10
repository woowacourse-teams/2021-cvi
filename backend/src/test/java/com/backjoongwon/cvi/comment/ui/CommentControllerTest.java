package com.backjoongwon.cvi.comment.ui;

import com.backjoongwon.cvi.comment.application.CommentService;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.post.ui.PreprocessPostControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("댓글 컨트롤러 Mock 테스트")
@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest extends PreprocessPostControllerTest {
    
    @MockBean
    private CommentService commentService;

    @DisplayName("댓글 등록 - 성공")
    @Test
    void createComment() throws Exception {
        //given
        CommentResponse expectedResponse = new CommentResponse(COMMENT_ID, userResponse, "좋은 정보 공유 감사해요 ㅎㅎㅎ", LocalDateTime.now());
        willReturn(expectedResponse).given(commentService).createComment(anyLong(), any(), any(CommentRequest.class));
        //when
        ResultActions response = 댓글_등록_요청(POST_ID, new CommentRequest("좋은 정보 공유 감사해요 ㅎㅎㅎ"), BEARER + ACCESS_TOKEN);
        //then
        댓글_등록_성공함(response, expectedResponse);
    }

    @DisplayName("댓글 등록 - 실패 - 비회원이 댓글을 작성할 때")
    @Test
    void createCommentFailureWhenWrongWriter() throws Exception {
        //given
        willThrow(new UnAuthorizedException("가입된 유저가 아닙니다.")).given(commentService).createComment(anyLong(), any(), any(CommentRequest.class));
        //when
        ResultActions response = 댓글_등록_요청(POST_ID, new CommentRequest("좋은 정보 공유 감사해요 ㅎㅎㅎ"), "null");
        //then
        댓글_등록_실패함(response);
    }

    @DisplayName("댓글 조회 - 성공")
    @Test
    void findCommentOfPost() throws Exception {
        //given
        willReturn(commentResponses).given(commentService).findCommentsByPostId(anyLong());
        //when
        ResultActions response = 댓글_조회_요청(POST_ID);
        //then
        댓글_조회_성공함(response);
    }

    @DisplayName("댓글 조회 - 실패 - 게시글이 없는 경우")
    @Test
    void findCommentOfPostFailure() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id 게시글이 존재하지 않습니다.")).given(commentService).findCommentsByPostId(anyLong());
        //when
        ResultActions response = 댓글_조회_요청(anyLong());
        //then
        댓글_조회_실패함(response);
    }

    @DisplayName("게시글 댓글 페이징 조회 - 성공")
    @Test
    void findCommentOfPostPaging() throws Exception {
        //given
        willReturn(commentResponses).given(commentService).findCommentsByPostId(anyLong(), anyInt(), anyInt());
        //when
        int offset = 1;
        int size = 2;
        ResultActions response = 댓글_조회_페이징_요청(POST_ID, offset, size);
        //then
        댓글_페이징_조회_성공함(response);
    }

    @DisplayName("게시글 댓글 페이징 조회 - 실패 - 게시글이 없는 경우")
    @Test
    void findCommentOfPostPagingFailure() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id 게시글이 존재하지 않습니다.")).given(commentService).findCommentsByPostId(anyLong(), anyInt(), anyInt());
        //when
        ResultActions response = 댓글_조회_페이징_요청(anyLong(), anyInt(), anyInt());
        //then
        댓글_페이징_조회_실패함(response);
    }

    @DisplayName("댓글 수정 - 성공")
    @Test
    void putComment() throws Exception {
        //given
        CommentRequest updateRequest = new CommentRequest("수정된 좋은 정보 공유 감사해요 ㅎㅎ");
        willDoNothing().given(commentService).updateComment(anyLong(), anyLong(), any(), any(CommentRequest.class));
        //when
        ResultActions response = 댓글_수정_요청(POST_ID, COMMENT_ID, updateRequest, BEARER + ACCESS_TOKEN);
        //then
        댓글_수정_성공함(response);
    }

    @DisplayName("댓글 수정 - 실패 - 작성자가 아닌 사용자가 수정 요청")
    @Test
    void putCommentFailureWhenWrongUser() throws Exception {
        //given
        CommentRequest updateRequest = new CommentRequest("수정된 좋은 정보 공유 감사해요 ㅎㅎ");
        willThrow(new UnAuthorizedException("댓글 작성자가 아닙니다.")).given(commentService).updateComment(anyLong(), anyLong(),
                any(), any(CommentRequest.class));
        //when
        ResultActions response = 댓글_수정_요청(POST_ID, COMMENT_ID, updateRequest, BEARER + "another_user_token");
        //then
        댓글_수정_실패함(response);
    }

    @DisplayName("댓글 삭제 - 성공")
    @Test
    void deleteComment() throws Exception {
        //given
        willDoNothing().given(commentService).deleteComment(anyLong(), anyLong(), any());
        //when
        ResultActions response = 댓글_삭제_요청(POST_ID, COMMENT_ID, BEARER + ACCESS_TOKEN);
        //then
        댓글_삭제_성공함(response);
    }

    @DisplayName("댓글 삭제 - 실패 - 작성자가 아닌 사용자가 삭제 요청")
    @Test
    void deleteCommentWhenWrongUser() throws Exception {
        //given
        willThrow(new UnAuthorizedException("댓글 작성자가 아닙니다.")).given(commentService).deleteComment(anyLong(), anyLong(), any());
        //when
        ResultActions response = 댓글_삭제_요청(POST_ID, COMMENT_ID, BEARER + "another_user_token");
        //then
        댓글_삭제_실패함(response);
    }

    private ResultActions 댓글_등록_요청(Long postId, CommentRequest request, String headerValue) throws Exception {
        return mockMvc.perform(post("/api/v1/posts/{postId}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
                .header(HttpHeaders.AUTHORIZATION, headerValue));
    }

    private void 댓글_등록_성공함(ResultActions response, CommentResponse expectedResponse) throws Exception {
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/comments/" + expectedResponse.getId()))
                .andDo(print())
                .andDo(toDocument("comment-create"));
    }

    private void 댓글_등록_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(toDocument("comment-create-failure"));
    }

    private ResultActions 댓글_조회_요청(Long postId) throws Exception {
        return mockMvc.perform(get("/api/v1/posts/{postId}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions 댓글_조회_페이징_요청(long postId, int offset, int size) throws Exception {
        return mockMvc.perform(get("/api/v1/posts/{postId}/comments/paging", postId)
                .queryParam("offset", String.valueOf(offset))
                .queryParam("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private void 댓글_조회_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("comment-find"));
    }

    private void 댓글_조회_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andDo(print())
                .andDo(toDocument("comment-find-failure"));
    }

    private void 댓글_페이징_조회_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("comment-find-paging"));
    }

    private void 댓글_페이징_조회_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andDo(print())
                .andDo(toDocument("comment-find-paging-failure"));
    }

    private ResultActions 댓글_수정_요청(Long postId, Long commentId, CommentRequest request, String accessToken) throws Exception {
        return mockMvc.perform(put("/api/v1/posts/{postId}/comments/{commentId}", postId, commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );
    }

    private void 댓글_수정_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("comment-update"));
    }

    private void 댓글_수정_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(toDocument("comment-update-failure"));
    }

    private ResultActions 댓글_삭제_요청(Long postId, Long commentId, String accessToken) throws Exception {
        return mockMvc.perform(delete("/api/v1/posts/{postId}/comments/{commentId}", postId, commentId)
                .header(HttpHeaders.AUTHORIZATION, accessToken));
    }

    private void 댓글_삭제_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("comment-delete"));
    }

    private void 댓글_삭제_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(toDocument("comment-delete-failure"));
    }
}
