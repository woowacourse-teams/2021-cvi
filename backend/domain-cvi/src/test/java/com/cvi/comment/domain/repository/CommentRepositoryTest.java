package com.cvi.comment.domain.repository;

import com.cvi.comment.domain.model.Comment;
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

@DisplayName("CommentRepository 테스트")
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private User user1;
    private User user2;
    private Comment comment1;
    private Comment comment2;
    private Comment comment3;
    private Post post1;

    @BeforeEach
    void setUp() {
        initUsers();
        initPost();
        initComments();
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
        postRepository.save(post1);
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

        comment3 = Comment.builder()
            .content("댓글 내용3")
            .user(user2)
            .build();
        comment3.assignPost(post1);
        commentRepository.save(comment3);
    }

    private void assertIdAssigned() {
        assertThat(comment1.getId()).isNotNull();
        assertThat(comment2.getId()).isNotNull();
        assertThat(comment3.getId()).isNotNull();

        assertThat(post1.getId()).isNotNull();
    }

    @DisplayName("유저 아이디로 댓글을 조회한다 - 성공")
    @Test
    void findCommentByUserId() {
        //given
        //when
        //then
        assertThat(commentRepository.findByUserId(user1.getId())).hasSize(1);
        assertThat(commentRepository.findByUserId(user2.getId())).hasSize(2);
    }

    @DisplayName("유저 아이디로 댓글을 조회한다 - 성공 - null인 경우")
    @Test
    void findCommentByUserIdWhenNull() {
        //given
        //when
        //then
        assertThat(commentRepository.findByUserId(null)).hasSize(3);
    }

    @DisplayName("유저 아이디로 댓글을 페이징 조회한다 - 성공")
    @Test
    void findCommentByUserIdPaging() {
        //given
        //when
        //then
        assertThat(commentRepository.findByUserId(user1.getId(), 0, 2)).hasSize(1);
        assertThat(commentRepository.findByUserId(user2.getId(), 0, 2)).hasSize(2);
    }

    @DisplayName("유저 아이디로 댓글을 페이징 조회한다 - 성공 - null인 경우")
    @Test
    void findCommentByUserIdPagingWhenNull() {
        //given
        //when
        //then
        assertThat(commentRepository.findByUserId(null, 0, 2)).hasSize(2);
    }
}
