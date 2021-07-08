package com.backjoongwon.cvi.post.ui;

import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.post.application.PostService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<PostResponse> create(@PathVariable Long userId, @RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.create(userId, postRequest);
        return ResponseEntity.created(URI.create("/api/v1/posts/" + postResponse.getId())).body(postResponse);
    }
}
