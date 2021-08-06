package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @PersistenceContext
    private EntityManager em;

    protected User user1;
    protected User user2;
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

    @BeforeEach
    void init() {
        List<User> users = initUser();
        List<Post> posts = initPost();
        initLike(users, posts);
        postRequest = new PostRequest("Test Content222", VaccinationType.PFIZER);
        commentRequest = new CommentRequest("방귀대장 라뿡연훈이");
        postService.createComment(post0.getId(), optionalUserNoLikesAndComment, commentRequest);
        postService.createComment(post1.getId(), optionalUserNoLikesAndComment, commentRequest);
        postService.createComment(post2.getId(), optionalUserNoLikesAndComment, commentRequest);
        postService.createComment(post3.getId(), optionalUserNoLikesAndComment, commentRequest);
        postService.createComment(post4.getId(), optionalUserNoLikesAndComment, commentRequest);
    }

    /**
     * 포스트0 -> 좋아요 안눌림
     * 포스트1 -> 좋아요 1개, 댓글 1개 (유저1)
     * 포스트2 -> 좋아요 2개, 댓글 2개 (유저1, 유저2)
     * .....
     * 포스트5 -> 좋아요 5개, 댓글 5개 (유저1, 유저2, 유저3, 유저4, 유저5)
     * 포스트6 -> 좋아요 6개, 댓글 6개 (유저1, 유저2, 유저3, 유저4, 유저5, 유저6)
     */

    private void initLike(List<User> users, List<Post> posts) {
        List<Like> likes = new ArrayList<>();
        for (int i = 1; i < posts.size(); i++) {
            for (int j = 1; j <= i ; j++) {
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
            for (int j = 1; j <= i ; j++) {
                Comment comment = Comment.builder().content("댓글 " + j).user(users.get(j)).build();
                posts.get(i).assignComment(comment);
                comments.add(comment);
                commentRepository.save(comment);
            }
        }
    }

    private List<Post> initPost() {
        post0 = Post.builder()
                .content("Test 0")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();
        post1 = Post.builder()
                .content("Test 1")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();
        post2 = Post.builder()
                .content("Test 2")
                .vaccinationType(VaccinationType.PFIZER)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();
        post3 = Post.builder()
                .content("Test 3")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();
        post4 = Post.builder()
                .content("Test 4")
                .vaccinationType(VaccinationType.JANSSEN)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();
        post5 = Post.builder()
                .content("Test 5")
                .vaccinationType(VaccinationType.JANSSEN)
                .user(user2)
                .createdAt(LocalDateTime.now())
                .build();
        post6 = Post.builder()
                .content("Test 6")
                .vaccinationType(VaccinationType.PFIZER)
                .user(user2)
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

        user1 = users.get(0);
        user2 = users.get(1);

        optionalUserNoLikesAndComment = Optional.of(user1);
        optionalUserWithLikesAndComment = Optional.of(user2);

        return users;
    }
}
