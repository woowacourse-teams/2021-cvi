package com.backjoongwon.cvi.post.controller;

import com.backjoongwon.cvi.post.domain.SearchType;
import com.backjoongwon.cvi.post.service.PostService;
import com.backjoongwon.cvi.post.domain.Sort;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.auth.AuthenticationPrincipal;
import com.backjoongwon.cvi.user.domain.User;
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
    public List<PostResponse> findByVaccineTypeAndPaging(@RequestParam(defaultValue = "ALL") VaccinationType vaccinationType,
                                                         @RequestParam(defaultValue = "0") int offset,
                                                         @RequestParam(defaultValue = "6") int size,
                                                         @RequestParam(defaultValue = "CREATED_AT_DESC") Sort sort,
                                                         @RequestParam(defaultValue = "500") int fromHoursBefore,
                                                         @AuthenticationPrincipal Optional<User> user) {
        return postService.findByVaccineType(vaccinationType, offset, size, sort, fromHoursBefore, user);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> searchByTypesAndQuery(@RequestParam(defaultValue = "ALL") VaccinationType vaccinationType,
                                                    @RequestParam(defaultValue = "CONTENT") SearchType searchType,
                                                    @RequestParam String q,
                                                    @AuthenticationPrincipal Optional<User> user) {
        return postService.searchByTypesAndQuery(vaccinationType, searchType, q, user);
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
