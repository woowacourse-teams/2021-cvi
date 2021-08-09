package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@DisplayName("게시글 - 생성/수정/삭제 비즈니스 흐름 테스트")
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    private User user;
    private User anotherUser;
    private Optional<User> optionalUser;
    private Optional<User> optionalAnotherUser;
    private Optional<User> optionalUserNotSignedIn;
    private Post post;
    private Post anotherPost;
    private PostRequest postRequest;

    @BeforeEach
    void init() {
        initUser();
        initPost();
        postRequest = new PostRequest("Test Content", VaccinationType.PFIZER);
    }

    private void initUser() {
        user = User.builder()
                .nickname("테스트유저")
                .ageRange(AgeRange.FORTIES)
                .socialProvider(SocialProvider.NAVER)
                .socialId("NAVER_ID")
                .profileUrl("naver.com/profile")
                .build();
        anotherUser = User.builder()
                .nickname("다른유저")
                .ageRange(AgeRange.FORTIES)
                .socialProvider(SocialProvider.NAVER)
                .socialId("NAVER_ID")
                .profileUrl("naver.com/profile")
                .build();
        optionalUser = Optional.of(user);
        optionalAnotherUser = Optional.of(anotherUser);
        optionalUserNotSignedIn = Optional.empty();
        userRepository.saveAll(Arrays.asList(user, anotherUser));
    }

    private void initPost() {
        post = Post.builder()
                .content("Test")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        anotherPost = Post.builder()
                .content("Test")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(anotherUser)
                .createdAt(LocalDateTime.now())
                .build();
        postRepository.saveAll(Arrays.asList(post, anotherPost));
    }

    @DisplayName("게시글 생성 - 성공")
    @Test
    void create() {
        //given
        //when
        PostResponse createResponse = postService.create(optionalUser, postRequest);
        Post foundPost = postRepository.findById(createResponse.getId())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        //then
        assertThat(createResponse.getWriter().getId()).isEqualTo(user.getId());
        assertThat(createResponse.getContent()).isEqualTo(postRequest.getContent());
        assertThat(foundPost).isNotNull();
        assertThat(foundPost.getUser()).isNotNull();
    }

    @DisplayName("게시글 생성 - 실패 - 존재하지 않는 유저")
    @Test
    void createFailureWhenWriterIsNull() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.create(optionalUserNotSignedIn, postRequest))
                .isExactlyInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("게시글 단일 조회 - 성공")
    @Test
    void findById() {
        //given
        //when
        PostResponse findResponse = postService.findById(post.getId(), optionalUser);
        //then
        assertThat(findResponse.getId()).isEqualTo(post.getId());
    }

    @DisplayName("게시글 단일 조회 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void findByIdFailureWhenPostIsNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.findById(Long.MAX_VALUE, optionalUser))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 전체 조회 - 성공")
    @Test
    void findAll() {
        //given
        //when
        List<PostResponse> findResponse = postService.findByVaccineType(VaccinationType.ALL, optionalUser);
        //then
        assertThat(findResponse).hasSize(2);
        assertThat(findResponse.get(0).getContent()).isEqualTo(post.getContent());
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void update() {
        //given
        PostRequest updateRequest = new PostRequest("updated content", postRequest.getVaccinationType());
        //when
        postService.update(post.getId(), optionalUser, updateRequest);
        //then
        Post updatedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        assertThat(updatedPost.getContent()).isEqualTo(updateRequest.getContent());
    }

    @DisplayName("게시글 수정 - 실패 - 찾을 수 없는 게시글")
    @Test
    void updateFailureWhenCannotFind() {
        //given
        PostRequest changedContent = new PostRequest("updated content", postRequest.getVaccinationType());
        //when
        //then
        assertThatThrownBy(() -> postService.update(Long.MAX_VALUE, optionalUser, changedContent))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 수정 - 실패 - 다른 작성자의 게시글")
    @Test
    void updateFailureWhenOthersPost() {
        //given
        PostRequest changedContent = new PostRequest("changed content", postRequest.getVaccinationType());
        //when
        //then
        assertThatThrownBy(() -> postService.update(post.getId(), optionalAnotherUser, changedContent))
                .isExactlyInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void delete() {
        //given
        //when
        postService.delete(post.getId(), optionalUser);
        Optional<Post> foundPost = postRepository.findById(post.getId());
        //then
        assertThat(foundPost).isEmpty();
    }

    @DisplayName("게시글 삭제 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void deleteFailureWhenPostIsNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.delete(Long.MAX_VALUE, optionalUser))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 삭제 - 실패 - 글 작성자가 아님")
    @Test
    void deletePostFailureWhenNotAuthor() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.delete(post.getId(), optionalAnotherUser))
                .isExactlyInstanceOf(InvalidOperationException.class);
    }
}
