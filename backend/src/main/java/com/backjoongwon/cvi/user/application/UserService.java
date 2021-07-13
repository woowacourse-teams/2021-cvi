package com.backjoongwon.cvi.user.application;

import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse signup(UserRequest userRequest) {
        userRepository.findByNickname(userRequest.getNickname())
                .ifPresent(user -> {
                    throw new DuplicateException("닉네임은 중복될 수 없습니다.");
                });

        User savedUser = userRepository.save(userRequest.toEntity());
        return UserResponse.of(savedUser);
    }

    public UserResponse findById(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 사용자가 없습니다."));
        return UserResponse.of(foundUser);
    }

    public void update(Long id, UserRequest updateRequest) {
        User beforeUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 사용자가 없습니다."));

        User updateUser = updateRequest.toEntity();
        beforeUser.update(updateUser);
    }

    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 사용자가 없습니다."));

        userRepository.deleteById(id);
    }
}
