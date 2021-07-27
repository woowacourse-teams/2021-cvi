package com.backjoongwon.cvi.comment.ui;

import com.backjoongwon.cvi.comment.application.CommentService;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.user.auth.AuthenticationPrincipal;
import com.backjoongwon.cvi.user.domain.RequestUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable Long id,
                       @RequestBody CommentRequest commentRequest,
                       @AuthenticationPrincipal RequestUser requestUser) {
        commentService.update(id, commentRequest, requestUser);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @AuthenticationPrincipal RequestUser requestUser) {
        commentService.delete(id, requestUser);
    }
}
