package com.cvi.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cvi.comment.domain.model.Comment;
import com.cvi.comment.domain.repository.CommentRepository;
import com.cvi.dto.PostWithCommentResponse;
import com.cvi.image.domain.Image;
import com.cvi.image.repository.ImageRepository;
import com.cvi.like.domain.model.Like;
import com.cvi.like.domain.repository.LikeRepository;
import com.cvi.post.domain.model.Filter;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.post.domain.repository.PostRepository;
import com.cvi.service.post.PostService;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("게시글 - 마이페이지 비즈니스 흐름 테스트")
class PostMyPageServiceTest {

    private static final String IMAGE_URL_1 = "{이미지1 S3 URL}";
    private static final String IMAGE_URL_2 = "{이미지2 S3 URL}";
    private static final String IMAGE_URL_3 = "{이미지3 S3 URL}";
    private static final List<String> POST1_IMAGE_URLS = Collections.singletonList(IMAGE_URL_1);
    private static final List<String> POST2_IMAGE_URLS = Arrays.asList(IMAGE_URL_1, IMAGE_URL_2);
    private static final List<String> POST3_IMAGE_URLS = Arrays.asList(IMAGE_URL_1, IMAGE_URL_2, IMAGE_URL_3);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PostService postService;

    @PersistenceContext
    private EntityManager entityManager;

    private User user;
    private Optional<User> optionalUser;
    private List<Post> posts;
    private List<String> imageUrls;

    @BeforeEach
    void init() {
        initUser();
        List<Post> posts = initPosts();
        initLikes(posts);
        initComments(posts);
        initImages(posts);
        entityManager.flush();
        entityManager.clear();
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
        posts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Post post = Post.builder()
                .content("Test " + (i + 1))
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

    private void initImages(List<Post> posts) {
        imageUrls = Arrays.asList(IMAGE_URL_1, IMAGE_URL_2, IMAGE_URL_3);
        for (int i = 0; i < posts.size() ; i++) {
            final Post post = posts.get(i);
            assignPostToImagesNumberOf(i + 1, post);
        }
    }

    private void assignPostToImagesNumberOf(int numberOfImages, Post post) {
        for (int i = 0; i < numberOfImages; i++) {
            final Image image = Image.builder().url(imageUrls.get(i)).build();
            image.assignPost(post);
            imageRepository.save(image);
        }
    }

    @DisplayName("내가 작성한 게시글 조회 - 성공")
    @Test
    void findByUserAndFilterNone() {
        //given
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(optionalUser, Filter.NONE);
        List<String> contents = postResponses.stream()
            .map(PostWithCommentResponse::getContent)
            .collect(Collectors.toList());
        List<Long> userIds = postResponses.stream()
            .map(postResponse -> postResponse.getWriter().getId())
            .distinct()
            .collect(Collectors.toList());

        //then
        assertThat(contents).containsExactlyInAnyOrder("Test 3", "Test 2", "Test 1");
        assertThat(userIds).containsExactly(user.getId());
        assertImageUrls(postResponses);
    }

    private void assertImageUrls(List<PostWithCommentResponse> postResponses) {
        final List<String> post3ActualImageUrls = postResponses.get(0).getImages();
        final List<String> post2ActualImageUrls = postResponses.get(1).getImages();
        final List<String> post1ActualImageUrls = postResponses.get(2).getImages();
        assertThat(post3ActualImageUrls).containsExactlyElementsOf(POST3_IMAGE_URLS);
        assertThat(post2ActualImageUrls).containsExactlyElementsOf(POST2_IMAGE_URLS);
        assertThat(post1ActualImageUrls).containsExactlyElementsOf(POST1_IMAGE_URLS);
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

        assertThat(contents).containsExactlyInAnyOrder("Test 3", "Test 2", "Test 1");
        assertImageUrls(postResponses);
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
        assertThat(contents).containsExactlyInAnyOrder("Test 3", "Test 2", "Test 1");
        assertImageUrls(postResponses);
    }

    @ParameterizedTest(name = "내가 작성한 글 첫 페이징 조회 - 성공")
    @MethodSource
    void findMyPostsPagingFirstPage(int offset, int size, List<String> expectedContents, List<List<String>> expectedImageUrls) {
        //given
        Filter filter = Filter.NONE;
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(filter, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
        assertThat(postResponses).extracting("images").containsExactlyElementsOf(expectedImageUrls);
    }

    static Stream<Arguments> findMyPostsPagingFirstPage() {
        return Stream.of(
            Arguments.of(0, 2, Arrays.asList("Test 3", "Test 2"), Arrays.asList(POST3_IMAGE_URLS, POST2_IMAGE_URLS)),
            Arguments.of(0, 1, Collections.singletonList("Test 3"), Collections.singletonList(POST3_IMAGE_URLS)));
    }

    @ParameterizedTest(name = "내가 작성한 글 다음 페이징 조회 - 성공")
    @MethodSource
    void findMyPostsPagingNextPage(int offset, int size, List<String> expectedContents, List<List<String>> expectedImageUrls) {
        //given
        Filter filter = Filter.NONE;
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(filter, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
        assertThat(postResponses).extracting("images").containsExactlyElementsOf(expectedImageUrls);
    }

    static Stream<Arguments> findMyPostsPagingNextPage() {
        return Stream.of(
            Arguments.of(1, 2, Arrays.asList("Test 2", "Test 1"), Arrays.asList(POST2_IMAGE_URLS, POST1_IMAGE_URLS)),
            Arguments.of(1, 1, Collections.singletonList("Test 2"), Collections.singletonList(POST2_IMAGE_URLS)));
    }

    @ParameterizedTest(name = "내가 좋아요 한 글 첫 페이징 조회 - 성공")
    @MethodSource
    void findLikedPostsPagingFirstPage(int offset, int size, List<String> expectedContents, List<List<String>> expectedImageUrls) {
        //given
        Filter filter = Filter.LIKES;
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(filter, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
        assertThat(postResponses).extracting("images").containsExactlyElementsOf(expectedImageUrls);
    }

    static Stream<Arguments> findLikedPostsPagingFirstPage() {
        return Stream.of(
            Arguments.of(0, 2, Arrays.asList("Test 3", "Test 2"), Arrays.asList(POST3_IMAGE_URLS, POST2_IMAGE_URLS)),
            Arguments.of(0, 1, Collections.singletonList("Test 3"), Collections.singletonList(POST3_IMAGE_URLS)));
    }

    @ParameterizedTest(name = "내가 좋아요 한 글 다음 페이징 조회 - 성공")
    @MethodSource
    void findLikedPostsPagingNextPage(int offset, int size, List<String> expectedContents, List<List<String>> expectedImageUrls) {
        //given
        Filter filter = Filter.LIKES;
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(filter, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
        assertThat(postResponses).extracting("images").containsExactlyElementsOf(expectedImageUrls);
    }

    static Stream<Arguments> findLikedPostsPagingNextPage() {
        return Stream.of(
            Arguments.of(1, 2, Arrays.asList("Test 2", "Test 1"), Arrays.asList(POST2_IMAGE_URLS, POST1_IMAGE_URLS)),
            Arguments.of(1, 1, Collections.singletonList("Test 2"), Collections.singletonList(POST2_IMAGE_URLS)));
    }

    @ParameterizedTest(name = "내가 댓글 단 게시글 첫 페이징 조회 - 성공")
    @MethodSource
    void findCommentedPostFirstPage(int offset, int size, List<String> expectedContents, List<List<String>> expectedImageUrls) {
        //given
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(Filter.COMMENTS, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
        assertThat(postResponses).extracting("images").containsExactlyElementsOf(expectedImageUrls);
    }

    static Stream<Arguments> findCommentedPostFirstPage() {
        return Stream.of(
            Arguments.of(0, 2, Arrays.asList("Test 3", "Test 2"), Arrays.asList(POST3_IMAGE_URLS, POST2_IMAGE_URLS)),
            Arguments.of(0, 1, Collections.singletonList("Test 3"), Collections.singletonList(POST3_IMAGE_URLS)));
    }

    @ParameterizedTest(name = "내가 댓글 단 게시글 다음 페이징 조회 - 성공")
    @MethodSource
    void findCommentedPostNextPage(int offset, int size, List<String> expectedContents, List<List<String>> expectedImageUrls) {
        //given
        //when
        List<PostWithCommentResponse> postResponses = postService.findByUserAndFilter(Filter.COMMENTS, offset, size, optionalUser);
        //then
        assertThat(postResponses.size()).isEqualTo(expectedContents.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(expectedContents);
    }

    static Stream<Arguments> findCommentedPostNextPage() {
        return Stream.of(
            Arguments.of(1, 2, Arrays.asList("Test 2", "Test 1"), Arrays.asList(POST2_IMAGE_URLS, POST1_IMAGE_URLS)),
            Arguments.of(1, 1, Collections.singletonList("Test 2"), Collections.singletonList(POST2_IMAGE_URLS)));
    }
}
