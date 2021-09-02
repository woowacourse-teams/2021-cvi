package com.backjoongwon.cvi.comment.service;

import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.user.domain.User;
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
        log.info("인증되지 않음 사용자입니다. 입력값: null");
        throw new UnAuthorizedException("인증되지 않은 사용자입니다. 입력값: null");
    }

    private Post findPostWithCommentsById(Long postId) {
        validateNotNull(postId);
        Optional<Post> post = postRepository.findWithCommentsByPostId(postId);
        if (post.isPresent()) {
            return post.get();
        }
        log.info("해당 id의 게시글이 존재하지 않습니다. 입력값: {}", postId);
        throw new NotFoundException(String.format("해당 id의 게시글이 존재하지 않습니다. 입력값: %s", postId));
    }

    private void validateNotNull(Long id) {
        if (Objects.isNull(id)) {
            log.info("id는 null이 될 수 없습니다. 입력값: null");
            throw new NotFoundException("id는 null이 될 수 없습니다. 입력값: null");
        }
    }
}
