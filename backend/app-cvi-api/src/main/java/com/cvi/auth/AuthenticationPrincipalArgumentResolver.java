package com.cvi.auth;

import com.cvi.exception.NotFoundException;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Optional<User> resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String accessToken = AuthorizationExtractor.extract(Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));
        if (jwtTokenProvider.isValidToken(accessToken)) {
            String id = jwtTokenProvider.getPayload(accessToken);
            final Optional<User> user = userRepository.findById(Long.valueOf(id));
            if (user.isPresent()) {
                return user;
            }
            log.info("해당 id의 사용자가 없습니다. 입력값: {}", id);
            throw new NotFoundException(String.format("해당 id의 사용자가 없습니다. 입력값: %s", id));
        }
        return Optional.empty();
    }
}
