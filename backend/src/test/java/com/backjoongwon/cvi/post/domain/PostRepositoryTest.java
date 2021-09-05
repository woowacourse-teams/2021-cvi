package com.backjoongwon.cvi.post.domain;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.aws.s3.AwsS3Uploader;
import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.image.domain.Image;
import com.backjoongwon.cvi.image.domain.ImageRepository;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PostRepository 테스트")
@Transactional
@SpringBootTest
class PostRepositoryTest {

    @MockBean
    private AwsS3Uploader awsS3Uploader;

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
                .content("내용 1")
                .vaccinationType(VaccinationType.PFIZER)
                .build();
        postRepository.save(post1);
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
        assertThat(comment1.getId()).isNotNull();
        assertThat(comment2.getId()).isNotNull();

        assertThat(image1.getId()).isNotNull();
        assertThat(image2.getId()).isNotNull();

        assertThat(like1.getId()).isNotNull();
        assertThat(like2.getId()).isNotNull();

        assertThat(post1.getId()).isNotNull();
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
        post1.deleteAllImages();
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
        post1.deleteAllImages();
        //then
    }

    private void resetEntityManager() {
        entityManager.flush();
        entityManager.clear();
    }
}
