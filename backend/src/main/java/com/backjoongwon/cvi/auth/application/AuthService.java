package com.backjoongwon.cvi.auth.application;

import com.backjoongwon.cvi.auth.domain.authorization.Authorization;
import com.backjoongwon.cvi.auth.domain.authorization.AuthorizationFactory;
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

    public UserResponse authorize(AuthRequest authRequest) {
        AuthorizationFactory authorizationFactory = new AuthorizationFactory();
        Authorization authorization = authorizationFactory.of(authRequest.getProvider());
        UserInformation userInformation = authorization.requestProfile(authRequest.getCode(), authRequest.getState());

        return createUserResponse(authRequest, userInformation);
    }

    private UserResponse createUserResponse(AuthRequest authRequest, UserInformation userInformation) {
        Optional<User> foundUser = userRepository.findBySocialProviderAndSocialId(authRequest.getProvider(), userInformation.getSocialId());

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            User updateUser = User.builder()
                    .profileUrl(userInformation.getProfileUrl())
                    .ageRange(user.getAgeRange())
                    .socialProvider(authRequest.getProvider())
                    .socialId(userInformation.getSocialId())
                    .profileUrl(userInformation.getProfileUrl())
                    .build();
            user.update(updateUser);

            String token = jwtTokenProvider.createToken(user.getId());

            return UserResponse.of(user, token);
        }

        return UserResponse.of(null, null, null, false,
                null, authRequest.getProvider(), userInformation.getSocialId(), userInformation.getProfileUrl());
    }
}
