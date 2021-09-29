package com.cvi.controller;

import com.cvi.dto.AuthRequest;
import com.cvi.dto.UserResponse;
import com.cvi.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponse authenticate(@RequestBody @Valid AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response) {
        UserResponse userResponse = authService.authenticate(authRequest, request.getHeader("Origin"));
        response.setHeader("Authorization", userResponse.getAccessToken());
        return userResponse;
    }
}
