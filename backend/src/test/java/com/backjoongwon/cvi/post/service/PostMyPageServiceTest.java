package com.backjoongwon.cvi.post.service;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.Filter;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostWithCommentResponse;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("게시글 - 마이페이지 비즈니스 흐름 테스트")
public class PostMyPageServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostService postService;

    private User user;
    private Optional<User> optionalUser;

    @BeforeEach
    void init() {
        initUser();
        List<Post> posts = initPosts();
        initLikes(posts);
        initComments(posts);
    }

    private User initUser() {
        user = User.builder()
                .nickname("테스트유저")
                .ageRange(AgeRange.FORTIES)
                .socialProvider(SocialProvider.NAVER)
                .socialId("NAVER_ID")
                .profileUrl("naver.com/profile")
                .build();
        optionalUser = Optional.of(user);
        return userRepository.save(user);
    }

    private List<Post> initPosts() {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Post post = Post.builder()
                    .content("Test " + i)
                    .vaccinationType(VaccinationType.ASTRAZENECA)
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .build();
            posts.add(post);
        }
        postRepository.saveAll(posts);
        return posts;
    }

    private void initLikes(List<Post> posts) {
        posts.forEach(post -> {
            Like like = Like.builder().user(user).build();
            post.assignLike(like);
            likeRepository.save(like);
        });
    }

    private void initComments(List<Post> posts) {
        posts.forEach(post -> {
            Comment comment = Comment.builder().content("댓글").user(user).build();
            post.assignComment(comment);
            commentRepository.save(comment);
        });
    }

    @DisplayName("내가 작성한 게시글 조회 - 성공")
    @Test
    void findByUserAndFilterNone() {
        //given
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(optionalUser, Filter.NONE);
        //then
        List<String> contents = postResponses.stream()
                .map(PostWithCommentResponse::getContent)
                .collect(Collectors.toList());
        List<Long> userIds = postResponses.stream()
                .map(postResponse -> postResponse.getWriter().getId())
                .distinct()
                .collect(Collectors.toList());

        assertThat(contents).containsExactlyInAnyOrder("Test 2", "Test 1", "Test 0");
        assertThat(userIds).containsExactly(user.getId());
    }

    @DisplayName("내가 좋아요 한 게시글 조회 - 성공")
    @Test
    void findByUserAndFilterLikes() {
        //given
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(optionalUser, Filter.LIKES);
        //then
        List<String> contents = postResponses.stream()
                .map(PostWithCommentResponse::getContent)
                .collect(Collectors.toList());

        assertThat(contents).containsExactlyInAnyOrder("Test 2", "Test 1", "Test 0");
    }

    @DisplayName("내가 댓글을 단 게시글 조회 - 성공")
    @Test
    void findByUserAndFilterComments() {
        //given
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(optionalUser, Filter.COMMENTS);
        //then
        List<String> contents = postResponses.stream()
                .map(PostWithCommentResponse::getContent)
                .collect(Collectors.toList());
        List<Long> userIds = postResponses.stream()
                .flatMap(postResponse -> postResponse.getComments().stream()
                        .map(commentResponse -> commentResponse.getWriter().getId()))
                .distinct()
                .collect(Collectors.toList());

        assertThat(userIds).containsExactly(user.getId());
        assertThat(contents).containsExactlyInAnyOrder("Test 2", "Test 1", "Test 0");
    }

    @ParameterizedTest(name = "내가 작성한 글 첫 페이징 조회 - 성공")
    @MethodSource
    void findMyPostsPagingFirstPage(int offset, int size, List<String> expectedContents) {
        //given
        Filter filter = Filter.NONE;
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(filter, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
    }

    static Stream<Arguments> findMyPostsPagingFirstPage() {
        return Stream.of(
                Arguments.of(0, 2, Arrays.asList("Test 2", "Test 1")),
                Arguments.of(0, 1, Collections.singletonList("Test 2")));
    }

    @ParameterizedTest(name = "내가 작성한 글 다음 페이징 조회 - 성공")
    @MethodSource
    void findMyPostsPagingNextPage(int offset, int size, List<String> expectedContents) {
        //given
        Filter filter = Filter.NONE;
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(filter, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
    }

    static Stream<Arguments> findMyPostsPagingNextPage() {
        return Stream.of(
                Arguments.of(1, 2, Arrays.asList("Test 1", "Test 0")),
                Arguments.of(1, 1, Collections.singletonList("Test 1")));
    }

    @ParameterizedTest(name = "내가 좋아요 한 글 첫 페이징 조회 - 성공")
    @MethodSource
    void findLikedPostsPagingFirstPage(int offset, int size, List<String> expectedContents) {
        //given
        Filter filter = Filter.LIKES;
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(filter, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
    }

    static Stream<Arguments> findLikedPostsPagingFirstPage() {
        return Stream.of(
                Arguments.of(0, 2, Arrays.asList("Test 2", "Test 1")),
                Arguments.of(0, 1, Collections.singletonList("Test 2")));
    }

    @ParameterizedTest(name = "내가 좋아요 한 글 다음 페이징 조회 - 성공")
    @MethodSource
    void findLikedPostsPagingNextPage(int offset, int size, List<String> expectedContents) {
        //given
        Filter filter = Filter.LIKES;
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(filter, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
    }

    static Stream<Arguments> findLikedPostsPagingNextPage() {
        return Stream.of(
                Arguments.of(1, 2, Arrays.asList("Test 1", "Test 0")),
                Arguments.of(1, 1, Collections.singletonList("Test 1")));
    }

    @ParameterizedTest(name = "내가 댓글 단 게시글 첫 페이징 조회 - 성공")
    @MethodSource
    void findCommentedPostFirstPage(int offset, int size, List<String> expectedContents) {
        //given
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(Filter.COMMENTS, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
    }

    static Stream<Arguments> findCommentedPostFirstPage() {
        return Stream.of(
                Arguments.of(0, 2, Arrays.asList("Test 2", "Test 1")),
                Arguments.of(0, 1, Collections.singletonList("Test 2")));
    }

    @ParameterizedTest(name = "내가 댓글 단 게시글 다음 페이징 조회 - 성공")
    @MethodSource
    void findCommentedPostNextPage(int offset, int size, List<String> expectedContents) {
        //given
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(Filter.COMMENTS, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
    }

    static Stream<Arguments> findCommentedPostNextPage() {
        return Stream.of(
                Arguments.of(1, 2, Arrays.asList("Test 1", "Test 0")),
                Arguments.of(1, 1, Collections.singletonList("Test 1")));
    }
}
