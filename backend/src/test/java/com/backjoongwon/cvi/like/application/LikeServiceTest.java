package com.backjoongwon.cvi.like.application;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.like.dto.LikeResponse;
import com.backjoongwon.cvi.post.application.PostService;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.RequestUser;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("게시글 좋아요 비즈니스 흐름 테스트")
class LikeServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private User user;
    private User anotherUser;
    private Post post;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .nickname("테스트유저")
                .ageRange(AgeRange.FORTIES)
                .profileUrl("")
                .socialProvider(SocialProvider.NAVER)
                .build();
        anotherUser = User.builder()
                .nickname("다른_유저")
                .ageRange(AgeRange.TWENTIES)
                .profileUrl("")
                .socialProvider(SocialProvider.KAKAO)
                .build();
        userRepository.save(user);
        userRepository.save(anotherUser);
        post = Post.builder()
                .content("Test Content111")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        postRepository.save(post);
        postService.createLike(post.getId(), RequestUser.of(user.getId()));
        em.flush();
        em.clear();
        em.close();
    }

    @DisplayName("게시글 좋아요 생성 - 성공")
    @Test
    void createLike() {
        //given
        //when
        LikeResponse like = postService.createLike(post.getId(), RequestUser.of(anotherUser.getId()));

        em.flush();
        em.clear();
        em.close();
        //then
        Post post = getPost();
        assertThat(post.getLikesCount()).isEqualTo(2);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 게시글이 없는 경우")
    @Test
    void createLikeFailureWhenPostNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createLike(post.getId() + 1L, RequestUser.of(anotherUser.getId())))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 동일한 유저가 이미 좋아요를 누른 경우")
    @Test
    void createLikeFailureWhenAlreadyCreatedBySameUser() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createLike(post.getId(), RequestUser.of(user.getId())))
                .isInstanceOf(InvalidOperationException.class);
    }

    private Post getPost() {
        return postRepository.findWithLikesById(post.getId())
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }
}