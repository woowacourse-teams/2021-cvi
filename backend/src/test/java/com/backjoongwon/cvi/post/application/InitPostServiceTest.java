package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.LikeResponse;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("게시글 비지니스 테스트 초기 설정")
public class InitPostServiceTest {

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

    protected User user1;
    protected User user2;
    protected User anotherUser;
    protected Optional<User> optionalUser;
    protected Optional<User> optionalAnotherUser;
    protected Post post1;
    protected Post post2;
    protected Post post3;
    protected Post post4;
    protected Post post5;
    protected Post post6;
    protected Post post7;
    protected PostRequest postRequest;
    protected LikeResponse likeResponse;
    protected CommentRequest commentRequest;

    @BeforeEach
    void init() {
        initUser();
        initPost();
        likeResponse = postService.createLike(post1.getId(), optionalUser);
        postService.createLike(post3.getId(), optionalUser);
        postService.createLike(post5.getId(), optionalUser);
        postRequest = new PostRequest("Test Content222", VaccinationType.PFIZER);
        commentRequest = new CommentRequest("방귀대장 라뿡연훈이");
        postService.createComment(post1.getId(), optionalUser, commentRequest);
        postService.createComment(post2.getId(), optionalUser, commentRequest);
        postService.createComment(post3.getId(), optionalUser, commentRequest);
        postService.createComment(post4.getId(), optionalUser, commentRequest);
        postService.createComment(post5.getId(), optionalUser, commentRequest);
    }

    private void initPost() {
        post1 = Post.builder()
                .content("Test 0")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();
        post2 = Post.builder()
                .content("Test 1")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();
        post3 = Post.builder()
                .content("Test 2")
                .vaccinationType(VaccinationType.PFIZER)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();
        post4 = Post.builder()
                .content("Test 3")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();
        post5 = Post.builder()
                .content("Test 4")
                .vaccinationType(VaccinationType.JANSSEN)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();
        post6 = Post.builder()
                .content("Test 5")
                .vaccinationType(VaccinationType.JANSSEN)
                .user(user2)
                .createdAt(LocalDateTime.now())
                .build();
        post7 = Post.builder()
                .content("Test 6")
                .vaccinationType(VaccinationType.PFIZER)
                .user(user2)
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.saveAll(Arrays.asList(post1, post2, post3, post4, post5, post6, post7));
    }

    private void initUser() {
        user1 = User.builder()
                .nickname("테스트유저")
                .ageRange(AgeRange.FORTIES)
                .profileUrl("")
                .socialProvider(SocialProvider.NAVER)
                .build();
        user2 = User.builder()
                .nickname("테스트유저2")
                .ageRange(AgeRange.THIRTIES)
                .profileUrl("")
                .socialProvider(SocialProvider.NAVER)
                .build();
        anotherUser = User.builder()
                .nickname("다른테스트유저")
                .ageRange(AgeRange.TWENTIES)
                .profileUrl("")
                .socialProvider(SocialProvider.KAKAO)
                .build();
        userRepository.saveAll(Arrays.asList(user1, user2, anotherUser));

        optionalUser = Optional.of(user1);
        optionalAnotherUser = Optional.of(anotherUser);
    }
}
