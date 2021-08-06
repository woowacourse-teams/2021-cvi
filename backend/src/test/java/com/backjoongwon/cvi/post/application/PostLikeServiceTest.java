package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("게시글-좋아 비즈니스 흐름 테스트")
public class PostLikeServiceTest extends InitPostServiceTest {

    @DisplayName("게시글 좋아요 생성 - 성공")
    @Test
    void createLike() {
        //given
        //when
        Long postId = post0.getId();
        postService.createLike(postId, optionalUserWithLikesAndComment);
        Post post = postRepository.findWithLikesById(postId).get();
        //then
        assertThat(post.getLikesCount()).isEqualTo(1);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 게시글이 없는 경우")
    @Test
    void createLikeFailureWhenPostNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createLike(Long.MAX_VALUE, optionalUserWithLikesAndComment))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 동일한 유저가 이미 좋아요를 누른 경우")
    @Test
    void createLikeFailureWhenAlreadyCreatedBySameUser() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createLike(post1.getId(), optionalUserWithLikesAndComment))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 좋아요 삭제 - 성공")
    @Test
    void deleteLike() {
        //given
        //when
        postService.deleteLike(post1.getId(), optionalUserWithLikesAndComment);
        Post actualPost = postRepository.findWithLikesById(this.post0.getId())
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        //then
        assertThat(actualPost.getLikes().getLikes()).isEmpty();
    }

    @DisplayName("게시글 좋아요 삭제 - 실패 - 좋아요를 누르지 않은 글에 좋아요를 삭제 요청한 경우")
    @Test
    void deleteLikeFailureWhenInvalidToken() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.deleteLike(post1.getId(), optionalUserNoLikesAndComment))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessage("해당 사용자의 좋아요가 글에 존재하지 않습니다.");
    }

    @DisplayName("게시글 좋아요 삭제 - 실패 - 삭제 요청한 좋아요의 게시글이 없는 경우")
    @Test
    void deleteLikeFailureWhenLikeNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.deleteLike(12379021380912L, optionalUserNoLikesAndComment))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 id의 게시글이 존재하지 않습니다.");
    }
}
