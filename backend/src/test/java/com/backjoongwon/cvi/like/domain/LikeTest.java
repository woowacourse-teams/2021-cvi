package com.backjoongwon.cvi.like.domain;

import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("댓글 도메인 테스트")
public class LikeTest {

    private Post post;
    private User user;
    private Like like;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .id(1L)
                .content("게시글 내용")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .createdAt(LocalDateTime.now())
                .build();
        user = User.builder()
                .id(1L)
                .nickname("안녕하세욘")
                .ageRange(AgeRange.TEENS)
                .socialProvider(SocialProvider.KAKAO)
                .socialId("KAKAO_ID")
                .profileUrl("kakao.com/profile")
                .createdAt(LocalDateTime.now())
                .build();
        like = Like.builder().id(1L).user(user).createdAt(LocalDateTime.now()).build();
    }

    @DisplayName("게시글 할당 - 성공")
    @Test
    void assignPost() {
        //given
        //when
        like.assignPost(post);
        //then
        assertThat(like.getPost()).isEqualTo(post);
    }

    @DisplayName("좋아요 누른 유저인지 확인")
    @Test
    void isSameUser() {
        //given
        //when
        //then
        assertThat(like.isSameUser(user.getId())).isTrue();
    }
}
