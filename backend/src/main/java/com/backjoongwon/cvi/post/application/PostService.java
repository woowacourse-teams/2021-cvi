package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.LikeResponse;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
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
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse create(Optional<User> optionalUser, PostRequest postRequest) {
        User writer = validateSignedinAndGetUser(optionalUser);
        Post post = postRequest.toEntity();
        post.assignUser(writer);
        postRepository.save(post);
        return PostResponse.of(post, writer);
    }

    @Transactional
    public PostResponse findById(Long id, Optional<User> optionalUser) {
        Post post = findPostByPostId(id);
        post.increaseViewCount();
        if (optionalUser.isPresent()) {
            return PostResponse.of(post, optionalUser.get());
        }
        return PostResponse.of(post, null);
    }

    public List<PostResponse> findByVaccineType(VaccinationType vaccinationType, Optional<User> optionalUser) {
        List<Post> posts = postRepository.findByVaccineType(vaccinationType);
        if (optionalUser.isPresent()) {
            return PostResponse.of(posts, optionalUser.get());
        }
        return PostResponse.of(posts, null);
    }

    @Transactional
    public void update(Long postId, Optional<User> optionalUser, PostRequest postRequest) {
        User user = validateSignedinAndGetUser(optionalUser);
        Post post = findPostByPostId(postId);
        post.update(postRequest.toEntity(), user);
    }

    @Transactional
    public void delete(Long postId, Optional<User> optionalUser) {
        User user = validateSignedinAndGetUser(optionalUser);
        Post post = findPostByPostId(postId);
        post.validateAuthor(user);
        postRepository.deleteById(postId);
    }

    @Transactional
    public CommentResponse createComment(Long postId, Optional<User> optionalUser, CommentRequest commentRequest) {
        User user = validateSignedinAndGetUser(optionalUser);
        Post post = findPostWithCommentsByPostId(postId);

        Comment comment = commentRequest.toEntity();
        comment.assignUser(user);

        post.assignComment(comment);
        postRepository.flush();
        return CommentResponse.of(comment);
    }

    @Transactional
    public void updateComment(Long postId, Long commentId, Optional<User> optionalUser, CommentRequest updateRequest) {
        User user = validateSignedinAndGetUser(optionalUser);
        Post post = findPostWithCommentsByPostId(postId);
        post.updateComment(commentId, updateRequest.toEntity(), user);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, Optional<User> optionalUser) {
        User user = validateSignedinAndGetUser(optionalUser);
        Post post = findPostWithCommentsByPostId(postId);
        post.deleteComment(commentId, user);
    }

    private Post findPostByPostId(Long id) {
        validateNotNull(id);
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    @Transactional
    public LikeResponse createLike(Long postId, Optional<User> optionalUser) {
        User user = validateSignedinAndGetUser(optionalUser);
        Post post = findPostWithLikesById(postId);
        Like like = Like.builder()
                .user(user)
                .build();
        post.addLike(like);
        postRepository.flush();
        return LikeResponse.from(like.getId());
    }

    @Transactional
    public void deleteLike(Long postId, Optional<User> optionalUser) {
        User user = validateSignedinAndGetUser(optionalUser);
        Post post = findPostWithLikesById(postId);
        post.deleteLike(user.getId());
    }

    private Post findPostWithLikesById(Long postId) {
        return postRepository.findWithLikesById(postId)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    private Post findPostWithCommentsByPostId(Long id) {
        validateNotNull(id);
        return postRepository.findWithCommentsById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    private User validateSignedinAndGetUser(Optional<User> user) {
        return user.orElseThrow(() -> new UnAuthorizedException("인증되지 않은 사용자입니다."));
    }

    private void validateNotNull(Long id) {
        if (Objects.isNull(id)) {
            log.info("id는 null이 될 수 없습니다.");
            throw new NotFoundException("id는 null이 될 수 없습니다.");
        }
    }
//
//    public List<PostResponse> findByUserAndFilter(RequestUser requestUser, Filter filter) {
//        requestUser.validateSignedin();
//        User foundUser = findUserByUserId(requestUser.getId());
//        if (filter == Filter.LIKES) {
//            return null;
//        }
//        if (filter == Filter.COMMENTS) {
//            return null;
//        }
//        List<Post> posts = postRepository.findByUser(foundUser);
//        return PostResponse.of(posts, foundUser);
//    }
}
