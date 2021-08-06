package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.Sort;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.LikeResponse;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("게시글 비즈니스 흐름 테스트")
class PostServiceTest extends InitPostServiceTest {

    @DisplayName("게시글 생성 - 성공")
    @Test
    void create() {
        //given
        //when
        PostResponse postResponse = postService.create(optionalUserNoLikesAndComment, postRequest);
        Post foundPost = postRepository.findById(postResponse.getId())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        //then
        assertThat(postResponse.getWriter().getId()).isEqualTo(user0.getId());
        assertThat(postResponse.getContent()).isEqualTo(postRequest.getContent());
        assertThat(foundPost).isNotNull();
        assertThat(foundPost.getUser()).isNotNull();
    }

    @DisplayName("게시글 생성 - 실패 - 존재하지 않는 유저")
    @Test
    void createFailureWhenWriterIsNull() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.create(Optional.empty(), postRequest))
                .isExactlyInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("게시글 단일 조회 - 성공")
    @Test
    void findById() {
        //given
        //when
        PostResponse response = postService.findById(post1.getId(), optionalUserNoLikesAndComment);
        //then
        assertThat(response.getId()).isEqualTo(post1.getId());
    }

    @DisplayName("게시글 단일 조회 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void findByIdFailureWhenPostIsNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.findById(0L, optionalUserNoLikesAndComment))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 전체 조회 - 성공")
    @Test
    void findAll() {
        //given
        //when
        List<PostResponse> response = postService.findByVaccineType(VaccinationType.ALL, optionalUserNoLikesAndComment);
        //then
        assertThat(response).hasSize(7);
        assertThat(response.get(0).getContent()).isEqualTo(post6.getContent());
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void update() {
        //given
        PostRequest changedRequest = new PostRequest("changed content", postRequest.getVaccinationType());
        postService.update(post0.getId(), Optional.of(user0), changedRequest);
        Post changedPost = postRepository.findById(post0.getId())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        //then
        assertThat(changedPost.getContent()).isEqualTo(changedRequest.getContent());
    }

    @DisplayName("게시글 수정 - 실패 - 찾을 수 없는 게시글")
    @Test
    void updateFailureWhenCannotFind() {
        //given
        PostRequest changedContent = new PostRequest("changed content", postRequest.getVaccinationType());
        //when
        //then
        assertThatThrownBy(() -> postService.update(0L, optionalUserNoLikesAndComment, changedContent))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 수정 - 실패 - 다른 작성자의 게시글")
    @Test
    void updateFailureWhenOthersPost() {
        //given
        PostRequest changedContent = new PostRequest("changed content", postRequest.getVaccinationType());
        //when
        //then
        assertThatThrownBy(() -> postService.update(post0.getId(), Optional.of(user1), changedContent))
                .isExactlyInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void delete() {
        //given
        //when
        postService.delete(post0.getId(), Optional.of(user0));
        Optional<Post> foundPost = postRepository.findById(post0.getId());
        //then
        assertThat(foundPost).isEmpty();
    }

    @DisplayName("게시글 삭제 - 성공 - 댓글도 함께 삭제 되는지 확인")
    @Test
    void deleteWithComments() {
        //given
        //when
        CommentResponse commentResponse = postService.createComment(post1.getId(), Optional.of(user1), commentRequest);
        postService.delete(post1.getId(), Optional.of(user1));
        Optional<Post> foundPost = postRepository.findById(post1.getId());
        Optional<Comment> foundComment = commentRepository.findById(commentResponse.getId());
        //then
        assertThat(foundPost).isEmpty();
        assertThat(foundComment).isEmpty();
    }

    @DisplayName("게시글 삭제시 좋아요 삭제")
    @Test
    void deleteLikeWhenDeletePost() {
        //given
        LikeResponse likeResponse = postService.createLike(post0.getId(), optionalUserNoLikesAndComment);
        //when
        postService.delete(post0.getId(), optionalUserNoLikesAndComment);
        //then
        assertThat(likeRepository.findById(likeResponse.getId())).isEmpty();
    }

    @DisplayName("게시글 삭제 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void deleteFailureWhenPostIsNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.delete(0L, optionalUserNoLikesAndComment))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 삭제 - 실패 - 글 작성자가 아님")
    @Test
    void deletePostFailureWhenNotAuthor() {
        //given
        PostRequest postRequest = new PostRequest("변경할 내용", VaccinationType.MODERNA);
        //when
        //then
        assertThatThrownBy(() -> postService.update(post1.getId(), Optional.of(user0), postRequest))
                .isInstanceOf(InvalidOperationException.class);
    }

    @ParameterizedTest(name = "게시글 타입별 조회 - 성공")
    @MethodSource
    void findByVaccineType(VaccinationType vaccinationType) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, optionalUserNoLikesAndComment);
        //then
        assertThat(postResponses).filteredOn(
                response -> response.getVaccinationType().equals(vaccinationType)
        );
    }

    static Stream<Arguments> findByVaccineType() {
        return Stream.of(
                Arguments.of(VaccinationType.PFIZER),
                Arguments.of(VaccinationType.ASTRAZENECA),
                Arguments.of(VaccinationType.MODERNA),
                Arguments.of(VaccinationType.JANSSEN)
        );
    }

    @DisplayName("게시글 타입별(전체) 첫 페이징 조회 - 성공")
    @Test
    void findByVaccineTypeFirstPageAll() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(VaccinationType.ALL, 0, 3, Sort.CREATED_AT_DESC, 24, optionalUserNoLikesAndComment);
        //then
        assertThat(postResponses).size().isEqualTo(3);
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(Arrays.asList("Test 6", "Test 5", "Test 4"));
        assertThat(postResponses).extracting("vaccinationType").filteredOn(vaccinationType -> vaccinationType instanceof VaccinationType);
    }

    @DisplayName("게시글 타입별(전체) 다음 페이징 조회 - 성공")
    @Test
    void findByVaccineTypeNextPageAll() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(VaccinationType.ALL, 3, 3, Sort.CREATED_AT_DESC, 24, optionalUserNoLikesAndComment);
        //then
        assertThat(postResponses).size().isEqualTo(3);
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(Arrays.asList("Test 3", "Test 2", "Test 1"));
        assertThat(postResponses).extracting("vaccinationType").filteredOn(vaccinationType -> vaccinationType instanceof VaccinationType);
    }

    @ParameterizedTest(name = "게시글 타입별 첫 페이징 조회 - 성공")
    @MethodSource
    void findByVaccineTypeFirstPage(VaccinationType vaccinationType, int size, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, size, Sort.CREATED_AT_DESC, 2147483647, optionalUserNoLikesAndComment);
        //then
        assertThat(postResponses).size().isEqualTo(contentResult.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
        assertThat(postResponses).extracting("vaccinationType").containsOnly(vaccinationType);
    }

    static Stream<Arguments> findByVaccineTypeFirstPage() {
        return Stream.of(
                Arguments.of(VaccinationType.ASTRAZENECA, 1, Arrays.asList("Test 3")),
                Arguments.of(VaccinationType.ASTRAZENECA, 2, Arrays.asList("Test 3", "Test 1")),
                Arguments.of(VaccinationType.JANSSEN, 1, Arrays.asList("Test 5")),
                Arguments.of(VaccinationType.PFIZER, 1, Arrays.asList("Test 6"))
        );
    }

    @ParameterizedTest(name = "게시글 타입별 다음 페이징 조회 - 성공")
    @MethodSource
    void findByVaccineTypeNextPage(VaccinationType vaccinationType, int size, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 1, size, Sort.CREATED_AT_DESC, 2147483647, optionalUserNoLikesAndComment);
        //then
        assertThat(postResponses).size().isEqualTo(contentResult.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
        assertThat(postResponses).extracting("vaccinationType").containsOnly(vaccinationType);
    }

    static Stream<Arguments> findByVaccineTypeNextPage() {
        return Stream.of(
                Arguments.of(VaccinationType.ASTRAZENECA, 1, Arrays.asList("Test 1")),
                Arguments.of(VaccinationType.ASTRAZENECA, 2, Arrays.asList("Test 1", "Test 0")),
                Arguments.of(VaccinationType.JANSSEN, 5, Collections.singletonList("Test 4")),
                Arguments.of(VaccinationType.PFIZER, 5, Collections.singletonList("Test 2"))
        );
    }

//    @ParameterizedTest(name = "게시글 정렬 조회 - 성공")
//    @MethodSource
//    void findByVaccineTypeSortBy() {
//        System.out.println();
//    }
//
//    static Stream<Arguments> findByVaccineTypeSortBy() {
//        return Stream.of(
//                Arguments.of()
//        );
//    }

    @DisplayName("게시글에 좋아요를 누른 후 본인이 다시 해당 글을 조회하면 hasLiked값이 true로 조회된다.")
    @Test
    void hasLiked() {
        //given
        //when
        PostResponse postResponse = postService.findById(post1.getId(), optionalUserWithLikesAndComment);
        //then
        assertThat(postResponse.isHasLiked()).isTrue();
    }

    @DisplayName("게시글에 좋아요를 누르지 않은 사람이 해당 글을 조회하면 hasLiked값이 false로 조회된다.")
    @Test
    void notHasLiked() {
        //given
        //when
        PostResponse postResponse = postService.findById(post0.getId(), optionalUserNoLikesAndComment);
        //then
        assertThat(postResponse.isHasLiked()).isFalse();
    }
}
