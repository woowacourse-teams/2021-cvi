package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
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

    @Transactional
    public PostResponse create(Long userId, PostRequest postRequest) {
        User user = findUserByUserId(userId);
        Post post = postRequest.toEntity();
        post.assignUser(user);
        postRepository.save(post);
        return PostResponse.of(post);
    }

    @Transactional
    public PostResponse findById(Long id) {
        Post post = findPostByPostId(id);
        post.increaseViewCount();
        return PostResponse.of(post);
    }

    public List<PostResponse> findByVaccineType(VaccinationType vaccinationType) {
        List<Post> posts = postRepository.findByVaccineType(vaccinationType);
        return PostResponse.of(posts);
    }

    @Transactional
    public void update(Long postId, Long userId, PostRequest postRequest) {
        User user = findUserByUserId(userId);
        Post post = findPostByPostId(postId);

        post.update(postRequest.toEntity(), user);
    }

    @Transactional
    public void delete(Long postId, Long userId) {
        User user = findUserByUserId(userId);
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
}
