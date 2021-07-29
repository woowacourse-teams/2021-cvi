package com.backjoongwon.cvi.user.auth;

import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class SigninInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        if (HttpMethod.OPTIONS.toString().equals(method) || HttpMethod.GET.toString().equals(method)) {
            return true;
        }
        validateAccessToken(request);
        return true;
    }

    private void validateAccessToken(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        jwtTokenProvider.isValidToken(accessToken);
    }
}
