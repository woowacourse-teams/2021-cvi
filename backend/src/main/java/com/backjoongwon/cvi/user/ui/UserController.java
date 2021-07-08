package com.backjoongwon.cvi.user.ui;

import com.backjoongwon.cvi.user.application.UserService;
import com.backjoongwon.cvi.user.dto.UserRequest;
import com.backjoongwon.cvi.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.signup(userRequest);
        return ResponseEntity.created(URI.create("/api/v1/users/" + userResponse.getId())).body(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> find(@PathVariable Long id) {
        UserResponse userResponse = userService.findById(id);
        return ResponseEntity.ok().body(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        userService.updateById(id, userRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

