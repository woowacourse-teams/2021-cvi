package com.backjoongwon.cvi.user.ui;

import com.backjoongwon.cvi.user.application.UserService;
import com.backjoongwon.cvi.user.auth.SigninUser;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse signup(@RequestBody UserRequest userRequest, HttpServletResponse servletResponse) {
        UserResponse userResponse = userService.signup(userRequest);
        servletResponse.setHeader("Location", "/api/v1/users/" + userResponse.getId());
        return userResponse;
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findMe(@SigninUser User user) {
        return userService.findMeById(user.getId());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse find(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@SigninUser User user, @RequestBody UserRequest userRequest) {
        userService.update(user.getId(), userRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@SigninUser User user) {
        userService.delete(user.getId());
    }
}

