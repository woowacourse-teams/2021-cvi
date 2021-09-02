package com.backjoongwon.cvi.auth.domain.authorization;

import com.backjoongwon.cvi.auth.dto.profile.UserInformation;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizationManager {

    private final Map<String, Authorization> authorizationMap;

    public UserInformation requestUserInfo(SocialProvider provider, String code, String state) {
        Authorization authorization = extractAuthorization(provider);
        return authorization.requestProfile(code, state);
    }

    private Authorization extractAuthorization(SocialProvider provider) {
        validateSocialProvider(provider);
        String key = provider.convertToComponentName();
        if (!authorizationMap.containsKey(key)) {
            log.info(String.format("해당 OAuth 제공자가 존재하지 않습니다 입력값: %s", key));
            throw new InvalidOperationException(String.format("해당 OAuth 제공자가 존재하지 않습니다 입력값: %s", key));
        }
        return authorizationMap.get(key);
    }

    private void validateSocialProvider(SocialProvider provider) {
        if (Objects.isNull(provider)) {
            log.info("해당 OAuth 제공자가 존재하지 않습니다 입력값: null");
            throw new InvalidOperationException("해당 OAuth 제공자가 존재하지 않습니다 입력값: null");
        }
    }
}

