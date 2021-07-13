package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.SocialProvider;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@DisplayName("POST 비지니스 흐름 테스트")
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private EntityManager em;

    private User user;
    private PostRequest postRequest;

    @BeforeEach
    void init() {
        user = User.builder()
                .nickname("테스트 유저")
                .ageRange(AgeRange.FORTIES)
                .socialProfileUrl("")
                .socialProvider(SocialProvider.NAVER)
                .build();
        postRequest = new PostRequest("Test Content", VaccinationType.PFIZER);
        userRepository.save(user);
    }

    @AfterEach
    void clear() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("게시글 생성 - 성공")
    @Test
    void create() {
        //given
        //when
        PostResponse postResponse = postService.create(user.getId(), postRequest);
        Post post = postRepository.findById(postResponse.getId()).get();
        //then
        assertThat(postResponse.getUser().getId()).isEqualTo(user.getId());
        assertThat(postResponse.getContent()).isEqualTo(postRequest.getContent());
        assertThat(post).isNotNull();
        assertThat(post.getUser()).isNotNull();
    }

    @DisplayName("게시글 생성 - 실패 - 존재하지 않는 유저")
    @Test
    void createFailureWhenWriterIsNull() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.create(0L, postRequest))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 단일 조회 - 성공")
    @Test
    void findById() {
        //given
        Post post = postRepository.save(Post.builder()
                .content("Test Content")
                .user(user)
                .vaccinationType(VaccinationType.MODERNA)
                .build());
        //when
        PostResponse response = postService.findById(post.getId());
        //then
        assertThat(response.getId()).isEqualTo(post.getId());
    }

    @DisplayName("게시글 단일 조회 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void findByIdFailureWhenPostIsNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.findById(0L))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 전체 조회 - 성공")
    @Test
    void findAll() {
        //given
        postRepository.save(Post.builder()
                .content("Test Content")
                .user(user)
                .vaccinationType(VaccinationType.MODERNA)
                .build());
        //when
        List<PostResponse> response = postService.findAll();
        //then
        assertThat(response).hasSize(1);
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void update() {
        //given
        PostRequest changeRequest = new PostRequest("change content", postRequest.getVaccinationType());
        Post post = postRepository.save(Post.builder()
                .content("Test Content")
                .user(user)
                .vaccinationType(VaccinationType.MODERNA)
                .build());
        //when
        postService.update(user.getId(), post.getId(), changeRequest);
        Post changedPost = postRepository.findById(post.getId()).get();
        //then
        assertThat(changedPost.getContent()).isEqualTo(changeRequest.getContent());
    }

    @DisplayName("게시글 수정 - 실패 - 찾을 수 없는 게시글")
    @Test
    void updateFailure() {
        //given
        PostRequest changeContent = new PostRequest("change content", postRequest.getVaccinationType());
        //when
        //then
        assertThatThrownBy(() -> postService.update(user.getId(), 0L, changeContent))
                .isExactlyInstanceOf(NotFoundException.class);
    }
}