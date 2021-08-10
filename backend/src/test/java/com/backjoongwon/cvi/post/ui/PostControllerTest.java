package com.backjoongwon.cvi.post.ui;

import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.post.domain.Sort;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("게시글 컨트롤러 Mock 테스트 - 게시글")
class PostControllerTest extends PreprocessPostControllerTest {

    @DisplayName("게시글 등록 - 성공")
    @Test
    void createPost() throws Exception {
        //given
        PostResponse expectedResponse = new PostResponse(POST_ID, userResponse, request.getContent(), 0, 0, 0, false, request.getVaccinationType(), LocalDateTime.now());
        willReturn(expectedResponse).given(postService).create(any(), any(PostRequest.class));
        //when
        ResultActions response = 글_등록_요청(request);
        //then
        글_등록_성공함(response, expectedResponse);
    }

    @DisplayName("게시글 등록 - 실패")
    @Test
    void createPostFailure() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id의 사용자가 존재하지 않습니다.")).given(postService).create(any(), any(PostRequest.class));
        //when
        ResultActions response = 글_등록_요청(request);
        //then
        글_등록_실패함(response);
    }

    @DisplayName("게시글 단일 조회 - 성공")
    @Test
    void find() throws Exception {
        //given
        PostResponse expectedPostResponse = new PostResponse(POST_ID, userResponse, "글 내용", 1, 0, 2, false, VaccinationType.PFIZER, LocalDateTime.now());
        willReturn(expectedPostResponse).given(postService).findById(any(Long.class), any());
        //when
        ResultActions response = 글_단일_조회_요청(POST_ID);
        //then
        글_단일_조회_성공함(response);
    }

    @DisplayName("게시글 단일 조회 - 실패")
    @Test
    void findFailure() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id의 게시글이 존재하지 않습니다.")).given(postService).findById(any(Long.class), any());
        //when
        ResultActions response = 글_단일_조회_요청(POST_ID);
        //then
        글_단일_조회_실패함(response);
    }

    @DisplayName("게시글 전체 조회 - 성공")
    @Test
    void findAll() throws Exception {
        //given
        UserResponse anotherUserResponse = UserResponse.of(user, null);
        List<PostResponse> postResponse = new LinkedList<>(Arrays.asList(
                new PostResponse(POST_ID + 1, anotherUserResponse, "글 내용2", 12, 0, 3, false, VaccinationType.MODERNA, LocalDateTime.now()),
                new PostResponse(POST_ID, userResponse, "글 내용1", 55, 5, 13, true, VaccinationType.PFIZER, LocalDateTime.now().minusDays(1L))
        ));
        willReturn(postResponse).given(postService).findByVaccineType(any(VaccinationType.class), any());
        //when
        ResultActions response = 글_전체_조회_요청();
        //then
        글_전체_조회_성공함(response, postResponse);
    }

    @DisplayName("게시글 전체 조회 - 성공 - 게시글이 하나도 없는 경우")
    @Test
    void findAllWhenPostsIsEmpty() throws Exception {
        //given
        List<PostResponse> postResponse = Collections.emptyList();
        willReturn(postResponse).given(postService).findByVaccineType(any(VaccinationType.class), any());
        //when
        ResultActions response = 글_전체_조회_요청();
        //then
        글_전체_조회_성공함_게시글없음(response, postResponse);
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void updatePost() throws Exception {
        //given
        willDoNothing().given(postService).update(any(Long.class), any(), any(PostRequest.class));
        //when
        ResultActions response = 글_수정_요청(POST_ID, request);
        //then
        글_수정_성공함(response);
    }

    @DisplayName("게시글 수정 - 실패")
    @Test
    void updatePostFailure() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id의 게시글이 존재하지 않습니다.")).given(postService).update(any(Long.class), any(), any(PostRequest.class));
        //when
        ResultActions response = 글_수정_요청(POST_ID, request);
        //then
        글_수정_실패함(response);
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void deletePost() throws Exception {
        //given
        willDoNothing().given(postService).delete(any(Long.class), any());
        //when
        ResultActions response = 글_삭제_요청(POST_ID);
        //then
        글_삭제_성공함(response);
    }

    @DisplayName("게시글 삭제 - 실패")
    @Test
    void deletePostFailure() throws Exception {
        //given
        willThrow(new NotFoundException("해당 id의 게시글이 존재하지 않습니다.")).given(postService).delete(any(Long.class), any());
        //when
        ResultActions response = 글_삭제_요청(POST_ID);
        //then
        글_삭제_실패함(response);
    }

    @DisplayName("게시글 타입별 조회 - 성공")
    @Test
    void findByVaccineType() throws Exception {
        //given
        List<PostResponse> postResponse = new LinkedList<>(Arrays.asList(
                new PostResponse(3L, userResponse, "이건 내용입니다.", 100, 10, 4, true, VaccinationType.PFIZER, LocalDateTime.now()),
                new PostResponse(2L, userResponse, "이건 내용입니다.2", 200, 20, 6, false, VaccinationType.PFIZER, LocalDateTime.now()),
                new PostResponse(1L, userResponse, "이건 내용입니다.3", 300, 30, 10, true, VaccinationType.PFIZER, LocalDateTime.now())
        ));
        willReturn(postResponse).given(postService).findByVaccineType(any(VaccinationType.class), any());
        //when
        ResultActions response = 글_타입별_조회_요청(VaccinationType.PFIZER);
        //then
        글_타입별_조회_요청_성공함(response);
    }

    @DisplayName("게시글 타입별 조회 - 성공 - 게시글이 하나도 없는 경우")
    @Test
    void findByVaccineTypeWhenPostsIsEmpty() throws Exception {
        //given
        List<PostResponse> postResponse = Collections.emptyList();
        willReturn(postResponse).given(postService).findByVaccineType(any(VaccinationType.class), any());
        //when
        ResultActions response = 글_타입별_조회_요청(VaccinationType.PFIZER);
        //then
        글_타입별_조회_성공함_게시글없음(response, postResponse);
    }

    @DisplayName("게시글 타입별 조회 페이징 - 성공")
    @Test
    void findByVaccineTypePaging() throws Exception {
        //given
        List<PostResponse> postResponses = new LinkedList<>(Arrays.asList(
                new PostResponse(38L, userResponse, "이건 내용입니다.", 100, 10, 3, true, VaccinationType.PFIZER, LocalDateTime.now()),
                new PostResponse(37L, userResponse, "이건 내용입니다.2", 200, 20, 4, false, VaccinationType.PFIZER, LocalDateTime.now().minusDays(1)),
                new PostResponse(36L, userResponse, "이건 내용입니다.3", 300, 30, 2, true, VaccinationType.PFIZER, LocalDateTime.now().minusDays(2))
        ));
        willReturn(postResponses).given(postService).findByVaccineType(any(VaccinationType.class), anyInt(), anyInt(), any(), anyInt(), any());
        //when
        ResultActions response = 글_타입별_페이징_조회_요청(VaccinationType.PFIZER, 0, 3);
        //then
        글_타입별_페이징_조회_요청_성공함(response);
    }

    @DisplayName("게시글 타입별 페이징 조회 - 성공 - 게시글이 하나도 없는 경우")
    @Test
    void findByVaccineTypePagingWhenPostsIsEmpty() throws Exception {
        //given
        List<PostResponse> postResponse = Collections.emptyList();
        willReturn(postResponse).given(postService).findByVaccineType(any(VaccinationType.class), anyInt(), anyInt(), any(), anyInt(), any());
        //when
        ResultActions response = 글_타입별_페이징_조회_요청(VaccinationType.PFIZER, 0, 3);
        //then
        글_타입별_페이징_조회_요청_성공함_게시글없음(response, postResponse);
    }

    @DisplayName("게시글 타입별 조회 좋아요 오름차순 정렬 - 성공")
    @Test
    void findByVaccineTypeSorting() throws Exception {
        //given
        List<PostResponse> postResponses = new LinkedList<>(Arrays.asList(
                new PostResponse(1L, userResponse, "이건 내용입니다.", 100, 10, 5, true, VaccinationType.PFIZER, LocalDateTime.now()),
                new PostResponse(37L, userResponse, "이건 내용입니다.2", 200, 20, 8, false, VaccinationType.PFIZER, LocalDateTime.now().minusDays(1)),
                new PostResponse(146L, userResponse, "이건 내용입니다.3", 300, 30, 1, true, VaccinationType.PFIZER, LocalDateTime.now().minusDays(2))
        ));
        willReturn(postResponses).given(postService).findByVaccineType(any(VaccinationType.class), anyInt(), anyInt(), any(), anyInt(), any());
        //when
        ResultActions response = 글_타입별_정렬_조회_요청(VaccinationType.PFIZER, Sort.LIKE_COUNT_ASC);
        //then
        글_타입별_정렬_조회_요청_성공함(response);
    }

    @DisplayName("게시글 타입별 조회 시간 필터링 - 성공")
    @Test
    void findByVaccineTypeHourFiltering() throws Exception {
        //given
        List<PostResponse> postResponses = new LinkedList<>(Arrays.asList(
                new PostResponse(1L, userResponse, "이건 내용입니다.", 100, 10, 3, true, VaccinationType.PFIZER, LocalDateTime.now()),
                new PostResponse(37L, userResponse, "이건 내용입니다.2", 200, 20, 6, false, VaccinationType.PFIZER, LocalDateTime.now().minusHours(3)),
                new PostResponse(146L, userResponse, "이건 내용입니다.3", 300, 30, 7, true, VaccinationType.PFIZER, LocalDateTime.now().minusHours(5))
        ));
        willReturn(postResponses).given(postService).findByVaccineType(any(VaccinationType.class), anyInt(), anyInt(), any(), anyInt(), any());
        //when
        ResultActions response = 글_타입별_시간필터링_조회_요청(VaccinationType.PFIZER, 24);
        //then
        글_타입별_시간필터링_조회_요청_성공함(response);
    }

    private ResultActions 글_등록_요청(PostRequest request) throws Exception {
        return mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 글_등록_성공함(ResultActions response, PostResponse postResponse) throws Exception {
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/posts/" + postResponse.getId()))
                .andDo(print())
                .andDo(toDocument("post-create"));
    }

    private void 글_등록_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andDo(print())
                .andDo(toDocument("post-create-failure"));
    }

    private ResultActions 글_단일_조회_요청(Long id) throws Exception {
        return mockMvc.perform(get("/api/v1/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 글_단일_조회_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("post-find"));
    }

    private void 글_단일_조회_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andDo(print())
                .andDo(toDocument("post-find-failure"));
    }

    private ResultActions 글_전체_조회_요청() throws Exception {
        return mockMvc.perform(get("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 글_전체_조회_성공함(ResultActions response, List<PostResponse> postResponse) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(postResponse)))
                .andDo(print())
                .andDo(toDocument("post-findAll"));
    }

    private void 글_전체_조회_성공함_게시글없음(ResultActions response, List<PostResponse> postResponse) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(postResponse)))
                .andDo(print())
                .andDo(toDocument("post-findAll-when-empty"));
    }

    private ResultActions 글_수정_요청(Long id, PostRequest request) throws Exception {
        return mockMvc.perform(put("/api/v1/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 글_수정_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("post-update"));
    }

    private void 글_수정_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andDo(print())
                .andDo(toDocument("post-update-failure"));
    }

    private ResultActions 글_삭제_요청(Long id) throws Exception {
        return mockMvc.perform(delete("/api/v1/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 글_삭제_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("post-delete"));
    }

    private void 글_삭제_실패함(ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andDo(print())
                .andDo(toDocument("post-delete-failure"));
    }

    private ResultActions 글_타입별_조회_요청(VaccinationType vaccinationType) throws Exception {
        return mockMvc.perform(get("/api/v1/posts")
                .queryParam("vaccinationType", vaccinationType.name())
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 글_타입별_조회_요청_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("post-findByVaccinationType"));
    }

    private void 글_타입별_조회_성공함_게시글없음(ResultActions response, List<PostResponse> postResponse) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(postResponse)))
                .andDo(print())
                .andDo(toDocument("post-findByVaccinationType-when-empty"));
    }

    private ResultActions 글_타입별_페이징_조회_요청(VaccinationType vaccinationType, int offset, int size) throws Exception {
        return mockMvc.perform(get("/api/v1/posts/paging")
                .queryParam("vaccinationType", vaccinationType.name())
                .queryParam("offset", String.valueOf(offset))
                .queryParam("size", String.valueOf(size))
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private ResultActions 글_타입별_정렬_조회_요청(VaccinationType vaccinationType, Sort sort) throws Exception {
        return mockMvc.perform(get("/api/v1/posts/paging")
                .queryParam("vaccinationType", vaccinationType.name())
                .queryParam("sort", sort.name())
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private ResultActions 글_타입별_시간필터링_조회_요청(VaccinationType vaccinationType, int hour) throws Exception {
        return mockMvc.perform(get("/api/v1/posts/paging")
                .queryParam("vaccinationType", vaccinationType.name())
                .queryParam("fromHoursBefore", String.valueOf(hour))
                .header(HttpHeaders.AUTHORIZATION, BEARER + ACCESS_TOKEN));
    }

    private void 글_타입별_페이징_조회_요청_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("post-findByVaccinationType-paging"));
    }

    private void 글_타입별_정렬_조회_요청_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("post-findByVaccinationType-paging-sorting"));
    }

    private void 글_타입별_시간필터링_조회_요청_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("post-findByVaccinationType-paging-filteringHour"));
    }

    private void 글_타입별_페이징_조회_요청_성공함_게시글없음(ResultActions response, List<PostResponse> postResponses) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(postResponses)))
                .andDo(print())
                .andDo(toDocument("post-findByVaccinationType-paging-when-empty"));
    }
}
