package com.backjoongwon.cvi.like.service;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.service.PostService;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.LikeResponse;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@DisplayName("좋아요 비즈니스 흐름 테스트")
public class LikeServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private LikeService likeService;

    private User userWithoutLike;
    private User userWithLike;
    private Optional<User> optionalUserWithoutLike;
    private Optional<User> optionalUserWithLike;
    private Optional<User> optionalUserNotSignedIn;
    private Post post;

    @BeforeEach
    void init() {
        initUsers();
        initPost();
        initLike();
    }

    private void initUsers() {
        userWithoutLike = User.builder()
                .nickname("테스트유저")
                .ageRange(AgeRange.FORTIES)
                .socialProvider(SocialProvider.NAVER)
                .socialId("NAVER_ID")
                .profileUrl("naver.com/profile")
                .build();
        userWithLike = User.builder()
                .nickname("다른유저")
                .ageRange(AgeRange.FORTIES)
                .socialProvider(SocialProvider.NAVER)
                .socialId("NAVER_ID")
                .profileUrl("naver.com/profile")
                .build();

        optionalUserWithoutLike = Optional.of(userWithoutLike);
        optionalUserWithLike = Optional.of(userWithLike);
        optionalUserNotSignedIn = Optional.empty();
        userRepository.saveAll(Arrays.asList(userWithoutLike, userWithLike));
    }

    private void initPost() {
        post = Post.builder()
                .content("테스트게시글")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(userWithLike)
                .createdAt(LocalDateTime.now())
                .build();
        postRepository.save(post);
    }

    private void initLike() {
        post.addLike(Like.builder().user(userWithLike).build());
    }

    @DisplayName("좋아요 생성 - 성공")
    @Test
    void createLike() {
        //given
        //when
        Long postId = post.getId();
        likeService.createLike(postId, optionalUserWithoutLike);
        Post post = postRepository.findWithLikesByPostId(postId).get();
        //then
        assertThat(post.getLikesCount()).isEqualTo(2);
    }

    @DisplayName("좋아요 생성 - 실패 - 게시글이 없는 경우")
    @Test
    void createLikeFailureWhenPostNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> likeService.createLike(Long.MAX_VALUE, optionalUserWithoutLike))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("좋아요 생성 - 실패 - 동일한 유저가 이미 좋아요를 누른 경우")
    @Test
    void createLikeFailureWhenAlreadyCreatedBySameUser() {
        //given
        //when
        //then
        assertThatThrownBy(() -> likeService.createLike(post.getId(), optionalUserWithLike))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("좋아요 생성 - 실패 - 비회원 좋아요 생성 시도")
    @Test
    void createCommentWhenNotLoginUser() {
        //given
        //when
        //then
        assertThatThrownBy(() -> likeService.createLike(post.getId(), optionalUserNotSignedIn))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("좋아요 삭제 - 성공")
    @Test
    void deleteLike() {
        //given
        //when
        likeService.deleteLike(post.getId(), optionalUserWithLike);
        Post actualPost = postRepository.findWithLikesByPostId(post.getId())
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        //then
        assertThat(actualPost.getLikes().getLikes()).isEmpty();
    }

    @DisplayName("좋아요 삭제 - 실패 - 좋아요를 누르지 않은 글에 좋아요를 삭제 요청한 경우")
    @Test
    void deleteLikeFailureWhenInvalidToken() {
        //given
        //when
        //then
        assertThatThrownBy(() -> likeService.deleteLike(post.getId(), optionalUserWithoutLike))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessage("해당 사용자의 좋아요가 글에 존재하지 않습니다.");
    }

    @DisplayName("좋아요 삭제 - 실패 - 삭제 요청한 좋아요의 게시글이 없는 경우")
    @Test
    void deleteLikeFailureWhenLikeNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> likeService.deleteLike(Long.MAX_VALUE, optionalUserWithoutLike))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 id의 게시글이 존재하지 않습니다.");
    }

    @DisplayName("좋아요 누른 유저 hasLiked 상태값(true) 확인 - 성공")
    @Test
    void hasLiked() {
        //given
        //when
        PostResponse postResponse = postService.findById(post.getId(), optionalUserWithLike);
        //then
        assertThat(postResponse.isHasLiked()).isTrue();
    }

    @DisplayName("좋아요 누르지 않은 유저 유저 hasLiked 상태값(false) 확인 - 성공")
    @Test
    void notHasLiked() {
        //given
        //when
        PostResponse postResponse = postService.findById(post.getId(), optionalUserWithoutLike);
        //then
        assertThat(postResponse.isHasLiked()).isFalse();
    }

    @DisplayName("게시글 삭제시 좋아요 삭제 - 성공")
    @Test
    void deleteLikeWhenDeletePost() {
        //given
        LikeResponse likeResponse = likeService.createLike(post.getId(), optionalUserWithoutLike);
        //when
        postService.delete(post.getId(), optionalUserWithLike);
        //then
        assertThat(likeRepository.findById(likeResponse.getId())).isEmpty();
    }
}
