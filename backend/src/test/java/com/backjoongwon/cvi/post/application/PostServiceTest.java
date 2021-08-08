package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.Sort;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.LikeResponse;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("게시글 비즈니스 흐름 테스트")
class PostServiceTest extends ApiDocument {
    @Autowired
    protected PostRepository postRepository;

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected LikeRepository likeRepository;

    @Autowired
    protected PostService postService;

    @PersistenceContext
    private EntityManager em;

    protected User user0;
    protected User user1;
    protected Optional<User> optionalUserNoLikesAndComment;
    protected Optional<User> optionalUserWithLikesAndComment;
    protected Post post0;
    protected Post post1;
    protected Post post2;
    protected Post post3;
    protected Post post4;
    protected Post post5;
    protected Post post6;
    protected PostRequest postRequest;
    protected CommentRequest commentRequest;

    /**
     * 포스트0(유저0작성) -> 좋아요 0개
     * 포스트1(유저1작성) -> 좋아요 1개, 댓글 1개 (유저1)
     * 포스트2(유저2작성) -> 좋아요 2개, 댓글 2개 (유저1, 유저2)
     * 포스트3(유저3작성) -> 좋아요 3개, 댓글 3개 (유저1, 유저2, 유저3)
     * 포스트4(유저4작성) -> 좋아요 4개, 댓글 4개 (유저1, 유저2, 유저3, 유저4)
     * 포스트5(유저0작성) -> 좋아요 5개, 댓글 5개 (유저1, 유저2, 유저3, 유저4, 유저5)
     * 포스트6(유저0작성) -> 좋아요 6개, 댓글 6개 (유저1, 유저2, 유저3, 유저4, 유저5, 유저6)
     */

    @BeforeEach
    void init() {
        List<User> users = initUser();
        List<Post> posts = initPost(users);
        initLike(users, posts);
        initComment(users, posts);
        postRequest = new PostRequest("Test Content", VaccinationType.PFIZER);
        commentRequest = new CommentRequest("댓글");
    }

    private List<User> initUser() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            User user = User.builder()
                    .nickname("테스트유저" + i)
                    .ageRange(AgeRange.FORTIES)
                    .profileUrl("")
                    .socialProvider(SocialProvider.NAVER)
                    .build();
            users.add(user);
        }
        userRepository.saveAll(users);

        user0 = users.get(0);
        user1 = users.get(1);

        optionalUserNoLikesAndComment = Optional.of(user0);
        optionalUserWithLikesAndComment = Optional.of(user1);

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

}
