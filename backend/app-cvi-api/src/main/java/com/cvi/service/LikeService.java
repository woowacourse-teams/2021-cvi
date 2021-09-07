package com.cvi.service;

import com.cvi.dto.LikeResponse;
import com.cvi.exception.NotFoundException;
import com.cvi.exception.UnAuthorizedException;
import com.cvi.like.domain.model.Like;
import com.cvi.like.domain.repository.LikeRepository;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.repository.PostRepository;
import com.cvi.user.domain.model.User;
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
        User user = getLoginUser(optionalUser);
        Post post = findPostByPostId(postId);
        Like like = Like.builder()
                .user(user)
                .build();
        post.assignLike(like);
        likeRepository.flush();
        return LikeResponse.from(like.getId());
    }

    @Transactional
    public void deleteLike(Long postId, Optional<User> optionalUser) {
        User user = getLoginUser(optionalUser);
        Post post = findPostByPostId(postId);
        post.deleteLike(user.getId());
    }

    private User getLoginUser(Optional<User> optionalUser) {
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        log.info("인증되지 안흔 사용자입니다. 입력 값: null");
        throw new UnAuthorizedException("인증되지 않은 사용자입니다. 입력 값: null");
    }

    private Post findPostByPostId(Long postId) {
        Optional<Post> post = postRepository.findWithLikesByPostId(postId);
        if (post.isPresent()) {
            return post.get();
        }
        log.info("해당 id의 게시글이 존재하지 않습니다. 입력 값: {}", postId);
        throw new NotFoundException(String.format("해당 id의 게시글이 존재하지 않습니다. 입력 값: %s", postId));
    }
}
