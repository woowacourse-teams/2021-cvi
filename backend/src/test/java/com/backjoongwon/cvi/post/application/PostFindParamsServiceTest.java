package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.Sort;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PostFindParamsServiceTest {
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
    private User anotherUser;
    private Optional<User> optionalUser;
    private Optional<User> optionalAnotherUser;
    private Post post0;
    private Post post1;
    private Post post2;
    private Post post3;
    private Post post4;
    private Post post5;
    private Post post6;

    @BeforeEach
    void init() {
        List<User> users = initUser();
        List<Post> posts = initPost(users);
        initLike(users, posts);
        initComment(users, posts);
    }

    private List<User> initUser() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            User dummyUser = User.builder()
                    .nickname("테스트유저" + i)
                    .ageRange(AgeRange.FORTIES)
                    .profileUrl("")
                    .socialProvider(SocialProvider.NAVER)
                    .build();
            users.add(dummyUser);
        }
        userRepository.saveAll(users);

        user = users.get(0);
        anotherUser = users.get(1);

        optionalUser = Optional.of(user);
        optionalAnotherUser = Optional.of(anotherUser);

        return users;
    }

    private List<Post> initPost(List<User> users) {
        post0 = Post.builder()
                .content("Test 0")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(users.get(0))
                .createdAt(LocalDateTime.now())
                .build();
        post1 = Post.builder()
                .content("Test 1")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(users.get(1))
                .createdAt(LocalDateTime.now())
                .build();
        post2 = Post.builder()
                .content("Test 2")
                .vaccinationType(VaccinationType.PFIZER)
                .user(users.get(2))
                .createdAt(LocalDateTime.now())
                .build();
        post3 = Post.builder()
                .content("Test 3")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(users.get(3))
                .createdAt(LocalDateTime.now())
                .build();
        post4 = Post.builder()
                .content("Test 4")
                .vaccinationType(VaccinationType.JANSSEN)
                .user(users.get(4))
                .createdAt(LocalDateTime.now())
                .build();
        post5 = Post.builder()
                .content("Test 5")
                .vaccinationType(VaccinationType.JANSSEN)
                .user(users.get(0))
                .createdAt(LocalDateTime.now())
                .build();
        post6 = Post.builder()
                .content("Test 6")
                .vaccinationType(VaccinationType.PFIZER)
                .user(users.get(0))
                .createdAt(LocalDateTime.now())
                .build();

        List<Post> posts = Arrays.asList(post0, post1, post2, post3, post4, post5, post6);
        postRepository.saveAll(posts);

        for (int i = 0; i < posts.size(); i++) {
            Long id = posts.get(i).getId();
            Query q = em.createNativeQuery("UPDATE post SET created_at=:created_at WHERE post_id=:post_id");
            q.setParameter("created_at", LocalDateTime.now().minusHours(posts.size() - i));
            q.setParameter("post_id", id);
            q.executeUpdate();
        }
        return posts;
    }

    private void initLike(List<User> users, List<Post> posts) {
        List<Like> likes = new ArrayList<>();
        for (int i = 1; i < posts.size(); i++) {
            for (int j = 1; j <= i; j++) {
                Like like = Like.builder().user(users.get(j)).build();
                posts.get(i).addLike(like);
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

    @ParameterizedTest(name = "게시글 타입별 조회 - 성공")
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

    @DisplayName("게시글 타입별(전체) 첫 페이징 조회 - 성공")
    @Test
    void findByVaccineTypeFirstPageAll() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(VaccinationType.ALL, 0, 3, Sort.CREATED_AT_DESC, 24, optionalUser);
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
        List<PostResponse> postResponses = postService.findByVaccineType(VaccinationType.ALL, 3, 3, Sort.CREATED_AT_DESC, 24, optionalUser);
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
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 0, size, Sort.CREATED_AT_DESC, 2147483647, optionalUser);
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
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, 1, size, Sort.CREATED_AT_DESC, 2147483647, optionalUser);
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
}
