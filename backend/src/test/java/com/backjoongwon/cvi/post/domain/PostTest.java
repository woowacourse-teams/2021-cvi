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

import static org.assertj.core.api.Assertions.*;

@DisplayName("게시글 도메인 테스트")
class PostTest {

    private User user;
    private Post post;

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
                .profileUrl("")
                .socialProvider(SocialProvider.KAKAO)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @DisplayName("게시글 작성자 할당 - 성공")
    @Test
    void assignUser() {
        //given
        //when
        post.assignUser(user);
        //then
        assertThat(post.getUser()).isNotNull();
    }

    @DisplayName("게시글 작성자 할당 - 실패 - 이미 작성자가 존재함")
    @Test
    void assignUserFailureWhenAlreadyExists() {
        //given
        User targetUser = User.builder()
                .id(2L)
                .nickname("독함")
                .build();
        //when
        post.assignUser(user);
        //then
        assertThatThrownBy(() -> post.assignUser(targetUser))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 작성자 할당 - 실패 - 할당하려는 작성자가 없음")
    @Test
    void assignUserFailureWhenNull() {
        //given
        //when
        //then
        assertThatThrownBy(() -> post.assignUser(null))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 조회수 증가 - 성공")
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
        post.assignUser(user);
        //when
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
                .nickname("라이언방구")
                .build();
        Post updatedPost = Post.builder()
                .content("content2")
                .build();
        post.assignUser(user);
        //when
        //then
        assertThatThrownBy(() -> post.update(updatedPost, targetUser))
                .isExactlyInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 작성자 확인 - 성공")
    @Test
    void deletePost() {
        //given
        User user = User.builder()
                .id(2L)
                .nickname("인비")
                .build();
        Post post = Post.builder()
                .content("내용")
                .build();
        post.assignUser(user);
        //when
        //then
        assertThatCode(() -> post.validateAuthor(user))
                .doesNotThrowAnyException();
    }

    @DisplayName("게시글 작성자 확인 - 실패 - 글 작성자가 아님")
    @Test
    void deletePostFailureWhenNotAuthor() {
        //given
        User user = User.builder()
                .id(2L)
                .nickname("인비")
                .build();
        User otherUser = User.builder()
                .id(3L)
                .nickname("라이언")
                .build();
        Post post = Post.builder()
                .content("내용")
                .build();
        post.assignUser(user);
        //when
        //then
        assertThatThrownBy(() -> post.validateAuthor(otherUser))
                .isInstanceOf(InvalidOperationException.class);
    }
}