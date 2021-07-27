package com.backjoongwon.cvi.user.application;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("사용자 비즈니스 흐름 테스트")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest("인비", AgeRange.TEENS, SocialProvider.NAVER, null, null);
    }

    @DisplayName("사용자 회원가입 - 성공")
    @Test
    void signup() {
        //given
        //when
        UserResponse userResponse = userService.signup(userRequest);
        userRepository.flush();
        //then
        User foundUser = userRepository.findById(userResponse.getId())
                .orElseThrow(() -> new NotFoundException("User 조회 에러"));

        assertThat(foundUser.getNickname()).isEqualTo("인비");
        assertThat(foundUser.getAgeRange()).isEqualTo(AgeRange.TEENS);
        assertThat(userResponse.getNickname()).isEqualTo("인비");
        assertThat(userResponse.getAgeRange().getMeaning()).isEqualTo(AgeRange.TEENS.getMeaning());
    }

    @DisplayName("사용자 회원가입 - 실패 - 닉네임 중복")
    @Test
    void signupFailureWhenDuplicateNickname() {
        //given
        userService.signup(userRequest);
        //when
        //then
        assertThatThrownBy(() -> userService.signup(userRequest))
                .isInstanceOf(DuplicateException.class);
    }

    @DisplayName("Access Token을 이용한 사용자 조회 - 성공")
    @Test
    void findUserByAccessToken() {
        //given
        userService.signup(userRequest);
        willReturn("1").given(jwtTokenProvider).getPayload("VALID_ACCESS_TOKEN");
        //when
        User foundUser = userService.findUserByAccessToken("VALID_ACCESS_TOKEN");
        //then
        assertThat(foundUser.getNickname()).isEqualTo("인비");
        assertThat(foundUser.getAgeRange()).isEqualTo(AgeRange.TEENS);
    }

    @DisplayName("토큰 검증 - 실패 - 유효하지 않은 토큰인 경우 예외를 던진다.")
    @Test
    void validateAccessTokenFailure() {
        //given
        willThrow(new UnAuthorizedException("유효하지 않은 토큰입니다.")).given(jwtTokenProvider).validateToken("INVALID_ACCESS_TOKEN");
        //when
        //then
        assertThatThrownBy(() -> userService.validateAccessToken("INVALID_ACCESS_TOKEN"))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @DisplayName("사용자 조회 - 성공")
    @Test
    void findById() {
        //given
        UserResponse signupResponse = userService.signup(userRequest);
        //when
        UserResponse findResponse = userService.findById(signupResponse.getId());
        //then
        assertThat(findResponse.getId()).isEqualTo(signupResponse.getId());
    }

    @DisplayName("사용자 조회 - 실패 - 존재하지 않는 User")
    @Test
    void findByIdFailureWhenNotExists() {
        //given
        Long lastIndex = getLastIndex();
        //when
        //then
        assertThatThrownBy(() -> userService.findById(lastIndex + 1L))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("사용자 수정 - 성공")
    @Test
    void update() {
        //given
        UserResponse signupResponse = userService.signup(userRequest);
        userRepository.flush();
        //when
        UserRequest updateRequest = new UserRequest("검프", AgeRange.THIRTIES, null, null, null);
        userService.update(signupResponse.getId(), updateRequest);
        //then
        User updatedUser = userRepository.findById(signupResponse.getId())
                .orElseThrow(() -> new NotFoundException("사용자 조회 실패"));

        assertThat(updatedUser.getAgeRange()).isEqualTo(AgeRange.THIRTIES);
    }

    @DisplayName("사용자 수정 - 실패 - 존재하지 않는 User")
    @Test
    void updateFailureWhenNotExists() {
        //given
        Long lastIndex = getLastIndex();
        //when
        UserRequest updateRequest = new UserRequest(null, null, null, null, null);
        //then
        assertThatThrownBy(() -> userService.update(lastIndex + 1L, updateRequest))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("사용자 삭제 - 성공")
    @Test
    void delete() {
        //given
        UserResponse signupResponse = userService.signup(userRequest);
        //when
        userService.delete(signupResponse.getId());
        //then
        Optional<User> foundUser = userRepository.findById(signupResponse.getId());
        assertThat(foundUser).isEmpty();
    }

    @DisplayName("사용자 삭제 - 실패 - 존재하지 않는 User")
    @Test
    void deleteFailureWhenNotExists() {
        //given
        Long lastIndex = getLastIndex();
        //when
        //then
        assertThatThrownBy(() -> userService.delete(lastIndex + 1L))
                .isInstanceOf(NotFoundException.class);
    }

    private Long getLastIndex() {
        User lastUser = User.builder()
                .nickname("라이언")
                .build();
        return userRepository.save(lastUser).getId();
    }
}
