package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.RequestUser;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("게시글 비즈니스 흐름 테스트")
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;
    private User user;
    private User anotherUser;
    private Post post;
    private PostRequest postRequest;
    private CommentRequest commentRequest;

    static Stream<Arguments> findByVaccineType() {
        return Stream.of(
                Arguments.of(VaccinationType.PFIZER),
                Arguments.of(VaccinationType.ASTRAZENECA),
                Arguments.of(VaccinationType.MODERNA),
                Arguments.of(VaccinationType.JANSSEN)
        );
    }

    @BeforeEach
    void init() {
        user = User.builder()
                .nickname("테스트유저")
                .ageRange(AgeRange.FORTIES)
                .profileUrl("")
                .socialProvider(SocialProvider.NAVER)
                .build();
        anotherUser = User.builder()
                .nickname("다른테스트유저")
                .ageRange(AgeRange.TWENTIES)
                .profileUrl("")
                .socialProvider(SocialProvider.KAKAO)
                .build();
        userRepository.saveAll(Arrays.asList(user, anotherUser));

        post = Post.builder()
                .content("Test Content111")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        postRepository.save(post);

        postRequest = new PostRequest("Test Content222", VaccinationType.PFIZER);
        commentRequest = new CommentRequest("방귀대장 라뿡연훈이");
    }

    @DisplayName("게시글 생성 - 성공")
    @Test
    void create() {
        //given
        //when
        PostResponse postResponse = postService.create(user.getId(), postRequest);
        Post foundPost = postRepository.findById(postResponse.getId())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        //then
        assertThat(postResponse.getWriter().getId()).isEqualTo(user.getId());
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
        assertThatThrownBy(() -> postService.create(0L, postRequest))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 단일 조회 - 성공")
    @Test
    void findById() {
        //given
        //when
        PostResponse response = postService.findById(post.getId());
        //then
        assertThat(response.getId()).isEqualTo(post.getId());
    }

    @DisplayName("게시글 단일 조회 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void findByIdFailureWhenPostIsNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.findById(0L))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 전체 조회 - 성공")
    @Test
    void findAll() {
        //given
        post = Post.builder()
                .content("Last Content")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        postRepository.save(post);
        //when
        List<PostResponse> response = postService.findByVaccineType(VaccinationType.ALL);
        //then
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getContent()).isEqualTo("Last Content");
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void update() {
        //given
        PostRequest changedRequest = new PostRequest("change content", postRequest.getVaccinationType());
        RequestUser requestUser = RequestUser.of(user.getId());
        //when
        postService.update(post.getId(), requestUser, changedRequest);
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        //then
        assertThat(changedPost.getContent()).isEqualTo(changedRequest.getContent());
    }

    @DisplayName("게시글 수정 - 실패 - 찾을 수 없는 게시글")
    @Test
    void updateFailureWhenCannotFind() {
        //given
        PostRequest changedContent = new PostRequest("changed content", postRequest.getVaccinationType());
        RequestUser requestUser = RequestUser.of(user.getId());
        //when
        //then
        assertThatThrownBy(() -> postService.update(0L, requestUser, changedContent))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 수정 - 실패 - 다른 작성자의 게시글")
    @Test
    void updateFailureWhenOthersPost() {
        //given
        PostRequest changedContent = new PostRequest("changed content", postRequest.getVaccinationType());
        User anotherUser = User.builder()
                .nickname("어나더사용자")
                .build();
        //when
        userRepository.save(anotherUser);
        RequestUser requestUser = RequestUser.of(anotherUser.getId());
        //then
        assertThatThrownBy(() -> postService.update(post.getId(), requestUser, changedContent))
                .isExactlyInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void delete() {
        //given
        RequestUser requestUser = RequestUser.of(user.getId());
        //when
        postService.delete(post.getId(), requestUser);
        Optional<Post> foundPost = postRepository.findById(post.getId());
        //then
        assertThat(foundPost).isEmpty();
    }

    @DisplayName("게시글 삭제 - 성공 - 댓글도 함꼐 삭제 되는지 확인")
    @Test
    void deleteWithComments() {
        //given
        RequestUser requestUser = RequestUser.of(user.getId());
        //when
        CommentResponse commentResponse = postService.createComment(post.getId(), requestUser, commentRequest);
        postService.delete(post.getId(), requestUser);
        Optional<Post> foundPost = postRepository.findById(post.getId());
        Optional<Comment> foundComment = commentRepository.findById(commentResponse.getId());
        //then
        assertThat(foundPost).isEmpty();
        assertThat(foundComment).isEmpty();
    }

    @DisplayName("게시글 삭제 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void deleteFailureWhenPostIsNotExists() {
        //given
        RequestUser requestUser = RequestUser.of(user.getId());
        //when
        //then
        assertThatThrownBy(() -> postService.delete(0L, requestUser))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 작성자 확인 - 실패 - 글 작성자가 아님")
    @Test
    void deletePostFailureWhenNotAuthor() {
        //given
        PostRequest postRequest = new PostRequest("변경할 내용", VaccinationType.MODERNA);
        User anotherUser = User.builder()
                .nickname("어나더사용자")
                .build();
        userRepository.save(anotherUser);
        RequestUser requestUser = RequestUser.of(anotherUser.getId());
        //when
        //then
        assertThatThrownBy(() -> postService.update(post.getId(), requestUser, postRequest))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 타입별 조회 - 성공")
    @ParameterizedTest
    @MethodSource
    void findByVaccineType(VaccinationType vaccinationType) {
        //given
        post = Post.builder()
                .content("Test Content222")
                .vaccinationType(vaccinationType)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        postRepository.save(post);

        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType);
        //then
        assertThat(postResponses).filteredOn(
                response -> response.getVaccinationType().equals(vaccinationType)
        );
    }

    @DisplayName("댓글 생성 - 성공")
    @Test
    void createComment() {
        //given
        RequestUser requestUser = RequestUser.of(user.getId());
        //when
        CommentResponse commentResponse = postService.createComment(post.getId(), requestUser, commentRequest);
        //then
        assertThat(commentResponse.getContent()).isEqualTo(commentRequest.getContent());
        assertThat(commentResponse.getWriter().getId()).isEqualTo(user.getId());
        assertThat(post.getCommentsAsList()).extracting("id").contains(commentResponse.getId());
    }

    @DisplayName("댓글 생성 - 실패 - 비회원 댓글 작성 시도")
    @Test
    void createCommentWhenNotLoginUser() {
        //given
        RequestUser requestUser = RequestUser.guest();
        //when
        //then
        assertThatThrownBy(() -> postService.createComment(post.getId(), requestUser, commentRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("id는 null이 될 수 없습니다.");
    }

    @DisplayName("댓글 수정 - 성공")
    @Test
    void updateComment() {
        //given
        RequestUser requestUser = RequestUser.of(user.getId());

        CommentResponse commentResponse = postService.createComment(post.getId(), requestUser, commentRequest);
        CommentRequest updateRequest = new CommentRequest("저녁 술 ㄱ?");
        //when
        postService.updateComment(post.getId(), commentResponse.getId(), requestUser, updateRequest);
        Comment comment = commentRepository.findById(commentResponse.getId()).get();
        //then
        assertThat(comment.getContent()).isEqualTo(updateRequest.getContent());
    }

    @DisplayName("댓글 수정 - 실패 - 댓글을 찾을 수 없음")
    @Test
    void updateCommentWhenNoComment() {
        //given
        RequestUser requestUser = RequestUser.of(user.getId());

        CommentRequest updateRequest = new CommentRequest("저녁 술 ㄱ?");
        //when
        //then
        assertThatThrownBy(() -> postService.updateComment(post.getId(), 0L, requestUser, updateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("찾을 수 없는 댓글입니다.");
    }

    @DisplayName("댓글 수정 - 실패 - 댓글 작성자가 아님")
    @Test
    void updateCommentWhenWrongUser() {
        //given
        RequestUser requestUser = RequestUser.of(user.getId());
        RequestUser anotherRequestUser = RequestUser.of(anotherUser.getId());

        CommentResponse commentResponse = postService.createComment(post.getId(), requestUser, commentRequest);
        CommentRequest updateRequest = new CommentRequest("저녁 술 ㄱ?");
        //when
        //then
        assertThatThrownBy(() -> postService.updateComment(post.getId(), commentResponse.getId(), anotherRequestUser, updateRequest))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessage("다른 사용자의 게시글은 수정할 수 없습니다.");
    }

    @DisplayName("댓글 삭제 - 성공")
    @Test
    void deleteComment() {
        //given
        RequestUser requestUser = RequestUser.of(user.getId());
        CommentResponse commentResponse = postService.createComment(post.getId(), requestUser, commentRequest);
        //when
        postService.deleteComment(post.getId(), commentResponse.getId(), requestUser);
        postRepository.flush();

        Optional<Comment> foundComment = commentRepository.findById(commentResponse.getId());
        Post foundPost = postRepository.findById(this.post.getId()).get();
        //then
        assertThat(foundComment).isEmpty();
        assertThat(foundPost.getCommentsAsList()).extracting("id").doesNotContain(commentResponse.getId());
    }

    @DisplayName("댓글 삭제 - 실패 - 댓글을 찾을 수 없음")
    @Test
    void deleteCommentWhenNoComment() {
        //given
        RequestUser requestUser = RequestUser.of(user.getId());
        //when
        //then
        assertThatThrownBy(() -> postService.deleteComment(post.getId(), 0L, requestUser))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("찾을 수 없는 댓글입니다.");
    }

    @DisplayName("댓글 삭제 - 실패 - 댓글 작성자가 아님")
    @Test
    void deleteCommentWhenWrongUser() {
        //given
        RequestUser requestUser = RequestUser.of(user.getId());
        RequestUser anotherRequestUser = RequestUser.of(anotherUser.getId());

        CommentResponse commentResponse = postService.createComment(post.getId(), requestUser, commentRequest);
        //when
        //then
        assertThatThrownBy(() -> postService.deleteComment(post.getId(), commentResponse.getId(), anotherRequestUser))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessage("다른 사용자의 게시글은 삭제할 수 없습니다.");
    }
}
