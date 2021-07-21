package com.backjoongwon.cvi.auth.ui;

import com.backjoongwon.cvi.auth.application.AuthService;
import com.backjoongwon.cvi.auth.dto.AuthRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse signup(@RequestBody AuthRequest authRequest, HttpServletResponse servletResponse) {
        return authService.authorize(authRequest);
    }
}
