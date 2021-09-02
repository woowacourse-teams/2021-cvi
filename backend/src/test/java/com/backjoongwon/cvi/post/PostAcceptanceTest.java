package com.backjoongwon.cvi.post;

import com.backjoongwon.cvi.AcceptanceTest;
import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.post.domain.Sort;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게시글 관련 인수 테스트")
public class PostAcceptanceTest extends AcceptanceTest {

    protected UserResponse userResponse;
    protected UserResponse anotherUserResponse;
    private PostRequest invalidPostRequest;
    protected PostRequest postRequestPFIZER;
    private PostRequest postRequestAZ;
    private PostRequest postRequestMODERNA;
    private PostRequest postRequestJANSSEN;
    private PostRequest updateRequest;

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

        invalidPostRequest = new PostRequest("     ", VaccinationType.PFIZER);
        postRequestPFIZER = new PostRequest("게시글 내용1", VaccinationType.PFIZER);
        postRequestAZ = new PostRequest("게시글 내용2", VaccinationType.ASTRAZENECA);
        postRequestMODERNA = new PostRequest("게시글 내용3", VaccinationType.MODERNA);
        postRequestJANSSEN = new PostRequest("게시글 내용4", VaccinationType.JANSSEN);
        updateRequest = new PostRequest("수정된 내용", VaccinationType.JANSSEN);
    }

    @DisplayName("게시글 작성 - 성공")
    @Test
    void create() {
        //given
        //when
        ExtractableResponse<Response> response = 게시글_작성_요청(userResponse, postRequestPFIZER);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.as(PostResponse.class).getWriter().getId()).isEqualTo(userResponse.getId());
        assertThat(response.as(PostResponse.class).getVaccinationType()).isEqualTo(VaccinationType.PFIZER);
    }

    @DisplayName("게시글 작성 - 실패 - 로그인 유저가 아닌 경우")
    @Test
    void createFailureWhenNoSignedUser() {
        //given
        //when
        ExtractableResponse<Response> response = 게시글_작성_요청(신규회원, postRequestPFIZER);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("게시글 작성 - 실패 - 게시글 내용이 유효하지 않은 경우")
    @Test
    void createFailureWhen() {
        //given
        //when
        ExtractableResponse<Response> response = 게시글_작성_요청(userResponse, invalidPostRequest);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest(name = "백신 타입별 게시글 조회 - 성공")
    @MethodSource
    void findByVaccineType(VaccinationType vaccinationType) {
        //given
        게시글_작성_요청(userResponse, postRequestPFIZER);
        게시글_작성_요청(userResponse, postRequestAZ);
        게시글_작성_요청(userResponse, postRequestMODERNA);
        게시글_작성_요청(userResponse, postRequestJANSSEN);
        //when
        ExtractableResponse<Response> responseOfAllType = 백신_타입별_게시글_조회(null, userResponse);
        ExtractableResponse<Response> responseOfType = 백신_타입별_게시글_조회(vaccinationType, userResponse);
        //then
        assertThat(responseOfAllType.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseOfAllType.as(List.class)).hasSize(4);

        assertThat(responseOfType.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseOfType.as(List.class).get(0)).extracting("vaccinationType").isEqualTo(vaccinationType.name());
    }

    static Stream<Arguments> findByVaccineType() {
        return Stream.of(
                Arguments.of(VaccinationType.ASTRAZENECA),
                Arguments.of(VaccinationType.PFIZER),
                Arguments.of(VaccinationType.JANSSEN),
                Arguments.of(VaccinationType.MODERNA));
    }

    @DisplayName("단일 게시글 조회 - 성공")
    @Test
    void findById() {
        //given
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        //when
        ExtractableResponse<Response> response = 단일_게시글_조회(postResponse.getId());
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(PostResponse.class).getId()).isEqualTo(postResponse.getId());
    }

    @DisplayName("단일 게시글 조회 - 실패 - 존재하지 않는 게시글 id인 경우")
    @Test
    void findByIdFailure() {
        //given
        //when
        ExtractableResponse<Response> response = 단일_게시글_조회(INVALID_ID);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("게시글 타입별 조회 페이징 - 성공")
    @Test
    void findByVaccineTypePaging() {
        //given
        List<PostRequest> postRequests = Arrays.asList(
                new PostRequest("내용1", VaccinationType.PFIZER),
                new PostRequest("내용2", VaccinationType.PFIZER),
                new PostRequest("내용3", VaccinationType.PFIZER),
                new PostRequest("내용4", VaccinationType.MODERNA)
        );

        for (PostRequest postRequest : postRequests) {
            게시글_작성_요청(userResponse, postRequest);
        }
        //when
        ExtractableResponse<Response> response = 백신_타입별_게시글_페이징_조회(VaccinationType.PFIZER, 1, 2, Sort.CREATED_AT_DESC, userResponse);
        List<String> resultPostContents = response.jsonPath()
                .getList(".", PostResponse.class)
                .stream()
                .map(PostResponse::getContent)
                .collect(Collectors.toList());
        //then
        assertThat(resultPostContents).containsExactly("내용2", "내용1");
    }

    private ExtractableResponse<Response> 백신_타입별_게시글_페이징_조회(VaccinationType vaccinationType, int offset, int size, Sort sort, UserResponse user) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + user.getAccessToken())
                .param("vaccinationType", vaccinationType)
                .queryParam("offset", offset)
                .param("size", size)
                .param("sort", sort)
                .when().get("/api/v1/posts/paging")
                .then().log().all()
                .extract();
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void update() {
        //given
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        //when
        ExtractableResponse<Response> response = 게시글_수정_요청(postResponse.getId(), userResponse, updateRequest);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("게시글 수정 - 실패 - 인증된 유저가 아닌 경우, 게시글 작성자가 아닌 경우, 게시글이 없는 경우")
    @Test
    void updateFailureWhenUnAuthorizedUser() {
        //given
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        //when
        ExtractableResponse<Response> UnAuthorizedResponse = 게시글_수정_요청(postResponse.getId(), 신규회원, updateRequest);
        ExtractableResponse<Response> noWriterResponse = 게시글_수정_요청(postResponse.getId(), anotherUserResponse, updateRequest);
        ExtractableResponse<Response> noExistsPostResponse = 게시글_수정_요청(INVALID_ID, userResponse, updateRequest);
        //then
        assertThat(UnAuthorizedResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(noWriterResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(noExistsPostResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void delete() {
        //given
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        //when
        ExtractableResponse<Response> response = 게시글_삭제_요청(postResponse.getId(), userResponse);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("게시글 삭제 - 실패 - 인증된 유저가 아닌 경우, 게시글 작성자가 아닌 경우, 게시글이 없는 경우")
    @Test
    void deleteFailureWhenUnAuthorizedUser() {
        //given
        ExtractableResponse<Response> createPostResponse = 게시글_작성_요청(userResponse, postRequestPFIZER);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        //when
        ExtractableResponse<Response> UnAuthorizedResponse = 게시글_삭제_요청(postResponse.getId(), 신규회원);
        ExtractableResponse<Response> noWriterResponse = 게시글_삭제_요청(postResponse.getId(), anotherUserResponse);
        ExtractableResponse<Response> noExistsPostResponse = 게시글_삭제_요청(INVALID_ID, userResponse);
        //then
        assertThat(UnAuthorizedResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(noWriterResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(noExistsPostResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private ExtractableResponse<Response> 백신_타입별_게시글_조회(VaccinationType vaccinationType, UserResponse user) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("vaccinationType", vaccinationType)
                .when().get("/api/v1/posts")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 게시글_수정_요청(Long postId, UserResponse user, PostRequest updateRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + user.getAccessToken())
                .body(updateRequest)
                .when().put("/api/v1/posts/{postId}", postId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 게시글_삭제_요청(Long postId, UserResponse user) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + user.getAccessToken())
                .when().delete("/api/v1/posts/{postId}", postId)
                .then().log().all()
                .extract();
    }
}
