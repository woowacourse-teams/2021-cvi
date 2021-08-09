package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.*;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.post.dto.PostWithCommentResponse;
import com.backjoongwon.cvi.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

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
        return PostResponse.of(post, optionalUser.orElse(null));
    }

    public List<PostResponse> findByVaccineType(VaccinationType vaccinationType, Optional<User> optionalUser) {
        List<Post> posts = postRepository.findByVaccineType(vaccinationType);
        return PostResponse.toList(posts, optionalUser.orElse(null));
    }

    public List<PostResponse> findByVaccineType(VaccinationType vaccinationType, int offset, int size, Sort sort, int hours, Optional<User> optionalUser) {
        List<Post> posts = postRepository.findByVaccineType(vaccinationType, offset, size, Sort.toOrderSpecifier(sort), hours);
        return PostResponse.toList(posts, optionalUser.orElse(null));
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

    private Post findPostByPostId(Long id) {
        validateNotNull(id);
        return postRepository.findById(id)
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

    public List<PostWithCommentResponse> findByUserAndFilter(Optional<User> optionalUser, Filter filter) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        return createPostsResponseByFilter(filter, user);
    }

    private List<PostWithCommentResponse> createPostsResponseByFilter(Filter filter, User user) {
        if (filter == Filter.LIKES) {
            return createResponsesFilteredByLikes(user);
        }
        if (filter == Filter.COMMENTS) {
            return createResponsesFilteredByComments(user);
        }
        List<Post> posts = postRepository.findByUserId(user.getId());
        return PostWithCommentResponse.toList(posts, user);
    }

    private List<PostWithCommentResponse> createResponsesFilteredByLikes(User user) {
        List<Like> likes = likeRepository.findByUserId(user.getId());
        List<Post> posts = likes.stream()
                .map(Like::getPost)
                .collect(Collectors.toList());
        return PostWithCommentResponse.toList(posts, user);
    }

    private List<PostWithCommentResponse> createResponsesFilteredByComments(User user) {
        List<Comment> comments = commentRepository.findByUserId(user.getId());
        List<Post> posts = comments.stream()
                .map(Comment::getPost)
                .distinct()
                .collect(Collectors.toList());
        return PostWithCommentResponse.toList(posts, user);
    }

    public List<PostWithCommentResponse> findByUserAndFilter(Filter filter, int offset, int size, Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        if (filter == Filter.LIKES) {
            return createResponsesFilteredByLikes(user, offset, size);
        }
        if (filter == Filter.COMMENTS) {
            return createResponsesFilteredByComments(user, offset, size);
        }
        List<Post> posts = postRepository.findByUserId(user.getId(), offset, size);
        return PostWithCommentResponse.toList(posts, user);
    }

    private List<PostWithCommentResponse> createResponsesFilteredByLikes(User user, int offset, int size) {
        List<Like> likes = likeRepository.findByUserId(user.getId(), offset, size);
        List<Post> posts = likes.stream()
                .map(Like::getPost)
                .collect(Collectors.toList());
        return PostWithCommentResponse.toList(posts, user);
    }

    private List<PostWithCommentResponse> createResponsesFilteredByComments(User user, int offset, int size) {
        List<Comment> comments = commentRepository.findByUserId(user.getId(), offset, size);
        List<Post> posts = comments.stream()
                .map(Comment::getPost)
                .distinct()
                .collect(Collectors.toList());
        return PostWithCommentResponse.toList(posts, user);
    }
}
