package com.backjoongwon.cvi.comment;

import com.backjoongwon.cvi.AcceptanceTest;
import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

        ExtractableResponse<Response> signupResponse = 회원_가입_요청(userRequest);
        userResponse = signupResponse.as(UserResponse.class);

        ExtractableResponse<Response> anotherSignupResponse = 회원_가입_요청(anotherUserRequest);
        anotherUserResponse = anotherSignupResponse.as(UserResponse.class);

        postRequestPFIZER = new PostRequest("게시글 내용1", VaccinationType.PFIZER);
        commentRequest = new CommentRequest("댓글 내용");
        updateRequest = new CommentRequest("수정된 댓글 내용");
    }

    @DisplayName("댓글 생성 - 성공")
    @Test
    void createLike() {
        //given
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
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
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
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
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
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
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
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
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
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
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
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
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
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
