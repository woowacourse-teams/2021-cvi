package com.backjoongwon.cvi.user;

import com.backjoongwon.cvi.AcceptanceTest;
import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
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

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("유저 관련 인수 테스트")
public class UserAcceptanceTest extends AcceptanceTest {

    private UserRequest userRequest;
    private UserRequest updateRequest;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        userRequest = UserRequest.builder()
                .nickname("닉네임")
                .ageRange(AgeRange.TWENTIES)
                .socialProvider(SocialProvider.NAVER)
                .socialId("NAVER_ID")
                .socialProfileUrl("naver.com/profile")
                .build();
        updateRequest = UserRequest.builder()
                .nickname("수정된닉네임")
                .ageRange(AgeRange.THIRTIES)
                .socialProvider(SocialProvider.KAKAO)
                .socialId("KAKAO_ID")
                .socialProfileUrl("kakao.com/profile")
                .build();
    }

    @DisplayName("신규 회원 가입 - 성공")
    @Test
    void signup() {
        //given
        //when
        ExtractableResponse<Response> response = 회원_가입_요청(userRequest);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.as(UserResponse.class).getId()).isEqualTo(1L);
        assertThat(response.as(UserResponse.class).getAccessToken()).isNotNull();
    }

    @ParameterizedTest(name = "신규 회원 가입 - 실패 - 유효하지 않은 값인 경우")
    @MethodSource
    void signupFailure(UserRequest userRequest) {
        //given
        //when
        ExtractableResponse<Response> response = 회원_가입_요청(userRequest);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    static Stream<Arguments> signupFailure() {
        return Stream.of(
                Arguments.of(new UserRequest("345612873681273468127364", AgeRange.TEENS, false, SocialProvider.NAVER, "NAVER_ID", "naver.com/profile")),
                Arguments.of(new UserRequest("nickname", null, false, SocialProvider.NAVER, "NAVER_ID", "naver.com/profile")),
                Arguments.of(new UserRequest("nickname", AgeRange.TWENTIES, false, null, "NAVER_ID", "naver.com/profile")),
                Arguments.of(new UserRequest("nickname", AgeRange.TWENTIES, false, SocialProvider.NAVER, "", "naver.com/profile")),
                Arguments.of(new UserRequest("nickname", AgeRange.TWENTIES, false, SocialProvider.NAVER, "NAVER_ID", ""))
        );
    }

    @DisplayName("내 정보 조회 - 성공")
    @Test
    void findMe() {
        //given
        ExtractableResponse<Response> signupResponse = 회원_가입_요청(userRequest);
        //when
        UserResponse userResponse = signupResponse.as(UserResponse.class);
        ExtractableResponse<Response> response = 내_정보_조회(userResponse);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(UserResponse.class).getId()).isEqualTo(userResponse.getId());
        assertThat(response.as(UserResponse.class).getNickname()).isEqualTo(userResponse.getNickname());
    }

    @DisplayName("내 정보 조회 - 실패 - 가입된 유저가 아닌 경우")
    @Test
    void findMeFailureWhenNotSignedinUser() {
        //given
        //when
        ExtractableResponse<Response> response = 내_정보_조회(신규회원);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("내 정보 수정 - 성공")
    @Test
    void update() {
        //given
        ExtractableResponse<Response> signupResponse = 회원_가입_요청(userRequest);
        //when
        UserResponse userResponse = signupResponse.as(UserResponse.class);
        ExtractableResponse<Response> response = 내_정보_수정(userResponse, updateRequest);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @ParameterizedTest(name = "내 정보 수정 - 실패 - 유효하지 않은 요청 값인 경우")
    @MethodSource
    void updateFailure(UserRequest invalidRequest) {
        //given
        ExtractableResponse<Response> signupResponse = 회원_가입_요청(userRequest);
        회원_가입_요청(updateRequest);
        //when
        UserResponse userResponse = signupResponse.as(UserResponse.class);
        ExtractableResponse<Response> response = 내_정보_수정(userResponse, invalidRequest);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    static Stream<Arguments> updateFailure() {
        return Stream.of(
                Arguments.of(new UserRequest("수정된닉네임", AgeRange.TEENS, false, SocialProvider.NAVER, "NAVER_ID", "naver.com/profile")),
                Arguments.of(new UserRequest("345612873681273468127364", AgeRange.TEENS, false, SocialProvider.NAVER, "NAVER_ID", "naver.com/profile")),
                Arguments.of(new UserRequest("nickname", null, false, SocialProvider.NAVER, "NAVER_ID", "naver.com/profile")),
                Arguments.of(new UserRequest("nickname", AgeRange.TWENTIES, false, null, "NAVER_ID", "naver.com/profile")),
                Arguments.of(new UserRequest("nickname", AgeRange.TWENTIES, false, SocialProvider.NAVER, "", "naver.com/profile")),
                Arguments.of(new UserRequest("nickname", AgeRange.TWENTIES, false, SocialProvider.NAVER, "NAVER_ID", ""))
        );
    }

    @DisplayName("사용자 조회 - 성공")
    @Test
    void find() {
        //given
        ExtractableResponse<Response> signupResponse = 회원_가입_요청(userRequest);
        UserResponse userResponse = signupResponse.as(UserResponse.class);
        //when
        ExtractableResponse<Response> response = 사용자_조회(userResponse.getId());
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(UserResponse.class).getId()).isEqualTo(userResponse.getId());
        assertThat(response.as(UserResponse.class).getNickname()).isEqualTo(userResponse.getNickname());
    }

    @DisplayName("사용자 조회 - 실패 - 존재하는 사용자가 아닌 경우")
    @Test
    void findFailure() {
        //given
        //when
        ExtractableResponse<Response> response = 사용자_조회(INVALID_ID);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("사용자 삭제 - 성공")
    @Test
    void delete() {
        //given
        ExtractableResponse<Response> signupResponse = 회원_가입_요청(userRequest);
        UserResponse userResponse = signupResponse.as(UserResponse.class);
        //when
        ExtractableResponse<Response> response = 사용자_삭제(userResponse);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("사용자 삭제 - 실패 - 로그인 유저가 요청한 게 아닌 경우")
    @Test
    void deleteFailureWhenNoSignedinUser() {
        //given
        //when
        ExtractableResponse<Response> response = 사용자_삭제(신규회원);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("사용자 삭제 - 실패 - 해당 유저가 존재하지 않는 경우")
    @Test
    void deleteFailureWhenNoExistsUser() {
        ExtractableResponse<Response> signupResponse = 회원_가입_요청(userRequest);
        회원_가입_요청(updateRequest);
        //when
        UserResponse userResponse = signupResponse.as(UserResponse.class);
        사용자_삭제(userResponse);
        ExtractableResponse<Response> response = 사용자_삭제(userResponse);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private ExtractableResponse<Response> 내_정보_조회(UserResponse userResponse) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + userResponse.getAccessToken())
                .when().get("/api/v1/users/me")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 내_정보_수정(UserResponse userResponse, UserRequest updateRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + userResponse.getAccessToken())
                .body(updateRequest)
                .when().put("/api/v1/users/me")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 사용자_조회(Long userId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/v1/users/{userId}", userId)
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 사용자_삭제(UserResponse userResponse) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + userResponse.getAccessToken())
                .when().delete("/api/v1/users")
                .then().log().all()
                .extract();
    }
}
