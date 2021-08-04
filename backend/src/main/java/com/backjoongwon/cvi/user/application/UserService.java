package com.backjoongwon.cvi.user.application;


import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
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
        validateDuplicateNickname(userRequest.getNickname());
        User user = userRepository.save(userRequest.toEntity());
        String accessToken = jwtTokenProvider.createToken(user.getId());
        return UserResponse.of(user, accessToken);
    }

    public boolean isValidAccessToken(String accessToken) {
        return jwtTokenProvider.isValidToken(accessToken);
    }

    public UserResponse findById(Long id) {
        return UserResponse.of(findUserById(id), null);
    }

    public UserResponse findUser(Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        return UserResponse.of(findUserById(user.getId()), null);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 사용자가 없습니다."));
    }

    @Transactional
    public void update(Optional<User> optionalUser, UserRequest userRequest) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        String nickname = user.getNickname();
        if (!nickname.equals(userRequest.getNickname())) {
            validateDuplicateNickname(userRequest.getNickname());
        }
        user.update(userRequest.toEntity());
    }

    private void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicateException("닉네임이 이미 사용중입니다.");
        }
    }

    @Transactional
    public void delete(Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        if (!userRepository.existsById(user.getId())) {
            throw new NotFoundException("해당 id의 사용자가 없습니다.");
        }
        postRepository.deleteAllByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }

    private void validateSignedin(Optional<User> optionalUser) {
        optionalUser.orElseThrow(() -> new UnAuthorizedException("인증되지 않은 사용자입니다."));
    }
}