package com.cvi.service;

import com.cvi.auth.JwtTokenProvider;
import com.cvi.dto.UserRequest;
import com.cvi.dto.UserResponse;
import com.cvi.exception.DuplicateException;
import com.cvi.exception.NotFoundException;
import com.cvi.exception.UnAuthorizedException;
import com.cvi.post.domain.repository.PostRepository;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
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

    public UserResponse findById(Long id) {
        return UserResponse.of(findUserById(id), null);
    }

    public UserResponse findUser(Optional<User> optionalUser) {
        User user = getLoginUser(optionalUser);
        return UserResponse.of(findUserById(user.getId()), null);
    }

    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        log.info("해당 id의 사용자가 없습니다. 입력값: {}", id);
        throw new NotFoundException(String.format("해당 id의 사용자가 없습니다. 입력값: %s", id));
    }

    @Transactional
    public void update(Optional<User> optionalUser, UserRequest userRequest) {
        User user = getLoginUser(optionalUser);
        String nickname = user.getNickname();
        if (!nickname.equals(userRequest.getNickname())) {
            validateDuplicateNickname(userRequest.getNickname());
        }
        user.update(userRequest.toEntity());
    }

    private void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            log.info("닉네임이 이미 사용중입니다. 입력값: {}", nickname);
            throw new DuplicateException(String.format("닉네임이 이미 사용중입니다. 입력값: %s", nickname));
        }
    }

    @Transactional
    public void delete(Optional<User> optionalUser) {
        User user = getLoginUser(optionalUser);
        if (!userRepository.existsById(user.getId())) {
            throw new NotFoundException("해당 id의 사용자가 없습니다.");
        }
        postRepository.deleteAllByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }

    private User getLoginUser(Optional<User> optionalUser) {
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        log.info("인증되지 않음 사용자입니다. 입력값: null");
        throw new UnAuthorizedException("인증되지 않은 사용자입니다. 입력값: null");
    }
}
