package com.cvi.service;

import com.cvi.comment.domain.model.Comment;
import com.cvi.comment.domain.repository.CommentRepository;
import com.cvi.dto.CommentRequest;
import com.cvi.dto.CommentResponse;
import com.cvi.exception.NotFoundException;
import com.cvi.exception.UnAuthorizedException;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.repository.PostRepository;
import com.cvi.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse createComment(Long postId, Optional<User> optionalUser, CommentRequest commentRequest) {
        User user = getLoginUser(optionalUser);
        Post post = findPostWithCommentsById(postId);
        Comment comment = commentRequest.toEntity();
        comment.assignUser(user);
        post.assignComment(comment);
        commentRepository.flush();
        return CommentResponse.of(comment);
    }

    public List<CommentResponse> findCommentsByPostId(Long postId) {
        Post post = findPostWithCommentsById(postId);
        return CommentResponse.toList(post.getCommentsAsList());
    }

    public List<CommentResponse> findCommentsByPostId(Long postId, int offset, int size) {
        Post post = findPostWithCommentsById(postId);
        return CommentResponse.toList(post.sliceCommentsAsList(offset, size));
    }

    @Transactional
    public void updateComment(Long postId, Long commentId, Optional<User> optionalUser, CommentRequest updateRequest) {
        User user = getLoginUser(optionalUser);
        Post post = findPostWithCommentsById(postId);
        post.updateComment(commentId, updateRequest.toEntity(), user);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, Optional<User> optionalUser) {
        User user = getLoginUser(optionalUser);
        Post post = findPostWithCommentsById(postId);
        post.deleteComment(commentId, user);
    }

    private User getLoginUser(Optional<User> optionalUser) {
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        log.info("???????????? ?????? ??????????????????. ?????????: null");
        throw new UnAuthorizedException("???????????? ?????? ??????????????????. ?????????: null");
    }

    private Post findPostWithCommentsById(Long postId) {
        validateNotNull(postId);
        Optional<Post> post = postRepository.findWithCommentsByPostId(postId);
        if (post.isPresent()) {
            return post.get();
        }
        log.info("?????? id??? ???????????? ???????????? ????????????. ?????????: {}", postId);
        throw new NotFoundException(String.format("?????? id??? ???????????? ???????????? ????????????. ?????????: %s", postId));
    }

    private void validateNotNull(Long id) {
        if (Objects.isNull(id)) {
            log.info("id??? null??? ??? ??? ????????????. ?????????: null");
            throw new NotFoundException("id??? null??? ??? ??? ????????????. ?????????: null");
        }
    }
}
