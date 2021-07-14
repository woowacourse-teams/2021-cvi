package com.backjoongwon.cvi.user.application;


import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import com.backjoongwon.cvi.user.dto.LoginResponse;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse signup(UserRequest userRequest) {
        if (userRepository.existsByNickname(userRequest.getNickname())) {
            throw new DuplicateException("닉네임은 중복될 수 없습니다.");
        }
        User user = userRepository.save(userRequest.toEntity());
        return UserResponse.of(user);
    }

    public LoginResponse signin(UserRequest userRequest) {
        return null;
    }

    public UserResponse findById(Long id) {
        return UserResponse.of(findUserById(id));
    }

    @Transactional
    public void update(Long id, UserRequest updateRequest) {
        User foundUser = findUserById(id);
        foundUser.update(updateRequest.toEntity());
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("해당 id의 사용자가 없습니다.");
        }
        userRepository.deleteById(id);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 사용자가 없습니다."));
    }
}
