package com.backjoongwon.cvi;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.auth.dto.AuthRequest;
import com.backjoongwon.cvi.auth.service.AuthService;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @MockBean
    private AuthService authService;

    protected String ACCESS_TOKEN = "{ACCESS_TOKEN created by jwt}";
    protected UserResponse 가입회원;
    protected UserResponse 신규회원;
    protected Optional<User> 비회원;

    @BeforeEach
    protected void setUp() {
        RestAssured.port = port;
        User user = User.builder()
                .id(1L)
                .nickname("유저")
                .ageRange(AgeRange.TEENS)
                .socialProvider(SocialProvider.NAVER)
                .socialId("NAVER_MEMBER")
                .profileUrl("naver.com/member/profile")
                .createdAt(LocalDateTime.now())
                .build();

        가입회원 = UserResponse.of(user, ACCESS_TOKEN);
        신규회원 = UserResponse.newUser(SocialProvider.NAVER, "NAVER_ID", "naver.com/profile");
        비회원 = Optional.empty();
    }

    protected UserResponse 가입된_회원_로그인() {
        return willReturn(가입회원).given(authService).authenticate(any(AuthRequest.class));
    }

    protected UserResponse 신규_회원_로그인() {
        return willReturn(신규회원).given(authService).authenticate(any(AuthRequest.class));
    }

    protected ExtractableResponse<Response> 회원_가입_요청(UserRequest userRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userRequest)
                .when().post("/api/v1/users/signup")
                .then().log().all()
                .extract();
    }
}
