package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.dto.LikeResponse;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.domain.RequestUser;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public PostResponse create(Long userId, PostRequest postRequest) {
        User writer = findUserByUserId(userId);
        Post post = postRequest.toEntity();
        post.assignUser(writer);
        postRepository.save(post);
        return PostResponse.of(post, writer);
    }

    @Transactional
    public PostResponse findById(Long id, RequestUser user) {
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

    private User findUserByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 사용자가 존재하지 않습니다."));
    }

    private Post findPostByPostId(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    @Transactional
    public LikeResponse createLike(Long postId, RequestUser requestUser) {
        User user = findUserByUserId(requestUser.getId());
        Post post = postRepository.findWithLikesById(postId)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        Like like = Like.builder()
                .user(user)
                .build();
        post.addLike(like);
        likeRepository.flush();
        return new LikeResponse(like.getId(), PostResponse.of(post, user));
    }

    @Transactional
    public void deleteLike(Long postId, Long likeId, RequestUser user) {
        user.validateSignedin();
        Post post = postRepository.findWithLikesById(postId)
                        .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        post.removeLike(likeId, user.getId());
    }
}
