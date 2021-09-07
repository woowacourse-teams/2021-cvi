package com.backjoongwon.cvi.auth.service;

import com.backjoongwon.cvi.auth.domain.authorization.AuthorizationManager;
import com.backjoongwon.cvi.auth.dto.AuthRequest;
import com.backjoongwon.cvi.auth.dto.profile.UserInformation;
import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import com.backjoongwon.cvi.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationManager authorizationManager;

    @Transactional
    public UserResponse authenticate(AuthRequest authRequest) {
        UserInformation userInformation =
                authorizationManager.requestUserInfo(authRequest.getProvider(), authRequest.getCode(), authRequest.getState());
        return createUserResponse(authRequest, userInformation);
    }

    private UserResponse createUserResponse(AuthRequest authRequest, UserInformation userInformation) {
        Optional<User> foundUser = userRepository.findBySocialProviderAndSocialId(authRequest.getProvider(), userInformation.getSocialId());
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            String token = jwtTokenProvider.createToken(user.getId());
            return UserResponse.of(user, token);
        }
        return UserResponse.newUser(authRequest.getProvider(), userInformation.getSocialId(), userInformation.getSocialProfileUrl());
    }
}
