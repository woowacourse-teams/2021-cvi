package com.backjoongwon.cvi.auth.application;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.auth.domain.profile.UserInformation;
import com.backjoongwon.cvi.auth.dto.AuthRequest;
import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import com.backjoongwon.cvi.user.dto.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResponse authenticate(AuthRequest authRequest) {
        UserInformation userInfo = SocialProvider.requestProfile(authRequest.getProvider(), authRequest.getCode(), authRequest.getState());

        return createUserResponse(authRequest, userInfo);
    }

    private UserResponse createUserResponse(AuthRequest authRequest, UserInformation userInformation) {
        Optional<User> foundUser = userRepository.findBySocialProviderAndSocialId(authRequest.getProvider(), userInformation.getSocialId());

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            String token = jwtTokenProvider.createToken(user.getId());

            return UserResponse.of(user, token);
        }

        return UserResponse.of(null, null, null, false,
                null, authRequest.getProvider(), userInformation.getSocialId(), userInformation.getSocialProfileUrl());
    }
}
