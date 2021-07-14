package com.backjoongwon.cvi.user.application;

import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DisplayName("사용자 비즈니스 흐름 테스트")
@Transactional
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest("인비", 10);
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
        assertThat(userResponse.getAgeRange()).isEqualTo(10);
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
        //when
        UserRequest updateRequest = new UserRequest(userRequest.getNickname(), 30);
        userService.update(signupResponse.getId(), updateRequest);
        //then
        User updatedUser = userRepository.findById(signupResponse.getId())
                .orElseThrow(() -> new NotFoundException("사용자 조회 실패"));

        assertThat(updatedUser.getAgeRange()).isEqualTo(AgeRange.THIRTIES);
    }

    @DisplayName("사용자 수정 - 실패 - 존재하지않는 User")
    @Test
    void updateFailureWhenNotExists() {
        //given
        Long lastIndex = getLastIndex();
        //when
        UserRequest updateRequest = new UserRequest("인비2", 30);
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
