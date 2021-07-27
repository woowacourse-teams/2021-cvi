package com.backjoongwon.cvi.like.ui;


import com.backjoongwon.cvi.like.application.LikeService;
import com.backjoongwon.cvi.user.auth.AuthenticationPrincipal;
import com.backjoongwon.cvi.user.domain.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @DeleteMapping("/api/v1/likes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable Long id, @AuthenticationPrincipal RequestUser user) {
        likeService.delete(id, user);
    }
}
