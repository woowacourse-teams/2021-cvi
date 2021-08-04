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
        userRequest = new UserRequest("인비", AgeRange.TEENS, false, SocialProvider.NAVER, null, null);
    }

    @DisplayName("사용자 회원가입 - 성공")
    @Test
    void signup() {
        //given
        //when
        UserResponse userResponse = userService.signup(userRequest);
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
        UserResponse signup = userService.signup(userRequest);
        willReturn("1").given(jwtTokenProvider).getPayload("VALID_ACCESS_TOKEN");
        //when
        User user = userService.findUserById(signup.getId());
        //then
        assertThat(user.getNickname()).isEqualTo("인비");
        assertThat(user.getAgeRange().getMeaning()).isEqualTo(AgeRange.TEENS.getMeaning());
    }

    @DisplayName("토큰 검증 - 실패 - 유효하지 않은 토큰인 대우 예외를 던진다.")
    @Test
    void validateAccessTokenFailure() {
        //given
        willThrow(new UnAuthorizedException("유효하지 않은 토큰입니다.")).given(jwtTokenProvider).isValidToken("INVALID_ACCESS_TOKEN");
        //when
        //then
        assertThatThrownBy(() -> userService.isValidAccessToken("INVALID_ACCESS_TOKEN"))
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
        User user = userService.findUserById(signupResponse.getId());
        //when
        UserRequest updateRequest = new UserRequest("검프", AgeRange.THIRTIES, true, null, null, null);
        userService.update(Optional.of(user), updateRequest);
        //then
        User updatedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("사용자 조회 실패"));

        assertThat(updatedUser.getNickname()).isEqualTo(updateRequest.getNickname());
        assertThat(updatedUser.getAgeRange()).isEqualTo(AgeRange.THIRTIES);
        assertThat(updatedUser.isShotVerified()).isEqualTo(updateRequest.isShotVerified());
    }

    @DisplayName("사용자 수정 - 성공 - 자신의 닉네임을 그대로 수정할 때")
    @Test
    void updateNicknameToSameNickname() {
        //given
        UserResponse signupResponse = userService.signup(userRequest);
        User user = userService.findUserById(signupResponse.getId());
        UserRequest updateRequest = new UserRequest("인비", AgeRange.THIRTIES, false, null, null, null);
        //when
        userService.update(Optional.of(user), updateRequest);
        //then
        User updatedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("사용자 조회 실패"));

        assertThat(updatedUser.getNickname()).isEqualTo(userRequest.getNickname());
    }

    @DisplayName("사용자 수정 - 실패 - 이미 존재하는 닉네임")
    @Test
    void updateFailureWhenNicknameDuplicate() {
        //given
        UserResponse signupResponse1 = userService.signup(userRequest);
        User user1 = userService.findUserById(signupResponse1.getId());
        UserRequest signUpRequest2 = new UserRequest("검프", AgeRange.THIRTIES, false, null, null, null);
        UserResponse signupResponse2 = userService.signup(signUpRequest2);
        User user2 = userService.findUserById(signupResponse2.getId());

        UserRequest updateRequest = UserRequest.builder()
                .nickname("인비")
                .ageRange(AgeRange.FIFTIES)
                .build();
        //when
        assertThatThrownBy(() -> userService.update(Optional.of(user2), updateRequest))
                .isInstanceOf(DuplicateException.class);
        //then
        User notUpdatedUser1 = userRepository.findById(user1.getId())
                .orElseThrow(() -> new NotFoundException("사용자 조회 실패"));

        assertThat(notUpdatedUser1.getNickname()).isEqualTo(userRequest.getNickname());
        assertThat(notUpdatedUser1.getAgeRange()).isSameAs(userRequest.getAgeRange());

        User notUpdatedUser2 = userRepository.findById(signupResponse2.getId())
                .orElseThrow(() -> new NotFoundException("사용자 조회 실패"));

        assertThat(notUpdatedUser2.getNickname()).isEqualTo(signUpRequest2.getNickname());
        assertThat(notUpdatedUser2.getAgeRange()).isSameAs(signUpRequest2.getAgeRange());
    }

    @DisplayName("사용자 수정 - 실패 - 존재하지 않는 User")
    @Test
    void updateFailureWhenNotExists() {
        //given
        Long lastIndex = getLastIndex();
        //when
        UserRequest updateRequest = new UserRequest(null, null, false, null, null, null);
        //then
        assertThatThrownBy(() -> userService.update(Optional.empty(), updateRequest))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("사용자 삭제 - 성공")
    @Test
    void delete() {
        //given
        UserResponse userResponse = userService.signup(userRequest);
        User user = userService.findUserById(userResponse.getId());
        //when
        userService.delete(Optional.of(user));
        //then
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isEmpty();
    }

    @DisplayName("사용자 삭제 - 실패 - 존재하지 않는 User")
    @Test
    void deleteFailureWhenNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> userService.delete(Optional.empty()))
                .isInstanceOf(UnAuthorizedException.class);
    }

    private Long getLastIndex() {
        User lastUser = User.builder()
                .nickname("라이언")
                .build();
        return userRepository.save(lastUser).getId();
    }
}
