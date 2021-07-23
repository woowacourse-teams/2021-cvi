package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.domain.profile.UserInformation;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorizationManager {

    private final Map<String, Authorization> authorizationMap;

    public UserInformation requestUserInfo(SocialProvider provider, String code, String state) {
        Authorization authorization = extractAuthorization(provider);
        return authorization.requestProfile(code, state);
    }

    private Authorization extractAuthorization(SocialProvider provider) {
        String key = provider.convertToComponentName();
        if (authorizationMap.containsKey(key)) {
            return authorizationMap.get(key);
        }
        throw new InvalidOperationException("해당 OAuth 제공자가 존재하지 않습니다");
    }
}

