package com.backjoongwon.cvi.like;

import com.backjoongwon.cvi.AcceptanceTest;
import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
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

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("좋아요 관련 인수 테스트")
public class LikeAcceptanceTest extends AcceptanceTest {

    protected UserResponse userResponse;
    protected UserResponse anotherUserResponse;
    protected PostRequest postRequestPFIZER;

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
    }

    @DisplayName("좋아요 생성 - 성공")
    @Test
    void createLike() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        //when
        ExtractableResponse<Response> response = 게시글_좋아요_생성(postResponse.getId(), userResponse);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(단일_게시글_조회(postResponse.getId()).as(PostResponse.class).getLikeCount()).isEqualTo(1);
    }

    @DisplayName("좋아요 생성 - 실패 - 인증된 유저가 아닌 경우, 게시글이 없는 경우, 이미 좋아요를 누른 유저인 경우 ")
    @Test
    void createLikeFailure() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        //when
        ExtractableResponse<Response> UnAuthorizedResponse = 게시글_좋아요_생성(postResponse.getId(), 신규회원);
        ExtractableResponse<Response> NoExistsPostResponse = 게시글_좋아요_생성(INVALID_ID, userResponse);
        게시글_좋아요_생성(postResponse.getId(), userResponse);
        ExtractableResponse<Response> alreadyLikedResponse = 게시글_좋아요_생성(postResponse.getId(), userResponse);
        //then
        assertThat(UnAuthorizedResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(NoExistsPostResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(alreadyLikedResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("좋아요 삭제 - 성공")
    @Test
    void deleteLike() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        게시글_좋아요_생성(postResponse.getId(), userResponse);
        //when
        ExtractableResponse<Response> response = 게시글_좋아요_삭제(postResponse.getId(), userResponse);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(단일_게시글_조회(postResponse.getId()).as(PostResponse.class).getLikeCount()).isEqualTo(0);
    }

    @DisplayName("좋아요 삭제 - 실패 - 인증된 유저가 아닌 경우, 게시글이 없는 경우, 좋아요를 누르지 않은 유저인 경우 ")
    @Test
    void deleteLikeFailure() {
        //given
        PostResponse postResponse = 게시글_작성_되어있음();
        //when
        ExtractableResponse<Response> UnAuthorizedResponse = 게시글_좋아요_삭제(postResponse.getId(), 신규회원);
        ExtractableResponse<Response> NoExistsPostResponse = 게시글_좋아요_삭제(INVALID_ID, userResponse);
        ExtractableResponse<Response> alreadyLikedResponse = 게시글_좋아요_삭제(postResponse.getId(), anotherUserResponse);
        //then
        assertThat(UnAuthorizedResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(NoExistsPostResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(alreadyLikedResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private PostResponse 게시글_작성_되어있음() {
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        return createPostResponse.as(PostResponse.class);
    }

    private ExtractableResponse<Response> 게시글_좋아요_생성(Long postId, UserResponse user) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + user.getAccessToken())
                .when().post("/api/v1/posts/{postId}/likes", postId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 게시글_좋아요_삭제(Long postId, UserResponse user) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + user.getAccessToken())
                .when().delete("/api/v1/posts/{postId}/likes", postId)
                .then().log().all()
                .extract();
    }
}
