package com.cvi.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.cvi.dto.CommentRequest;
import com.cvi.dto.CommentResponse;
import com.cvi.dto.PostRequest;
import com.cvi.dto.PostResponse;
import com.cvi.dto.UserRequest;
import com.cvi.dto.UserResponse;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("댓글 관련 인수 테스트")
public class CommentAcceptanceTest extends AcceptanceTest {

    private UserResponse userResponse;
    private UserResponse anotherUserResponse;
    private PostRequest postRequestPFIZER;
    private CommentRequest commentRequest;
    private CommentRequest updateRequest;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        UserRequest userRequest = UserRequest.builder()
                .nickname("닉네임")
                .ageRange(AgeRange.TWENTIES)
                .socialProvider(SocialProvider.NAVER)
                .socialId("NAVER_ID")
                .socialProfileUrl("naver.com/profile")
                .build();
        UserRequest anotherUserRequest = UserRequest.builder()
                .nickname("다른유저닉네임")
                .ageRange(AgeRange.TWENTIES)
                .socialProvider(SocialProvider.NAVER)
                .socialId("NAVER_ID")
                .socialProfileUrl("naver.com/profile")
                .build();

        userResponse = 회원_가입_되어있음(userRequest);
        anotherUserResponse = 회원_가입_되어있음(anotherUserRequest);

        postRequestPFIZER = new PostRequest("게시글 내용1", VaccinationType.PFIZER);
        commentRequest = new CommentRequest("댓글 내용");
        updateRequest = new CommentRequest("수정된 댓글 내용");
    }

    @DisplayName("댓글 생성 - 성공")
    @Test
    void createLike() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        //when
        ExtractableResponse<Response> response = 게시글_댓글_생성(postResponse.getId(), userResponse, commentRequest);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(단일_게시글_조회(postResponse.getId()).as(PostResponse.class).getCommentCount()).isEqualTo(1);
    }

    @DisplayName("댓글 생성 - 실패 - 인증된 유저가 아닌 경우, 게시글이 없는 경우")
    @Test
    void createLikeFailure() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        //when
        ExtractableResponse<Response> UnAuthorizedResponse = 게시글_댓글_생성(postResponse.getId(), 신규회원, commentRequest);
        ExtractableResponse<Response> NoExistsPostResponse = 게시글_댓글_생성(INVALID_ID, userResponse, commentRequest);
        //then
        assertThat(UnAuthorizedResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(NoExistsPostResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("댓글 조회 - 성공")
    @Test
    void findCommentsOfPost() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        게시글_댓글_생성(postResponse.getId(), userResponse, commentRequest);
        //when
        ExtractableResponse<Response> response = 게시글_댓글_조회(postResponse.getId());
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(List.class)).hasSize(1);
    }

    @DisplayName("댓글 조회 - 실패 - 없는 게시글인 경우")
    @Test
    void findCommentsOfPostFailure() {
        //given
        //when
        ExtractableResponse<Response> response = 게시글_댓글_조회(INVALID_ID);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("댓글 수정 - 성공")
    @Test
    void updateComment() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        ExtractableResponse<Response> createCommentResponse = 게시글_댓글_생성(postResponse.getId(), userResponse, commentRequest);
        CommentResponse commentResponse = createCommentResponse.as(CommentResponse.class);
        //when
        ExtractableResponse<Response> response = 게시글_댓글_수정(postResponse.getId(), commentResponse.getId(), userResponse, updateRequest);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("댓글 수정 - 실패 - 인증된 유저가 아닌 경우, 없는 게시글인 경우, 없는 댓글인 경우, 댓글 작성자가 아닌 경우")
    @Test
    void updateCommentFailure() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        ExtractableResponse<Response> createCommentResponse = 게시글_댓글_생성(postResponse.getId(), userResponse, commentRequest);
        CommentResponse commentResponse = createCommentResponse.as(CommentResponse.class);
        //when
        ExtractableResponse<Response> unAuthorizedResponse = 게시글_댓글_수정(postResponse.getId(), commentResponse.getId(), 신규회원, updateRequest);
        ExtractableResponse<Response> noExistsPostResponse = 게시글_댓글_수정(INVALID_ID, commentResponse.getId(), userResponse, updateRequest);
        ExtractableResponse<Response> noExistsCommentResponse = 게시글_댓글_수정(postResponse.getId(), INVALID_ID, userResponse, updateRequest);
        ExtractableResponse<Response> noWriterResponse = 게시글_댓글_수정(postResponse.getId(), commentResponse.getId(), anotherUserResponse, updateRequest);
        //then
        assertThat(unAuthorizedResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(noExistsPostResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(noExistsCommentResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(noWriterResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("댓글 삭제 - 성공")
    @Test
    void deleteLike() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        ExtractableResponse<Response> createCommentResponse = 게시글_댓글_생성(postResponse.getId(), userResponse, commentRequest);
        CommentResponse commentResponse = createCommentResponse.as(CommentResponse.class);
        //when
        ExtractableResponse<Response> response = 게시글_댓글_삭제(postResponse.getId(), commentResponse.getId(), userResponse);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(단일_게시글_조회(postResponse.getId()).as(PostResponse.class).getCommentCount()).isEqualTo(0);
    }

    @DisplayName("댓글 삭제 - 실패 - 인증된 유저가 아닌 경우, 게시글이 없는 경우, 본인이 작성한 댓글이 아닌 경우 ")
    @Test
    void deleteLikeFailure() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        ExtractableResponse<Response> createCommentResponse = 게시글_댓글_생성(postResponse.getId(), userResponse, commentRequest);
        CommentResponse commentResponse = createCommentResponse.as(CommentResponse.class);
        //when
        ExtractableResponse<Response> UnAuthorizedResponse = 게시글_댓글_삭제(postResponse.getId(), commentResponse.getId(), 신규회원);
        ExtractableResponse<Response> NoExistsPostResponse = 게시글_댓글_삭제(INVALID_ID, commentResponse.getId(), userResponse);
        ExtractableResponse<Response> alreadyLikedResponse = 게시글_댓글_삭제(postResponse.getId(), commentResponse.getId(), anotherUserResponse);
        //then
        assertThat(UnAuthorizedResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(NoExistsPostResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(alreadyLikedResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private PostResponse 게시글_작성_되어있음() {
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        return createPostResponse.as(PostResponse.class);
    }

    private ExtractableResponse<Response> 게시글_댓글_생성(Long postId, UserResponse user, CommentRequest commentRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + user.getAccessToken())
                .body(commentRequest)
                .when().post("/api/v1/posts/{postId}/comments", postId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 게시글_댓글_조회(Long postId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/v1/posts/{postId}/comments", postId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 게시글_댓글_수정(Long postId, Long commentId, UserResponse user, CommentRequest commentRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + user.getAccessToken())
                .body(commentRequest)
                .when().put("/api/v1/posts/{postId}/comments/{commentId}", postId, commentId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 게시글_댓글_삭제(Long postId, Long commentId, UserResponse user) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + user.getAccessToken())
                .when().delete("/api/v1/posts/{postId}/comments/{commentsId}", postId, commentId)
                .then().log().all()
                .extract();
    }
}
