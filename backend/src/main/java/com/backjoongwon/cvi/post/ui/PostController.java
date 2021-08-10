package com.backjoongwon.cvi.post.ui;

import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.post.application.PostService;
import com.backjoongwon.cvi.post.domain.Sort;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.LikeResponse;
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
                                                         @RequestParam(defaultValue = "#{T(java.lang.String).valueOf(T(java.lang.Integer).MAX_VALUE)}") int fromHoursBefore,
                                                         @AuthenticationPrincipal Optional<User> user) {
        return postService.findByVaccineType(vaccinationType, offset, size, sort, fromHoursBefore, user);
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

    @PostMapping("/{postId}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLike(@PathVariable Long postId, @AuthenticationPrincipal Optional<User> user, HttpServletResponse servletResponse) {
        LikeResponse likeResponse = postService.createLike(postId, user);
        servletResponse.setHeader("Location", "/api/v1/likes/" + likeResponse.getId());
    }

    @DeleteMapping("/{postId}/likes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable Long postId, @AuthenticationPrincipal Optional<User> user) {
        postService.deleteLike(postId, user);
    }

    @PostMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(@PathVariable Long postId,
                                         @AuthenticationPrincipal Optional<User> user,
                                         @RequestBody @Valid CommentRequest commentRequest,
                                         HttpServletResponse servletResponse) {
        CommentResponse commentResponse = postService.createComment(postId, user, commentRequest);
        servletResponse.setHeader("Location", "/api/v1/comments/" + commentResponse.getId());
        return commentResponse;
    }

    @GetMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> findCommentsOfPost(@PathVariable Long postId) {
        return postService.findCommentsById(postId);
    }

    @GetMapping("/{postId}/comments/paging")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> findCommentsOfPost(@PathVariable Long postId, @RequestParam(defaultValue = "0") int offset,
                                                    @RequestParam(defaultValue = "6") int size) {
        return postService.findCommentsById(postId, offset, size);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              @AuthenticationPrincipal Optional<User> user,
                              @RequestBody @Valid CommentRequest commentRequest) {
        postService.updateComment(postId, commentId, user, commentRequest);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              @AuthenticationPrincipal Optional<User> user) {
        postService.deleteComment(postId, commentId, user);
    }
}
