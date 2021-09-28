package com.cvi.controller;

import com.cvi.auth.AuthenticationPrincipal;
import com.cvi.dto.PostWithCommentResponse;
import com.cvi.dto.UserRequest;
import com.cvi.dto.UserResponse;
import com.cvi.post.domain.model.Filter;
import com.cvi.service.post.PostService;
import com.cvi.service.UserService;
import com.cvi.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse signup(@RequestBody @Valid UserRequest userRequest, HttpServletResponse servletResponse) {
        UserResponse userResponse = userService.signup(userRequest);
        servletResponse.setHeader("Location", "/api/v1/users/" + userResponse.getId());
        return userResponse;
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findMe(@AuthenticationPrincipal Optional<User> user) {
        return userService.findUser(user);
    }

    @GetMapping("/me/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostWithCommentResponse> findMyPosts(@RequestParam(defaultValue = "NONE") Filter filter, @AuthenticationPrincipal Optional<User> user) {
        return postService.findByUserAndFilter(user, filter);
    }

    @GetMapping("/me/posts/paging")
    @ResponseStatus(HttpStatus.OK)
    public List<PostWithCommentResponse> findMyPostsByPaging(@RequestParam(defaultValue = "NONE") Filter filter,
                                                             @RequestParam(defaultValue = "0") int offset,
                                                             @RequestParam(defaultValue = "6") int size,
                                                             @AuthenticationPrincipal Optional<User> user) {
        return postService.findByUserAndFilter(filter, offset, size, user);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal Optional<User> user, @RequestBody @Valid UserRequest userRequest) {
        userService.update(user, userRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse find(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal Optional<User> user) {
        userService.delete(user);
    }
}
