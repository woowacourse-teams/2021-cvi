package com.cvi.service;

import com.cvi.auth.JwtTokenProvider;
import com.cvi.dto.AuthRequest;
import com.cvi.dto.UserResponse;
import com.cvi.dto.profile.UserInformation;
import com.cvi.parser.AuthorizationManager;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
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
    public UserResponse authenticate(AuthRequest authRequest, String requestOrigin) {
        UserInformation userInformation =
            authorizationManager.requestUserInfo(authRequest.getProvider(), authRequest.getCode(), authRequest.getState(), requestOrigin);
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
