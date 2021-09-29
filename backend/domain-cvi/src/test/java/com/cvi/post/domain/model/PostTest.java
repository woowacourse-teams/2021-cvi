package com.cvi.post.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cvi.comment.domain.model.Comment;
import com.cvi.exception.InvalidOperationException;
import com.cvi.exception.NotFoundException;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
        post.updateContent(updatedPost, user);
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
        assertThatThrownBy(() -> post.updateContent(updatedPost, targetUser))
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

    @ParameterizedTest(name = "페이징 요청 값 resize - 성공")
    @MethodSource
    void resizePagingRangeTest(int offset, int size, List<String> contentResult) {
        //given
        User user = User.builder()
            .id(1L)
            .nickname("인비")
            .build();
        Post post = Post.builder()
            .content("내용")
            .user(user)
            .build();
        Comment comment1 = Comment.builder().content("댓글1").user(user).createdAt(LocalDateTime.now().minusHours(1L)).build();
        Comment comment2 = Comment.builder().content("댓글2").user(user).createdAt(LocalDateTime.now().minusHours(2L)).build();
        post.assignComment(comment1);
        post.assignComment(comment2);
        //when
        List<Comment> slicedComments = post.sliceCommentsAsList(offset, size);
        //then
        assertThat(slicedComments).extracting("content").containsExactlyElementsOf(contentResult);
    }

    static Stream<Arguments> resizePagingRangeTest() {
        return Stream.of(
            Arguments.of(0, 2, Arrays.asList("댓글1", "댓글2")),
            Arguments.of(1, 1, Arrays.asList("댓글2")),
            Arguments.of(0, 1, Arrays.asList("댓글1")),
            Arguments.of(2, 1, Collections.emptyList())
        );
    }
}
