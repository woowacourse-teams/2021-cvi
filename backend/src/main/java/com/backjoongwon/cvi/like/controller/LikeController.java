package com.backjoongwon.cvi.like.controller;

import com.backjoongwon.cvi.like.service.LikeService;
import com.backjoongwon.cvi.post.dto.LikeResponse;
import com.backjoongwon.cvi.user.auth.AuthenticationPrincipal;
import com.backjoongwon.cvi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createLike(@PathVariable Long postId, @AuthenticationPrincipal Optional<User> user, HttpServletResponse servletResponse) {
        LikeResponse likeResponse = likeService.createLike(postId, user);
        servletResponse.setHeader("Location", "/api/v1/likes/" + likeResponse.getId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable Long postId, @AuthenticationPrincipal Optional<User> user) {
        likeService.deleteLike(postId, user);
    }
}
