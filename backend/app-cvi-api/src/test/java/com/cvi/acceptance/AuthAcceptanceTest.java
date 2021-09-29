package com.cvi.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.cvi.dto.AuthRequest;
import com.cvi.dto.UserResponse;
import com.cvi.user.domain.model.SocialProvider;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("소셜 로그인 관련 테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("소셜로그인 - 이미 가입된 회원인 경우")
    @Test
    void authenticateWhenSigninedUser() {
        //given
        AuthRequest authRequest = new AuthRequest(SocialProvider.NAVER, "CODE", "STATE");
        //when
        가입된_회원_로그인();
        ExtractableResponse<Response> response = 소셜_로그인_요청(authRequest);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(UserResponse.class).getAccessToken()).isNotNull();
    }

    @DisplayName("소셜로그인 - 신규 회원인 경우")
    @Test
    void authenticateWhenNewUser() {
        //given
        AuthRequest authRequest = new AuthRequest(SocialProvider.NAVER, "CODE", "STATE");
        //when
        신규_회원_로그인();
        ExtractableResponse<Response> response = 소셜_로그인_요청(authRequest);
        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(UserResponse.class).getId()).isNull();
        assertThat(response.as(UserResponse.class).getAccessToken()).isNull();
    }

    private ExtractableResponse<Response> 소셜_로그인_요청(AuthRequest authRequest) {
        return RestAssured.given().log().all()
                .header("Origin", "http://localhost:9000")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(authRequest)
                .when().post("/api/v1/auth")
                .then().log().all()
                .extract();
    }
}
