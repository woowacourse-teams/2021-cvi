package com.cvi.controller;

import com.cvi.auth.AuthenticationPrincipal;
import com.cvi.dto.PostRequest;
import com.cvi.dto.PostResponse;
import com.cvi.dto.PostsFindRequest;
import com.cvi.post.domain.model.Sort;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.service.post.PostService;
import com.cvi.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@AuthenticationPrincipal Optional<User> user, @RequestBody @Valid PostRequest postRequest, HttpServletResponse servletResponse) {
        PostResponse postResponse = postService.create(user, postRequest);
        servletResponse.setHeader("Location", "/api/v1/posts/" + postResponse.getId());
        return postResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> findByVaccineType(@RequestParam(defaultValue = "ALL") VaccinationType vaccinationType, @AuthenticationPrincipal Optional<User> user) {
        return postService.findByVaccineType(vaccinationType, user);
    }

    @GetMapping("/paging")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> findByVaccineTypeAndPaging(@RequestBody @Valid PostsFindRequest postsFindRequest,
                                                         @AuthenticationPrincipal Optional<User> user) {
        return postService.findByVaccineType(postsFindRequest, user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse find(@PathVariable Long id, @AuthenticationPrincipal Optional<User> user) {
        return postService.findById(id, user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @AuthenticationPrincipal Optional<User> user, @RequestBody @Valid PostRequest postRequest) {
        postService.update(id, user, postRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @AuthenticationPrincipal Optional<User> user) {
        postService.delete(id, user);
    }
}
