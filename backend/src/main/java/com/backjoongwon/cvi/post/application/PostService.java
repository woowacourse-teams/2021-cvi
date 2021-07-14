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
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostResponse create(Long userId, PostRequest postRequest) {
        User user = findUserById(userId);
        Post post = postRequest.toEntity();
        post.assignUser(user);
        postRepository.save(post);
        return PostResponse.of(post);
    }

    @Transactional
    public PostResponse findById(Long postId) {
        Post post = findPostById(postId);
        post.increaseViewCount();
        return PostResponse.of(post);
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long postId, Long userId, PostRequest postRequest) {
        User user = findUserById(userId);
        Post post = findPostById(postId);

        post.update(postRequest.toEntity(), user);
    }

    @Transactional
    public void delete(Long postId, Long userId) {
        User user = findUserById(userId);
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException("존재하지 않는 게시글입니다.");
        }
        postRepository.deleteById(postId);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
    }

    public List<PostResponse> findByVaccineType(VaccinationType vaccinationType) {
        List<Post> posts = postRepository.findByVaccineType(vaccinationType);
        return PostResponse.of(posts);
    }
}
