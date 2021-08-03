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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse create(Optional<User> optionalUser, PostRequest postRequest) {
        validateSignedin(optionalUser);
        User writer = optionalUser.get();
        Post post = postRequest.toEntity();
        post.assignUser(writer);
        postRepository.save(post);
        return PostResponse.of(post, writer);
    }

    @Transactional
    public PostResponse findById(Long id, Optional<User> optionalUser) {
        Post post = findPostByPostId(id);
        post.increaseViewCount();
        return createPostResponse(optionalUser, post);
    }

    private PostResponse createPostResponse(Optional<User> optionalUser, Post post) {
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

    public List<PostResponse> findByVaccineType(VaccinationType vaccinationType, Long lastPostId, int size, Optional<User> optionalUser) {
        return postRepository.findByVaccineType(vaccinationType, lastPostId, size)
                .stream()
                .map(post -> PostResponse.from(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, Optional<User> optionalUser, PostRequest postRequest) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = findPostByPostId(id);
        post.update(postRequest.toEntity(), user);
    }

    @Transactional
    public void delete(Long id, Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = findPostByPostId(id);
        post.validateAuthor(user);
        postRepository.deleteById(id);
    }

    @Transactional
    public CommentResponse createComment(Long id, Optional<User> optionalUser, CommentRequest commentRequest) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = findPostWithCommentsById(id);

        Comment comment = commentRequest.toEntity();
        comment.assignUser(user);

        post.assignComment(comment);
        postRepository.flush();
        return CommentResponse.of(comment);
    }

    @Transactional
    public void updateComment(Long id, Long commentId, Optional<User> optionalUser, CommentRequest updateRequest) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = findPostWithCommentsById(id);
        post.updateComment(commentId, updateRequest.toEntity(), user);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = findPostWithCommentsById(postId);
        post.deleteComment(commentId, user);
    }

    private Post findPostByPostId(Long id) {
        validateNotNull(id);
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    @Transactional
    public LikeResponse createLike(Long id, Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = findPostWithLikesById(id);
        Like like = Like.builder()
                .user(user)
                .build();
        post.addLike(like);
        postRepository.flush();
        return LikeResponse.from(like.getId());
    }

    @Transactional
    public void deleteLike(Long id, Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = findPostWithLikesById(id);
        post.deleteLike(user.getId());
    }

    private Post findPostWithLikesById(Long id) {
        return postRepository.findWithLikesById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    private Post findPostWithCommentsById(Long id) {
        validateNotNull(id);
        return postRepository.findWithCommentsById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    private void validateSignedin(Optional<User> user) {
        if (!user.isPresent()) {
            throw new UnAuthorizedException("인증되지 않은 사용자입니다.");
        }
    }

    private void validateNotNull(Long id) {
        if (Objects.isNull(id)) {
            log.info("id는 null이 될 수 없습니다.");
            throw new NotFoundException("id는 null이 될 수 없습니다.");
        }
    }
}
