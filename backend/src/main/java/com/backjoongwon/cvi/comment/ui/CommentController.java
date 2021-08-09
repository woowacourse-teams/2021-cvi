package com.backjoongwon.cvi.comment.ui;

import com.backjoongwon.cvi.comment.application.CommentService;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
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
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(@PathVariable Long postId,
                                         @AuthenticationPrincipal Optional<User> user,
                                         @RequestBody @Valid CommentRequest commentRequest,
                                         HttpServletResponse servletResponse) {
        CommentResponse commentResponse = commentService.createComment(postId, user, commentRequest);
        servletResponse.setHeader("Location", "/api/v1/comments/" + commentResponse.getId());
        return commentResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> findCommentsOfPost(@PathVariable Long postId) {
        return commentService.findCommentsByPostId(postId);
    }

    @GetMapping("/paging")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> findCommentsOfPost(@PathVariable Long postId,
                                                    @RequestParam(defaultValue = "0") int offset,
                                                    @RequestParam(defaultValue = "6") int size) {
        return commentService.findCommentsByPostId(postId, offset, size);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              @AuthenticationPrincipal Optional<User> user,
                              @RequestBody @Valid CommentRequest commentRequest) {
        commentService.updateComment(postId, commentId, user, commentRequest);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              @AuthenticationPrincipal Optional<User> user) {
        commentService.deleteComment(postId, commentId, user);
    }
}
