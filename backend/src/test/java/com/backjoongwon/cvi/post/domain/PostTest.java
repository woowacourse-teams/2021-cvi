package com.backjoongwon.cvi.post.domain;

import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("게시글 도메인 테스트")
class PostTest {

    @DisplayName("게시글 작성자 할당 - 성공")
    @Test
    void assignUser() {
        //given
        Post post = Post.builder().build();
        User user = User.builder()
                .id(1L)
                .nickname("인비")
                .build();
        //when
        post.assignUser(user);
        //then
        assertThat(post.getUser()).isNotNull();
    }

    @DisplayName("게시글 작성자 할당 - 실패 - 이미 작성자가 존재함")
    @Test
    void assignUserFailureWhenAlreadyExists() {
        //given
        User user = User.builder()
                .id(1L)
                .nickname("인비")
                .build();
        Post post = Post.builder()
                .user(user)
                .build();
        User newUser = User.builder()
                .nickname("라이언")
                .id(2L)
                .build();
        //when
        //then
        assertThatThrownBy(() -> post.assignUser(newUser))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 작성자 할당 - 실패 - 할당하려는 작성자가 없음")
    @Test
    void assignUserFailureWhenNull() {
        //given
        Post post = Post.builder()
                .build();
        //when
        //then
        assertThatThrownBy(() -> post.assignUser(null))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 조회수 증가 - 성공")
    @Test
    void increaseViewCount() {
        //given
        Post post = Post.builder()
                .build();
        //when
        post.increaseViewCount();
        //then
        assertThat(post.getViewCount()).isEqualTo(1);
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void update() {
        //given
        Post post = Post.builder()
                .content("content1")
                .build();
        Post updatePost = Post.builder()
                .content("content2")
                .build();
        //when
        post.update(updatePost);
        //then
        assertThat(post.getContent()).isEqualTo(updatePost.getContent());
    }
}