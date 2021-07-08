package com.backjoongwon.cvi.post.ui;

import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.post.application.PostService;
import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable Long userId, @RequestBody PostRequest postRequest, HttpServletResponse servletResponse) {
        PostResponse postResponse = postService.create(userId, postRequest);
        servletResponse.setHeader("Location", "/api/v1/posts/" + postResponse.getId());
    }

    @PutMapping("/{postId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long postId, @PathVariable Long userId, @RequestBody PostRequest postRequest) {
        postService.update(userId, postId, postRequest);
    }

    @DeleteMapping("/{postId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long postId, @PathVariable Long userId){
        postService.delete(postId, userId);
    }
}