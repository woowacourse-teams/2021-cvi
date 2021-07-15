package com.backjoongwon.cvi.post.ui;

import com.backjoongwon.cvi.post.application.PostService;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.auth.SigninUser;
import com.backjoongwon.cvi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@SigninUser User user, @RequestBody PostRequest postRequest, HttpServletResponse servletResponse) {
        PostResponse postResponse = postService.create(user.getId(), postRequest);
        servletResponse.setHeader("Location", "/api/v1/posts/" + postResponse.getId());
        return postResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> findAll() {
        return postService.findAll();
    }

    @GetMapping("/reviews")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> findByVaccineType(@RequestParam VaccinationType vaccinationType) {
        return postService.findByVaccineType(vaccinationType);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse find(@PathVariable Long postId) {
        return postService.findById(postId);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long postId, @SigninUser User user, @RequestBody PostRequest postRequest) {
        postService.update(postId, user.getId(), postRequest);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long postId, @SigninUser User user) {
        postService.delete(postId, user.getId());
    }
}
