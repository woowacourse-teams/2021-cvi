package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("게시글-좋아요 비즈니스 흐름 테스트")
public class PostLikeServiceTest extends InitPostServiceTest {

    @DisplayName("게시글 좋아요 생성 - 성공")
    @Test
    void createLike() {
        //given
        //when
        Long postId = post1.getId();
        postService.createLike(postId, optionalAnotherUser);
        Post post = postRepository.findWithLikesById(postId).get();
        //then
        assertThat(post.getLikesCount()).isEqualTo(2);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 게시글이 없는 경우")
    @Test
    void createLikeFailureWhenPostNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createLike(Long.MAX_VALUE, optionalAnotherUser))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 동일한 유저가 이미 좋아요를 누른 경우")
    @Test
    void createLikeFailureWhenAlreadyCreatedBySameUser() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createLike(post1.getId(), optionalUser))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 좋아요 삭제 - 성공")
    @Test
    void deleteLike() {
        //given
        //when
        postService.deleteLike(post1.getId(), optionalUser);
        Post actualPost = postRepository.findWithLikesById(this.post1.getId())
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        //then
        assertThat(actualPost.getLikes().getLikes()).isEmpty();
    }

    @DisplayName("게시글 좋아요 삭제 - 실패 - 다른 유저인 경우 ")
    @Test
    void deleteLikeFailureWhenInvalidToken() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.deleteLike(post2.getId(), optionalAnotherUser))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 좋아요 삭제 - 실패 - 삭제 요청한 좋아요가 해당 게시글에 없는 경우")
    @Test
    void deleteLikeFailureWhenLikeNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.deleteLike(post5.getId() + 1, optionalUser))
                .isInstanceOf(NotFoundException.class);
    }
}
