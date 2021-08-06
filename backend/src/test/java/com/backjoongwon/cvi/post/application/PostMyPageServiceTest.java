package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.post.domain.Filter;
import com.backjoongwon.cvi.post.dto.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게시글-마이페이지 비즈니스 흐름 테스트")
public class PostMyPageServiceTest extends InitPostServiceTest {

    @ParameterizedTest(name = "내가 작성한 글 첫 페이징 조회 - 성공")
    @MethodSource
    void findMyPostsPagingFirstPage(int offset, int size, List<String> expectedContents) {
        //given
        Filter filter = Filter.NONE;
        //when
        List<PostResponse> postResponses = postService.findByUserAndFilter(filter, offset, size, Optional.of(user0));
        //then
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
    }

    static Stream<Arguments> findMyPostsPagingFirstPage() {
        return Stream.of(
                Arguments.of(0, 4, Arrays.asList("Test 6", "Test 5", "Test 0")),
                Arguments.of(0, 2, Arrays.asList("Test 6", "Test 5")));
    }

    @ParameterizedTest(name = "내가 좋아요 한 글 첫 페이징 조회 - 성공")
    @MethodSource
    void findLikedPostsPagingFirstPage(int offset, int size, List<String> expectedContents) {
        //given
        Filter filter = Filter.LIKES;
        //when
        List<PostResponse> postResponses = postService.findByUserAndFilter(filter, offset, size, Optional.of(user1));
        //then
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
    }

    static Stream<Arguments> findLikedPostsPagingFirstPage() {
        return Stream.of(
                Arguments.of(0, 4, Arrays.asList("Test 6", "Test 5", "Test 4", "Test 3")),
                Arguments.of(0, 2, Arrays.asList("Test 6", "Test 5")));
    }

    @DisplayName("내가 댓글을 단 게시글 조회 - 성공")
    @Test
    void findByUserAndFilterComments() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByUserAndFilter(optionalUserWithLikesAndComment, Filter.COMMENTS);
        List<Long> postIds = postResponses.stream()
                .map(PostResponse::getId)
                .collect(Collectors.toList());
        //then
        assertThat(postIds).containsExactlyInAnyOrder(post6.getId(), post5.getId(), post4.getId(), post3.getId(), post2.getId(), post1.getId());
    }

    @ParameterizedTest(name = "내가 댓글 단 게시글 첫 페이징 조회 - 성공")
    @MethodSource
    void findCommentedPostFirstPage(int size, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByUserAndFilter(Filter.COMMENTS, 0, size, Optional.of(user1));
        //then
        assertThat(postResponses.size()).isEqualTo(contentResult.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
    }

    static Stream<Arguments> findCommentedPostFirstPage() {
        return Stream.of(
                Arguments.of(3, Arrays.asList("Test 6", "Test 5", "Test 4")),
                Arguments.of(2, Arrays.asList("Test 6", "Test 5"))
        );
    }

    @DisplayName("내가 작성한 게시글 조회 - 성공")
    @Test
    void findByUserAndFilterNone() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByUserAndFilter(Optional.of(user0), Filter.NONE);
        //then
        List<Long> userIds = postResponses.stream()
                .map(postResponse -> postResponse.getWriter().getId())
                .distinct()
                .collect(Collectors.toList());
        assertThat(userIds).containsExactly(user0.getId());
    }

    @DisplayName("내가 좋아요 한 게시글 조회 - 성공")
    @Test
    void findByUserAndFilterLikes() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByUserAndFilter(optionalUserWithLikesAndComment, Filter.LIKES);
        //then
        List<Long> postIds = postResponses.stream()
                .map(PostResponse::getId)
                .collect(Collectors.toList());
        assertThat(postIds).containsExactly(post6.getId(), post5.getId(), post4.getId(), post3.getId(), post2.getId(), post1.getId());
    }
}
