package com.backjoongwon.cvi.user.auth;

import com.backjoongwon.cvi.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
public class SigninInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        validateRequest(request);
        return true;
    }

    private void validateAccessToken(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        userService.validateAccessToken(accessToken);
    }

    private void validateRequest(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        if (url.contains("/api/v1/users")) {
            validateUserRequest(request);
        }
        if (url.contains("/api/v1/posts")) {
            validatePostRequest(request);
        }
    }

    private void validateUserRequest(HttpServletRequest request) {
        String method = request.getMethod();
        if (method.equals("PUT") || method.equals("DELETE")) {
            validateAccessToken(request);
        }
    }

    private void validatePostRequest(HttpServletRequest request) {
        String method = request.getMethod();
        if (!method.equals("GET")) {
            validateAccessToken(request);
        }
    }
}
