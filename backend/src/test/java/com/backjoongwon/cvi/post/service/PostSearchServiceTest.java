package com.backjoongwon.cvi.post.service;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.aws.s3.AwsS3Uploader;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.SearchType;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostResponse;
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

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("게시글 - 검색 비즈니스 흐름 테스트")
public class PostSearchServiceTest {

    public static final String CONTENT = "Test";
    @MockBean
    private AwsS3Uploader awsS3Uploader;

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

    private User user;
    private Optional<User> optionalUser;
    private Post postWithCONTENTByUser0;
    private Post postWithCONTENTByUser1;
    private Post postWithCONTENTByUser2;
    private Post postByUser0;
    private Post postByUser1;
    private Post postByUser2;

    @BeforeEach
    void init() {
        List<User> users = initUser();
        initPost(users);
    }

    private List<User> initUser() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User dummyUser = User.builder()
                    .nickname("테스트유저" + i)
                    .ageRange(AgeRange.TWENTIES)
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

    private void initPost(List<User> users) {
        postWithCONTENTByUser0 = Post.builder()
                .content(CONTENT + " 0")
                .vaccinationType(VaccinationType.MODERNA)
                .user(users.get(0))
                .createdAt(LocalDateTime.now())
                .build();
        postWithCONTENTByUser1 = Post.builder()
                .content(CONTENT + " 1")
                .vaccinationType(VaccinationType.JANSSEN)
                .user(users.get(1))
                .createdAt(LocalDateTime.now())
                .build();
        postWithCONTENTByUser2 = Post.builder()
                .content(CONTENT + " 2")
                .vaccinationType(VaccinationType.PFIZER)
                .user(users.get(2))
                .createdAt(LocalDateTime.now())
                .build();
        postByUser0 = Post.builder()
                .content("유저0의 내용")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(users.get(0))
                .createdAt(LocalDateTime.now())
                .build();
        postByUser1 = Post.builder()
                .content("유저1의 내용")
                .vaccinationType(VaccinationType.PFIZER)
                .user(users.get(1))
                .createdAt(LocalDateTime.now())
                .build();
        postByUser2 = Post.builder()
                .content("유저2의 내용")
                .vaccinationType(VaccinationType.PFIZER)
                .user(users.get(2))
                .createdAt(LocalDateTime.now())
                .build();
        List<Post> posts = Arrays.asList(postWithCONTENTByUser0, postWithCONTENTByUser1, postWithCONTENTByUser2,
                postByUser0, postByUser1, postByUser2);
        postRepository.saveAll(posts);
    }

    @DisplayName("게시글 내용으로 검색 - 성공")
    @Test
    void searchPostByContent() {
        //given
        //when
        List<PostResponse> postResponses = postService.searchByTypesAndQuery(VaccinationType.ALL, SearchType.CONTENT, CONTENT, optionalUser);
        //then
        assertThat(postResponses).hasSize(3);
        assertThat(postResponses).extracting("content")
                .containsExactlyElementsOf(Arrays.asList(postWithCONTENTByUser2.getContent(), postWithCONTENTByUser1.getContent(), postWithCONTENTByUser0.getContent()));
    }

    @DisplayName("게시글 내용으로 검색 - 성공 - 해당하는 글이 없는 경우 빈 리스트를 반환한다.")
    @Test
    void searchPostByContentFailureWhenNotContainContent() {
        //given
        //when
        List<PostResponse> postResponses = postService.searchByTypesAndQuery(VaccinationType.ALL, SearchType.CONTENT, "없는 내용", optionalUser);
        //then
        assertThat(postResponses).hasSize(0);
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(Collections.emptyList());
    }

    @DisplayName("작성자로 검색 - 성공")
    @Test
    void searchPostByWriter() {
        //given
        //when
        List<PostResponse> postResponses = postService.searchByTypesAndQuery(VaccinationType.ALL, SearchType.WRITER, user.getNickname(), optionalUser);
        //then
        assertThat(postResponses).hasSize(2);
        assertThat(postResponses).extracting("content")
                .containsExactlyElementsOf(Arrays.asList(postByUser0.getContent(), postWithCONTENTByUser0.getContent()));
    }

    @DisplayName("작성자로 검색 - 성공 - 작성자의 글이 없는 경우 빈 리스트를 반환한다.")
    @Test
    void searchPostByWriterFailureWhenNotExists() {
        //given
        //when
        List<PostResponse> postResponses = postService.searchByTypesAndQuery(VaccinationType.ALL, SearchType.WRITER, "글이없는유저", optionalUser);
        //then
        assertThat(postResponses).hasSize(0);
        assertThat(postResponses).extracting("content").containsExactlyElementsOf(Collections.emptyList());
    }
}
