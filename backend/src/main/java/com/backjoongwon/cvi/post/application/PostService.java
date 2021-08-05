package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.*;
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

    public List<PostResponse> findByVaccineType(VaccinationType vaccinationType, Long lastPostId, int size, Sort sort, Optional<User> optionalUser) {
        List<Post> posts = postRepository.findByVaccineType(vaccinationType, lastPostId, size, Sort.toOrderSpecifier(sort));
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

    public List<PostResponse> findByUserAndFilter(Optional<User> optionalUser, Filter filter) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        return createPostsResponseByFilter(filter, user);
    }

    private List<PostResponse> createPostsResponseByFilter(Filter filter, User user) {
        if (filter == Filter.LIKES) {
            return createResponsesFilteredByLikes(user);
        }
        if (filter == Filter.COMMENTS) {
            return createResponsesFilteredByComments(user);
        }
        List<Post> posts = postRepository.findByUser(user.getId());
        return PostResponse.toList(posts, user);
    }

    private List<PostResponse> createResponsesFilteredByComments(User user) {
        List<Comment> comments = commentRepository.findByUser(user.getId());
        List<Post> posts = comments.stream()
                .map(Comment::getPost)
                .distinct()
                .collect(Collectors.toList());
        return PostResponse.toList(posts, user);
    }

    private List<PostResponse> createResponsesFilteredByLikes(User user) {
        List<Like> likes = likeRepository.findByUser(user.getId());
        List<Post> posts = likes.stream()
                .map(Like::getPost)
                .collect(Collectors.toList());
        return PostResponse.toList(posts, user);
    }
}
