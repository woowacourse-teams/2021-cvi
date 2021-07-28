package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.domain.RequestUser;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public PostResponse create(Long userId, PostRequest postRequest) {
        User writer = findUserByUserId(userId);
        Post post = postRequest.toEntity();
        post.assignUser(writer);
        postRepository.save(post);
        return PostResponse.of(post, writer);
    }

    @Transactional
    public PostResponse findById(Long id) {
        Post post = findPostByPostId(id);
        post.increaseViewCount();
        return PostResponse.of(post, null);
    }

    public List<PostResponse> findByVaccineType(VaccinationType vaccinationType) {
        List<Post> posts = postRepository.findByVaccineType(vaccinationType);
        return PostResponse.of(posts, null);
    }

    @Transactional
    public void update(Long postId, RequestUser requestUser, PostRequest postRequest) {
        requestUser.validateSignedin();
        User user = findUserByUserId(requestUser.getId());
        Post post = findPostByPostId(postId);

        post.update(postRequest.toEntity(), user);
    }

    @Transactional
    public void delete(Long postId, RequestUser requestUser) {
        User user = findUserByUserId(requestUser.getId());
        Post foundPost = findPostByPostId(postId);
        foundPost.validateAuthor(user);
        postRepository.deleteById(postId);
    }

    @Transactional
    public CommentResponse createComment(Long postId, RequestUser user, CommentRequest commentRequest) {
        User foundUser = findUserByUserId(user.getId());
        Post foundPost = findPostWithCommentsByPostId(postId);

        Comment comment = commentRequest.toEntity();
        comment.assignUser(foundUser);

        foundPost.addComment(comment);
        postRepository.flush();
        return CommentResponse.of(comment);
    }

    @Transactional
    public void updateComment(Long postId, Long commentId, RequestUser requestUser, CommentRequest updateRequest) {
        User foundUser = findUserByUserId(requestUser.getId());
        Post foundPost = findPostWithCommentsByPostId(postId);
        foundPost.updateComment(commentId, updateRequest.toEntity(), foundUser);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, RequestUser requestUser) {
        User foundUser = findUserByUserId(requestUser.getId());
        Post foundPost = findPostWithCommentsByPostId(postId);
        foundPost.deleteComment(commentId, foundUser);
    }

    private User findUserByUserId(Long id) {
        validateNull(id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                            log.info("해당 id의 사용자가 존재하지 않습니다.");
                            return new NotFoundException("해당 id의 사용자가 존재하지 않습니다.");
                        }
                );
    }

    private Post findPostByPostId(Long id) {
        validateNull(id);
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    private Post findPostWithCommentsByPostId(Long id) {
        validateNull(id);
        return postRepository.findWithCommentsById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    private void validateNull(Long id) {
        if (Objects.isNull(id)) {
            log.info("id는 null이 될 수 없습니다.");
            throw new NotFoundException("id는 null이 될 수 없습니다.");
        }
    }
}
