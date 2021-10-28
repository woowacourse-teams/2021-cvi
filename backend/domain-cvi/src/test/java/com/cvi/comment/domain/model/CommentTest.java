package com.cvi.comment.domain.model;

import com.cvi.exception.InvalidOperationException;
import com.cvi.exception.NotFoundException;
import com.cvi.exception.UnAuthorizedException;
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

@DisplayName("댓글 도메인 테스트")
class CommentTest {

    private Post post;
    private User user;
    private User anotherUser;
    private Comment comment;

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
        anotherUser = User.builder()
            .id(2L)
            .nickname("방구왕라이언")
            .ageRange(AgeRange.TEENS)
            .socialProvider(SocialProvider.KAKAO)
            .socialId("KAKAO_ID")
            .profileUrl("kakao.com/profile")
            .createdAt(LocalDateTime.now())
            .build();
        comment = Comment.builder().id(1L).content("댓글입니다.").build();
    }

    @DisplayName("게시글 할당 - 성공")
    @Test
    void assignPost() {
        //given
        //when
        comment.assignPost(post);
        //then
        assertThat(comment.getPost()).isEqualTo(post);
    }

    @DisplayName("게시글 할당 - 실패 - 게시글이 없는 경우")
    @Test
    void assignPostFailure() {
        //given
        //when
        //then
        assertThatThrownBy(() -> comment.assignPost(null)).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 할당 - 실패 - 이미 게시글에 할당된 경우")
    @Test
    void assignPostFailureWhenAlreadyAssignedPost() {
        //given
        //when
        comment.assignPost(post);
        //then
        assertThatThrownBy(() -> comment.assignPost(post)).isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("유저 할당 - 성공")
    @Test
    void assignUser() {
        //given
        //when
        comment.assignUser(user);
        //then
        assertThat(comment.getUser()).isEqualTo(user);
    }

    @DisplayName("유저 할당 - 실패 - 유저가 없는 경우")
    @Test
    void assignUserFailureWhenUserIsNull() {
        //given
        //when
        //then
        assertThatThrownBy(() -> comment.assignUser(null)).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("유저 할당 - 실패 - 유저가 이미 할당된 경우")
    @Test
    void assignUserFailureWhenAlreadyUserExists() {
        //given
        comment.assignUser(user);
        //when
        //then
        assertThatThrownBy(() -> comment.assignUser(anotherUser)).isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("댓글 수정 - 성공")
    @Test
    void update() {
        //given
        Comment updateComment = Comment.builder().content("수정 댓글").user(user).build();
        comment.assignUser(user);
        comment.assignPost(post);
        //when
        comment.update(updateComment, user);
        //then
        assertThat(comment.getContent()).isEqualTo(updateComment.getContent());
    }

    @DisplayName("댓글 수정 - 삭제 - 다른 사용자의 댓글인 경우")
    @Test
    void updateFailure() {
        //given
        Comment updateComment = Comment.builder().content("수정 댓글").user(user).build();
        comment.assignUser(user);
        comment.assignPost(post);
        //when
        //then
        assertThatThrownBy(() -> comment.update(updateComment, anotherUser)).isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("댓글 작성자가 같은 지 확인 - 성공")
    @Test
    void isSameUser() {
        //given
        comment.assignUser(user);
        comment.assignPost(post);
        //when
        //then
        assertThat(comment.isSameUser(user)).isTrue();
    }

    @DisplayName("같은 댓글인지 확인 - 성공")
    @Test
    void isSameAs() {
        //given
        comment.assignUser(user);
        comment.assignPost(post);
        //when
        //then
        assertThat(comment.isSameAs(comment.getId())).isTrue();
    }
}
