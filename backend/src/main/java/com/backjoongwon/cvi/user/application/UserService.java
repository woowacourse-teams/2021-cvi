package com.backjoongwon.cvi.user.application;


import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import com.backjoongwon.cvi.user.domain.RequestUser;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
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

    public UserResponse findUser(RequestUser user) {
        user.validateSignedin();
        return UserResponse.of(findUserById(user.getId()), null);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 사용자가 없습니다."));
    }

    @Transactional
    public void update(RequestUser user, UserRequest userRequest) {
        User foundUser = findUserById(user.getId());
        String nickname = foundUser.getNickname();
        if (!nickname.equals(userRequest.getNickname())) {
            validateDuplicateNickname(userRequest.getNickname());
        }
        foundUser.update(userRequest.toEntity());
    }

    private void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicateException("닉네임이 이미 사용중입니다.");
        }
    }

    @Transactional
    public void delete(RequestUser user) {
        if (!userRepository.existsById(user.getId())) {
            throw new NotFoundException("해당 id의 사용자가 없습니다.");
        }
        postRepository.deleteAllByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }
}
