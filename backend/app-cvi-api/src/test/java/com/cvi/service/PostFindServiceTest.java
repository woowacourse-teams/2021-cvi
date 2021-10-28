package com.cvi.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cvi.CustomParameterizedTest;
import com.cvi.comment.domain.model.Comment;
import com.cvi.comment.domain.repository.CommentRepository;
import com.cvi.dto.PostResponse;
import com.cvi.like.domain.model.Like;
import com.cvi.like.domain.repository.LikeRepository;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.Sort;
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
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("게시글 - 조회 비즈니스 흐름 테스트")
class PostFindServiceTest {

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

    @PersistenceContext
    private EntityManager em;

    private User user;
    private Optional<User> optionalUser;
    private Post postWithoutLikesAndComments;
    private Post postWithOneLikesAndComments;
    private Post postWithTwoLikesAndComments;


    @BeforeEach
    void init() {
        List<User> users = initUser();
        List<Post> posts = initPost(users);
        initLike(users, posts);
        initComment(users, posts);
        modifyPostsCreatedAt(posts);
    }

    private List<User> initUser() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User dummyUser = User.builder()
                .nickname("테스트유저댓글" + i + "좋아요" + i)
                .ageRange(AgeRange.FORTIES)
                .socialProvider(SocialProvider.NAVER)
                .socialId("NAVER_ID")
                .profileUrl("naver.com/profile")
                .build();
            users.add(dummyUser);
        }
        user = users.get(0);
        optionalUser = Optional.of(user);
        return userRepository.saveAll(users);
    }

    private List<Post> initPost(List<User> users) {
        postWithoutLikesAndComments = Post.builder()
            .content("Test 0")
            .vaccinationType(VaccinationType.ASTRAZENECA)
            .user(users.get(0))
            .createdAt(LocalDateTime.now())
            .build();
        postWithOneLikesAndComments = Post.builder()
            .content("Test 1")
            .vaccinationType(VaccinationType.ASTRAZENECA)
            .user(users.get(1))
            .createdAt(LocalDateTime.now())
            .build();
        postWithTwoLikesAndComments = Post.builder()
            .content("Test 2")
            .vaccinationType(VaccinationType.PFIZER)
            .user(users.get(2))
            .createdAt(LocalDateTime.now())
            .build();
        postWithOneLikesAndComments.increaseViewCount();
        postWithTwoLikesAndComments.increaseViewCount();
        postWithTwoLikesAndComments.increaseViewCount();
        List<Post> posts = Arrays.asList(postWithoutLikesAndComments, postWithOneLikesAndComments, postWithTwoLikesAndComments);
        return postRepository.saveAll(posts);
    }

    private void initLike(List<User> users, List<Post> posts) {
        List<Like> likes = new ArrayList<>();
        for (int i = 1; i < posts.size(); i++) {
            for (int j = 1; j <= i; j++) {
                Like like = Like.builder().user(users.get(j)).build();
                posts.get(i).assignLike(like);
                likes.add(like);
                likeRepository.save(like);
            }
        }
    }

    private void initComment(List<User> users, List<Post> posts) {
        List<Comment> comments = new ArrayList<>();
        for (int i = 1; i < posts.size(); i++) {
            for (int j = 1; j <= i; j++) {
                Comment comment = Comment.builder().content("댓글 " + j).user(users.get(j)).build();
                posts.get(i).assignComment(comment);
                comments.add(comment);
                commentRepository.save(comment);
            }
        }
    }

    private void modifyPostsCreatedAt(List<Post> posts) {
        for (int i = posts.size() - 1; i >= 0; i--) {
            Long id = posts.get(i).getId();
            Query q = em.createNativeQuery("UPDATE post SET created_at=:created_at WHERE post_id=:post_id");
            q.setParameter("created_at", LocalDateTime.now().minusHours(posts.size() - i).minusMinutes(30L));
            q.setParameter("post_id", id);
            q.executeUpdate();
        }
    }

    @DisplayName("게시글 타입별 조회 - 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineType(VaccinationType vaccinationType) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, optionalUser);
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

    @DisplayName("게시글 첫 페이징 조회 - 성공")
    @Test
    void findByVaccineTypeFirstPageAll() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(VaccinationType.ALL, 0, 1, Sort.CREATED_AT_DESC, optionalUser);
        //then
        assertThat(postResponses).size().isEqualTo(1);
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(Collections.singletonList("Test 2"));
        assertThat(postResponses).extracting("vaccinationType").filteredOn(vaccinationType -> vaccinationType instanceof VaccinationType);
    }

    @DisplayName("게시글 다음 페이징 조회 - 성공")
    @Test
    void findByVaccineTypeNextPageAll() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(VaccinationType.ALL, 1, 1, Sort.CREATED_AT_DESC, optionalUser);
        //then
        assertThat(postResponses).size().isEqualTo(1);
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(Collections.singletonList("Test 1"));
        assertThat(postResponses).extracting("vaccinationType").filteredOn(vaccinationType -> vaccinationType instanceof VaccinationType);
    }

    @DisplayName("게시글 타입별 첫 페이징 조회 - 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeFirstPage(VaccinationType vaccinationType, int size, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, size, Sort.CREATED_AT_DESC, optionalUser);
        //then
        assertThat(postResponses).size().isEqualTo(contentResult.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
        assertThat(postResponses).extracting("vaccinationType").containsOnly(vaccinationType);
    }

    static Stream<Arguments> findByVaccineTypeFirstPage() {
        return Stream.of(
            Arguments.of(VaccinationType.ASTRAZENECA, 1, Arrays.asList("Test 1")),
            Arguments.of(VaccinationType.ASTRAZENECA, 2, Arrays.asList("Test 1", "Test 0")),
            Arguments.of(VaccinationType.PFIZER, 1, Arrays.asList("Test 2"))
        );
    }

    @DisplayName("게시글 타입별 다음 페이징 조회 - 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeNextPage(VaccinationType vaccinationType, int size, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 1, size, Sort.CREATED_AT_DESC, optionalUser);
        //then
        assertThat(postResponses).size().isEqualTo(contentResult.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
        assertThat(postResponses).extracting("vaccinationType").containsAnyOf(vaccinationType, Collections.emptyList());
    }

    static Stream<Arguments> findByVaccineTypeNextPage() {
        return Stream.of(
            Arguments.of(VaccinationType.ASTRAZENECA, 1, Arrays.asList("Test 0"))
        );
    }

    @DisplayName("게시글 타입별 다음 페이징 조회(다음페이지 없음) - 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeNextPageIsNull(VaccinationType vaccinationType, int size, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 1, size, Sort.CREATED_AT_DESC, optionalUser);
        //then
        assertThat(postResponses).size().isEqualTo(contentResult.size());
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
        assertThat(postResponses).extracting("vaccinationType").doesNotContain(Object.class);
    }

    static Stream<Arguments> findByVaccineTypeNextPageIsNull() {
        return Stream.of(
            Arguments.of(VaccinationType.PFIZER, 1, Collections.emptyList()),
            Arguments.of(VaccinationType.JANSSEN, 1, Collections.emptyList()),
            Arguments.of(VaccinationType.MODERNA, 1, Collections.emptyList())
        );
    }

    @DisplayName("게시글 정렬 조회 좋아요 오름차순- 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeSortByLikeCountAsc(VaccinationType vaccinationType, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, Integer.MAX_VALUE, Sort.LIKE_COUNT_ASC, optionalUser);
        //then
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
    }

    static Stream<Arguments> findByVaccineTypeSortByLikeCountAsc() {
        return Stream.of(
            Arguments.of(VaccinationType.ALL, Arrays.asList("Test 0", "Test 1", "Test 2")),
            Arguments.of(VaccinationType.ASTRAZENECA, Arrays.asList("Test 0", "Test 1")),
            Arguments.of(VaccinationType.PFIZER, Arrays.asList("Test 2")),
            Arguments.of(VaccinationType.JANSSEN, Collections.emptyList()),
            Arguments.of(VaccinationType.MODERNA, Collections.emptyList())
        );
    }

    @DisplayName("게시글 정렬 조회 좋아요 내림차순 - 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeSortByLikeCountDesc(VaccinationType vaccinationType, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, Integer.MAX_VALUE, Sort.LIKE_COUNT_DESC, optionalUser);
        //then
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
    }

    static Stream<Arguments> findByVaccineTypeSortByLikeCountDesc() {
        return Stream.of(
            Arguments.of(VaccinationType.ALL, Arrays.asList("Test 2", "Test 1", "Test 0")),
            Arguments.of(VaccinationType.ASTRAZENECA, Arrays.asList("Test 1", "Test 0")),
            Arguments.of(VaccinationType.PFIZER, Arrays.asList("Test 2")),
            Arguments.of(VaccinationType.JANSSEN, Collections.emptyList()),
            Arguments.of(VaccinationType.MODERNA, Collections.emptyList())
        );
    }

    @DisplayName("게시글 정렬 조회 댓글 갯수 오름차순- 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeSortByCommentsCountAsc(VaccinationType vaccinationType, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, Integer.MAX_VALUE, Sort.COMMENT_COUNT_ASC, optionalUser);
        //then
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
    }

    static Stream<Arguments> findByVaccineTypeSortByCommentsCountAsc() {
        return Stream.of(
            Arguments.of(VaccinationType.ALL, Arrays.asList("Test 0", "Test 1", "Test 2")),
            Arguments.of(VaccinationType.ASTRAZENECA, Arrays.asList("Test 0", "Test 1")),
            Arguments.of(VaccinationType.PFIZER, Arrays.asList("Test 2")),
            Arguments.of(VaccinationType.JANSSEN, Collections.emptyList()),
            Arguments.of(VaccinationType.MODERNA, Collections.emptyList())
        );
    }

    @DisplayName("게시글 정렬 조회 댓글 갯수 내림차순 - 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeSortByCommentsCountDesc(VaccinationType vaccinationType, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, Integer.MAX_VALUE, Sort.COMMENT_COUNT_DESC, optionalUser);
        //then
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
    }

    static Stream<Arguments> findByVaccineTypeSortByCommentsCountDesc() {
        return Stream.of(
            Arguments.of(VaccinationType.ALL, Arrays.asList("Test 2", "Test 1", "Test 0")),
            Arguments.of(VaccinationType.ASTRAZENECA, Arrays.asList("Test 1", "Test 0")),
            Arguments.of(VaccinationType.PFIZER, Arrays.asList("Test 2")),
            Arguments.of(VaccinationType.JANSSEN, Collections.emptyList()),
            Arguments.of(VaccinationType.MODERNA, Collections.emptyList())
        );
    }

    @DisplayName("게시글 정렬 조회 조회수 오름차순- 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeSortByViewCountAsc(VaccinationType vaccinationType, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, Integer.MAX_VALUE, Sort.VIEW_COUNT_ASC, optionalUser);
        //then
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
    }

    static Stream<Arguments> findByVaccineTypeSortByViewCountAsc() {
        return Stream.of(
            Arguments.of(VaccinationType.ALL, Arrays.asList("Test 0", "Test 1", "Test 2")),
            Arguments.of(VaccinationType.ASTRAZENECA, Arrays.asList("Test 0", "Test 1")),
            Arguments.of(VaccinationType.PFIZER, Arrays.asList("Test 2")),
            Arguments.of(VaccinationType.JANSSEN, Collections.emptyList()),
            Arguments.of(VaccinationType.MODERNA, Collections.emptyList())
        );
    }

    @DisplayName("게시글 정렬 조회 조회수 내림차순 - 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeSortByViewCountDesc(VaccinationType vaccinationType, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, Integer.MAX_VALUE, Sort.VIEW_COUNT_DESC, optionalUser);
        //then
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
    }

    static Stream<Arguments> findByVaccineTypeSortByViewCountDesc() {
        return Stream.of(
            Arguments.of(VaccinationType.ALL, Arrays.asList("Test 2", "Test 1", "Test 0")),
            Arguments.of(VaccinationType.ASTRAZENECA, Arrays.asList("Test 1", "Test 0")),
            Arguments.of(VaccinationType.PFIZER, Arrays.asList("Test 2")),
            Arguments.of(VaccinationType.JANSSEN, Collections.emptyList()),
            Arguments.of(VaccinationType.MODERNA, Collections.emptyList())
        );
    }

    @DisplayName("게시글 정렬 조회 시간순 오름차순- 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeSortByCreatedAtAsc(VaccinationType vaccinationType, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, Integer.MAX_VALUE, Sort.CREATED_AT_ASC, optionalUser);
        //then
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
    }

    static Stream<Arguments> findByVaccineTypeSortByCreatedAtAsc() {
        return Stream.of(
            Arguments.of(VaccinationType.ALL, Arrays.asList("Test 0", "Test 1", "Test 2")),
            Arguments.of(VaccinationType.ASTRAZENECA, Arrays.asList("Test 0", "Test 1")),
            Arguments.of(VaccinationType.PFIZER, Arrays.asList("Test 2")),
            Arguments.of(VaccinationType.JANSSEN, Collections.emptyList()),
            Arguments.of(VaccinationType.MODERNA, Collections.emptyList())
        );
    }

    @DisplayName("게시글 정렬 조회 시간순 내림차순 - 성공")
    @CustomParameterizedTest
    @MethodSource
    void findByVaccineTypeSortByCreatedAtDesc(VaccinationType vaccinationType, List<String> contentResult) {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, Integer.MAX_VALUE, Sort.CREATED_AT_DESC, optionalUser);
        //then
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(contentResult);
    }

    static Stream<Arguments> findByVaccineTypeSortByCreatedAtDesc() {
        return Stream.of(
            Arguments.of(VaccinationType.ALL, Arrays.asList("Test 2", "Test 1", "Test 0")),
            Arguments.of(VaccinationType.ASTRAZENECA, Arrays.asList("Test 1", "Test 0")),
            Arguments.of(VaccinationType.PFIZER, Arrays.asList("Test 2")),
            Arguments.of(VaccinationType.JANSSEN, Collections.emptyList()),
            Arguments.of(VaccinationType.MODERNA, Collections.emptyList())
        );
    }
}
