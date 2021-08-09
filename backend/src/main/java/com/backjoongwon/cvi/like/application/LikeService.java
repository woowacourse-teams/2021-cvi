package com.backjoongwon.cvi.like.application;

import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.dto.LikeResponse;
import com.backjoongwon.cvi.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public LikeResponse createLike(Long postId, Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = postRepository.findWithLikesById(postId)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        Like like = Like.builder()
                .user(user)
                .build();
        post.addLike(like);
        likeRepository.flush();
        return LikeResponse.from(like.getId());
    }

    public void deleteLike(Long postId, Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = postRepository.findWithLikesById(postId)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        post.deleteLike(user.getId());
    }

    private void validateSignedin(Optional<User> user) {
        if (!user.isPresent()) {
            throw new UnAuthorizedException("인증되지 않은 사용자입니다.");
        }
    }
}
