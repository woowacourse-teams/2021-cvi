package com.cvi.post.domain.repository;

import com.cvi.comment.domain.model.Comment;
import com.cvi.comment.domain.repository.CommentRepository;
import com.cvi.image.domain.Image;
import com.cvi.image.repository.ImageRepository;
import com.cvi.like.domain.model.Like;
import com.cvi.like.domain.repository.LikeRepository;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.Sort;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PostRepository 테스트")
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private User user1;
    private User user2;
    private Comment comment1;
    private Comment comment2;
    private Like like1;
    private Like like2;
    private Image image1;
    private Image image2;
    private Post post1;
    private Post post2;
    private Post post3;

    @BeforeEach
    void setUp() {
        initUsers();
        initPost();
        initImages();
        initComments();
        initLikes();
        assertIdAssigned();
    }

    private void initUsers() {
        user1 = User.builder()
            .nickname("인비")
            .ageRange(AgeRange.TEENS)
            .socialProvider(SocialProvider.KAKAO)
            .socialId("1000")
            .profileUrl("profile url 1")
            .build();
        user2 = User.builder()
            .nickname("검프")
            .socialProvider(SocialProvider.NAVER)
            .socialId("1001")
            .profileUrl("profile url 2")
            .ageRange(AgeRange.FIFTIES)
            .build();
        userRepository.save(user1);
        userRepository.save(user2);
    }

    private void initPost() {
        post1 = Post.builder()
            .user(user1)
            .content("화이자 1차 맞았어요.")
            .vaccinationType(VaccinationType.PFIZER)
            .createdAt(LocalDateTime.now().minusHours(3))
            .build();
        post2 = Post.builder()
            .user(user2)
            .content("모더나 1차 맞았어요.")
            .vaccinationType(VaccinationType.MODERNA)
            .createdAt(LocalDateTime.now().minusHours(2))
            .build();
        post3 = Post.builder()
            .user(user1)
            .content("화이자 2차 맞았어요.")
            .vaccinationType(VaccinationType.PFIZER)
            .createdAt(LocalDateTime.now().minusHours(1))
            .build();
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
    }

    private void initImages() {
        image1 = Image.builder()
            .url("image1_s3_url")
            .build();
        image1.assignPost(post1);
        imageRepository.save(image1);

        image2 = Image.builder()
            .url("image2_s3_url")
            .build();
        image2.assignPost(post1);
        imageRepository.save(image2);
    }

    private void initComments() {
        comment1 = Comment.builder()
            .content("댓글 내용1")
            .user(user1)
            .build();
        comment1.assignPost(post1);
        commentRepository.save(comment1);

        comment2 = Comment.builder()
            .content("댓글 내용2")
            .user(user2)
            .build();
        comment2.assignPost(post1);
        commentRepository.save(comment2);
    }

    private void initLikes() {
        like1 = Like.builder()
            .user(user1)
            .build();
        like1.assignPost(post1);
        likeRepository.save(like1);

        like2 = Like.builder()
            .user(user2)
            .build();
        like2.assignPost(post1);
        likeRepository.save(like2);
    }

    private void assertIdAssigned() {
        assertThat(user1.getId()).isNotNull();
        assertThat(user2.getId()).isNotNull();

        assertThat(post1.getId()).isNotNull();
        assertThat(post2.getId()).isNotNull();
        assertThat(post3.getId()).isNotNull();

        assertThat(comment1.getId()).isNotNull();
        assertThat(comment2.getId()).isNotNull();

        assertThat(image1.getId()).isNotNull();
        assertThat(image2.getId()).isNotNull();

        assertThat(like1.getId()).isNotNull();
        assertThat(like2.getId()).isNotNull();
    }

    @DisplayName("Post를 삭제하면, 안에 있는 Comment, Like 들은 모두 삭제되고, Image는 삭제되지 않는다.")
    @Test
    void deletePost() {
        //given
        //when
        imageRepository.deleteAll(Arrays.asList(image1, image2));
        postRepository.delete(post1);
        resetEntityManager();
        //then
        assertThat(userRepository.findById(user1.getId())).isNotEmpty();
        assertThat(userRepository.findById(user2.getId())).isNotEmpty();

        assertThat(postRepository.findById(post1.getId())).isEmpty();

        assertThat(commentRepository.findById(comment1.getId())).isEmpty();
        assertThat(commentRepository.findById(comment2.getId())).isEmpty();

        assertThat(likeRepository.findById(like1.getId())).isEmpty();
        assertThat(likeRepository.findById(like2.getId())).isEmpty();
    }

    @DisplayName("Post Collection 에서 Comment, Like, Image 를 제거해도 DB에서 삭제되지 않는다.")
    @Test
    void deleteFromPost() {
        //given
        //when
        post1.deleteLike(user1.getId());
        post1.deleteComment(comment1.getId(), user1);
        post1.getImages().getImages().clear();
        resetEntityManager();
        //then
        assertThat(post1.hasImages()).isFalse();

        assertThat(userRepository.findById(user1.getId())).isNotEmpty();
        assertThat(userRepository.findById(user2.getId())).isNotEmpty();

        assertThat(postRepository.findById(post1.getId())).isNotEmpty();

        assertThat(commentRepository.findById(comment1.getId())).isNotEmpty();
        assertThat(commentRepository.findById(comment2.getId())).isNotEmpty();

        assertThat(likeRepository.findById(like1.getId())).isNotEmpty();
        assertThat(likeRepository.findById(like2.getId())).isNotEmpty();

        assertThat(imageRepository.findById(image1.getId())).isNotEmpty();
        assertThat(imageRepository.findById(image2.getId())).isNotEmpty();
    }

    @DisplayName("Image 삭제 쿼리 확인 테스트")
    @Test
    void deleteImage() {
        //given
        //when
        post1.getImages().getImages().clear();
        //then
    }

    @DisplayName("백신 타입별 페이징 조회")
    @Test
    void findByVaccineTypePaging() {
        //given
        //when
        List<Post> posts = postRepository.findByVaccineType(VaccinationType.PFIZER, 0, 3, Sort.CREATED_AT_DESC.getSort());
        //then
        final List<Long> extractedIds = Arrays.asList(post3.getId(), post1.getId());
        assertThat(posts).extracting("id").containsExactlyElementsOf(extractedIds);
        final List<String> extractedContents = Arrays.asList(post3.getContent(), post1.getContent());
        assertThat(posts).extracting("content").containsExactlyElementsOf(extractedContents);
    }

    @DisplayName("백신 타입별 조회 - 특정 타입 검색")
    @Test
    void findByVaccineType() {
        //given
        //when
        List<Post> posts = postRepository.findByVaccineType(VaccinationType.MODERNA);
        //then
        assertThat(posts).hasSize(1);
        assertThat(posts).extracting("content").containsExactlyElementsOf(Collections.singletonList(post2.getContent()));
    }

    @DisplayName("백신 타입별 조회 - 백신타입이 주어지지 않은 경우 모든 타입 검색")
    @Test
    void findByVaccineTypeNull() {
        //given
        //when
        List<Post> posts = postRepository.findByVaccineType(null);
        //then
        assertThat(posts).hasSize(3);
        final List<String> extractedContents = Arrays.asList(post3.getContent(), post2.getContent(), post1.getContent());
        assertThat(posts).extracting("content").containsExactlyElementsOf(extractedContents);
    }

    @DisplayName("백신 타입별 조회 - All로 주어지는 경우 모든 타입 검색")
    @Test
    void findByVaccineTypeAll() {
        //given
        //when
        List<Post> posts = postRepository.findByVaccineType(VaccinationType.ALL);
        //then
        assertThat(posts).hasSize(3);
        final List<String> extractedContents = Arrays.asList(post3.getContent(), post2.getContent(), post1.getContent());
        assertThat(posts).extracting("content").containsExactlyElementsOf(extractedContents);
    }

    @DisplayName("작성자 ID로 게시글 페이징 조회")
    @Test
    void findByUserId() {
        //given
        //when
        List<Post> postsByUser1 = postRepository.findByUserId(user1.getId(), 0, 10);
        //then
        assertThat(postsByUser1).hasSize(2);
        assertThat(postsByUser1).extracting("id").containsExactlyInAnyOrder(post1.getId(), post3.getId());
    }

    @DisplayName("작성자 ID로 게시글 조회")
    @Test
    void name() {
        //given
        //when
        List<Post> postsByUser1 = postRepository.findByUserId(user1.getId());
        //then
        assertThat(postsByUser1).hasSize(2);
        assertThat(postsByUser1).extracting("id").containsExactlyInAnyOrder(post1.getId(), post3.getId());
    }

    @DisplayName("게시글 좋아요 검색")
    @Test
    void findWithLikesByPostId() {
        //given
        //when
        Optional<Post> posts = postRepository.findWithLikesByPostId(post1.getId());
        Post post = posts.get();
        //then
        assertThat(post.getLikesCount()).isEqualTo(2);
        assertThat(post.getLikes().getLikes()).extracting("user.id").containsExactlyInAnyOrder(user1.getId(), user2.getId());
    }

    @DisplayName("게시글 댓글 검색")
    @Test
    void findWithCommentsByPostId() {
        //given
        //when
        Optional<Post> posts = postRepository.findWithCommentsByPostId(post1.getId());
        Post post = posts.get();
        //then
        assertThat(post.getComments().getComments()).hasSize(2);
        assertThat(post.getComments().getComments()).extracting("content").containsExactlyInAnyOrder(comment1.getContent(), comment2.getContent());
    }

    private void resetEntityManager() {
        entityManager.flush();
        entityManager.clear();
    }
}
