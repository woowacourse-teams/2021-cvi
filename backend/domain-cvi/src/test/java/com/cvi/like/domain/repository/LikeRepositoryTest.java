package com.cvi.like.domain.repository;

import com.cvi.like.domain.model.Like;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.post.domain.repository.PostRepository;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LikeRepository 테스트")
@DataJpaTest
class LikeRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    private User user1;
    private User user2;
    private Like like1;
    private Like like2;
    private Like like3;
    private Post post1;
    private Post post2;

    @BeforeEach
    void setUp() {
        initUsers();
        initPost();
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
            .ageRange(AgeRange.FIFTIES)
            .socialProvider(SocialProvider.NAVER)
            .socialId("1001")
            .profileUrl("profile url 2")
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
        post2 = Post.builder()
            .user(user1)
            .content("내용 2")
            .vaccinationType(VaccinationType.JANSSEN)
            .build();
        postRepository.save(post1);
        postRepository.save(post2);
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

        like3 = Like.builder()
            .user(user2)
            .build();
        like3.assignPost(post2);
        likeRepository.save(like3);
    }

    private void assertIdAssigned() {
        assertThat(like1.getId()).isNotNull();
        assertThat(like2.getId()).isNotNull();
        assertThat(like3.getId()).isNotNull();

        assertThat(post1.getId()).isNotNull();
    }

    @DisplayName("유저 아이디로 좋아요를 조회한다 - 성공")
    @Test
    void findCommentByUserId() {
        //given
        //when
        //then
        assertThat(likeRepository.findByUserId(user1.getId())).hasSize(1);
        assertThat(likeRepository.findByUserId(user2.getId())).hasSize(2);
    }

    @DisplayName("유저 아이디로 댓글을 조회한다 - 실패")
    @Test
    void findCommentByUserIdWhenNull() {
        //given
        //when
        //then
        assertThat(likeRepository.findByUserId(null)).hasSize(3);
    }

    @DisplayName("유저 아이디로 좋아요를 페이징 조회한다 - 성공")
    @Test
    void findCommentByUserIdPaging() {
        //given
        //when
        //then
        assertThat(likeRepository.findByUserId(user1.getId(), 0, 2)).hasSize(1);
        assertThat(likeRepository.findByUserId(user2.getId(), 0, 2)).hasSize(2);
    }

    @DisplayName("유저 아이디로 댓글을 페이징 조회한다 - 성공 - null인 경우")
    @Test
    void findCommentByUserIdPagingWhenNull() {
        //given
        //when
        //then
        assertThat(likeRepository.findByUserId(null, 0, 2)).hasSize(2);
    }
}
