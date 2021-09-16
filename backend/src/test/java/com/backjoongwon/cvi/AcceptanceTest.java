package com.backjoongwon.cvi;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.auth.dto.AuthRequest;
import com.backjoongwon.cvi.auth.service.AuthService;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    protected static final long INVALID_ID = 999L;

    @LocalServerPort
    int port;

    @MockBean
    private AuthService authService;

    @PersistenceContext
    private EntityManager em;

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

    @AfterEach
    protected void truncate() {
        List<String> tableNames = Arrays.asList("COMMENT", "LIKES", "POST", "USER");
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        for (String tableName : tableNames) {
            em.createNativeQuery("TRUNCATE TABLE " + tableName + " RESTART IDENTITY").executeUpdate();
        }
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

    protected UserResponse 가입된_회원_로그인() {
        return willReturn(가입회원).given(authService).authenticate(any(AuthRequest.class), any(String.class));
    }

    protected UserResponse 신규_회원_로그인() {
        return willReturn(신규회원).given(authService).authenticate(any(AuthRequest.class), any(String.class));
    }

    protected UserResponse 회원_가입_되어있음(UserRequest signupRequest) {
        ExtractableResponse<Response> signupResponse = 회원_가입_요청(signupRequest);
        return signupResponse.as(UserResponse.class);
    }

    protected ExtractableResponse<Response> 회원_가입_요청(UserRequest userRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userRequest)
                .when().post("/api/v1/users/signup")
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 게시글_작성_요청(UserResponse user, PostRequest postRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + user.getAccessToken())
                .body(postRequest)
                .when().post("/api/v1/posts")
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 단일_게시글_조회(Long postId) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/v1/posts/{postId}", postId)
                .then().log().all()
                .extract();
    }
}
