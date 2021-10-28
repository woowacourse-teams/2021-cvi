package com.cvi.like.domain.model;

import com.cvi.exception.InvalidOperationException;
import com.cvi.exception.NotFoundException;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("좋아요 도메인 테스트")
class LikeTest {

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

    @DisplayName("게시글 할당 - 실패 - 게시글이 없는 경우")
    @Test
    void assignPostFailure() {
        //given
        //when
        //then
        assertThatThrownBy(() -> like.assignPost(null)).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 할당 - 실패 - 이미 게시글에 할당된 경우")
    @Test
    void assignPostFailureWhenAlreadyAssignedPost() {
        //given
        //when
        like.assignPost(post);
        //then
        assertThatThrownBy(() -> like.assignPost(post)).isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("좋아요 누른 유저인지 확인 - 성공")
    @Test
    void isSameUser() {
        //given
        //when
        //then
        assertThat(like.isSameUser(user.getId())).isTrue();
    }
}
