package com.backjoongwon.cvi.user.application;


import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import com.backjoongwon.cvi.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponse signup(UserRequest userRequest) {
        if (userRepository.existsByNickname(userRequest.getNickname())) {
            throw new DuplicateException("닉네임은 중복될 수 없습니다.");
        }

        User user = userRepository.save(userRequest.toEntity());
        String accessToken = jwtTokenProvider.createToken(user.getId());

        return UserResponse.of(user, accessToken);
    }

    public void validateAccessToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new UnAuthorizedException("유효하지 않은 토큰입니다.");
        }
    }

    public User findUserByAccessToken(String accessToken) {
        Long userId = Long.valueOf(jwtTokenProvider.getPayload(accessToken));
        return findUserById(userId);
    }

    public UserResponse findById(Long id) {
        return UserResponse.of(findUserById(id), null);
    }

    public UserResponse findMeById(Long id) {
        return UserResponse.of(findUserById(id), null);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 사용자가 없습니다."));
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
        postRepository.deleteAllByUserId(id);
        userRepository.deleteById(id);
    }
}
