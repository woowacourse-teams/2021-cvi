package com.backjoongwon.cvi.post.domain;

import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.SocialProvider;
import com.backjoongwon.cvi.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Post 도메인 테스트")
class PostTest {

    private Post post;
    private User user;

    @BeforeEach
    void init() {
        post = Post.builder()
                .content("content1")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .createdAt(LocalDateTime.now())
                .build();
        user = User.builder()
                .id(1L)
                .nickname("안녕하세욘")
                .ageRange(AgeRange.TEENS)
                .socialProfileUrl("")
                .socialProvider(SocialProvider.KAKAO)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @DisplayName("작성자 할당 - 성공")
    @Test
    void assignUser() {
        //given
        //when
        post.assignUser(user);
        //then
        assertThat(post.getUser()).isNotNull();
    }

    @DisplayName("작성자 할당 - 실패 - 이미 작성자가 존재함")
    @Test
    void assignUserFailureWhenAlreadyExists() {
        //given
        User targetUser = User.builder()
                .id(2L)
                .build();
        //when
        post.assignUser(user);
        //then
        assertThatThrownBy(() -> post.assignUser(targetUser))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("작성자 할당 - 실패 - 할당하려는 작성자가 없음")
    @Test
    void assignUserFailureWhenNull() {
        //given
        //when
        //then
        assertThatThrownBy(() -> post.assignUser(null))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("조회수 증가 - 성공")
    @Test
    void increaseViewCount() {
        //given
        //when
        post.increaseViewCount();
        //then
        assertThat(post.getViewCount()).isEqualTo(1);
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void update() {
        //given
        Post updatedPost = Post.builder()
                .content("content2")
                .build();
        //when
        post.assignUser(user);
        post.update(updatedPost, user);
        //then
        assertThat(post.getContent()).isEqualTo(updatedPost.getContent());
    }

    @DisplayName("게시글 수정 - 실패")
    @Test
    void updateFailWhenUserNotMatch() {
        //given
        User targetUser = User.builder()
                .id(2L)
                .build();
        Post updatedPost = Post.builder()
                .content("content2")
                .build();
        //when
        post.assignUser(user);
        //then
        assertThatThrownBy(() -> post.update(updatedPost, targetUser))
                .isExactlyInstanceOf(InvalidOperationException.class);
    }
}