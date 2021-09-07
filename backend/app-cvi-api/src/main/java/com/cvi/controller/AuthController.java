package com.cvi.controller;

import com.cvi.dto.AuthRequest;
import com.cvi.dto.UserResponse;
import com.cvi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponse authenticate(@RequestBody @Valid AuthRequest authRequest, HttpServletResponse httpServletResponse) {
        UserResponse userResponse = authService.authenticate(authRequest);
        httpServletResponse.setHeader("Authorization", userResponse.getAccessToken());
        return userResponse;
    }
}
