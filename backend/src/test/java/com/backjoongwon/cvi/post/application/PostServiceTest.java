package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.comment.dto.CommentRequest;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.LikeResponse;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private LikeRepository likeRepository;

    @Autowired
    private PostService postService;

    @PersistenceContext
    private EntityManager em;

    private User user;
    private User anotherUser;
    private Optional<User> optionalUser;
    private Optional<User> optionalAnotherUser;
    private Post post;
    private Post post1;
    private Post post2;
    private Post post3;
    private Post post4;
    private PostRequest postRequest;
    private LikeResponse likeResponse;
    private CommentRequest commentRequest;

    @BeforeEach
    void init() {
        initUser();
        initPost();
        likeResponse = postService.createLike(post.getId(), optionalUser);
        postRequest = new PostRequest("Test Content222", VaccinationType.PFIZER);
        commentRequest = new CommentRequest("방귀대장 라뿡연훈이");
        postService.createComment(post.getId(), optionalUser, commentRequest);
        postService.createComment(post.getId(), optionalUser, commentRequest);
    }

    private void initPost() {
        post = Post.builder()
                .content("Test 0")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        post1 = Post.builder()
                .content("Test 1")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        post2 = Post.builder()
                .content("Test 2")
                .vaccinationType(VaccinationType.PFIZER)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        post3 = Post.builder()
                .content("Test 3")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        post4 = Post.builder()
                .content("Test 4")
                .vaccinationType(VaccinationType.JANSSEN)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.saveAll(Arrays.asList(post, post1, post2, post3, post4));
    }

    private void initUser() {
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
        optionalUser = Optional.of(user);
        optionalAnotherUser = Optional.of(anotherUser);
    }

    @DisplayName("게시글 생성 - 성공")
    @Test
    void create() {
        //given
        //when
        PostResponse postResponse = postService.create(optionalUser, postRequest);
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
        assertThatThrownBy(() -> postService.create(Optional.empty(), postRequest))
                .isExactlyInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("게시글 단일 조회 - 성공")
    @Test
    void findById() {
        //given
        //when
        PostResponse response = postService.findById(post.getId(), optionalUser);
        //then
        assertThat(response.getId()).isEqualTo(post.getId());
    }

    @DisplayName("게시글 단일 조회 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void findByIdFailureWhenPostIsNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.findById(0L, optionalUser))
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
        List<PostResponse> response = postService.findByVaccineType(VaccinationType.ALL, optionalUser);
        //then
        assertThat(response).hasSize(6);
        assertThat(response.get(0).getContent()).isEqualTo("Last Content");
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void update() {
        //given
        PostRequest changedRequest = new PostRequest("change content", postRequest.getVaccinationType());
        postService.update(post.getId(), optionalUser, changedRequest);
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
        //when
        //then
        assertThatThrownBy(() -> postService.update(0L, optionalUser, changedContent))
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

    @DisplayName("게시글 삭제 - 성공 - 댓글도 함꼐 삭제 되는지 확인")
    @Test
    void deleteWithComments() {
        //given
        //when
        CommentResponse commentResponse = postService.createComment(post.getId(), optionalUser, commentRequest);
        postService.delete(post.getId(), optionalUser);
        Optional<Post> foundPost = postRepository.findById(post.getId());
        Optional<Comment> foundComment = commentRepository.findById(commentResponse.getId());
        //then
        assertThat(foundPost).isEmpty();
        assertThat(foundComment).isEmpty();
    }

    @DisplayName("게시글 삭제시 좋아요 삭제")
    @Test
    void deleteLikeWhenDeletePost() {
        //given
        //when
        postService.delete(post.getId(), optionalUser);
        //then
        assertThat(likeRepository.findById(likeResponse.getId())).isEmpty();
    }

    @DisplayName("게시글 삭제 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void deleteFailureWhenPostIsNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.delete(0L, optionalUser))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 작성자 확인 - 실패 - 글 작성자가 아님")
    @Test
    void deletePostFailureWhenNotAuthor() {
        //given
        PostRequest postRequest = new PostRequest("변경할 내용", VaccinationType.MODERNA);
        //when
        //then
        assertThatThrownBy(() -> postService.update(post.getId(), optionalAnotherUser, postRequest))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 타입별 조회 - 성공")
    @Test
    void findByVaccineType() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(VaccinationType.ASTRAZENECA, optionalUser);
        //then
        assertThat(postResponses).filteredOn(
                response -> response.getVaccinationType().equals(VaccinationType.ASTRAZENECA)
        );
    }

    @DisplayName("게시글 타입별 페이징 조회 - 성공")
    @Test
    void findByVaccineTypePaging() {
        //given
        //when
        List<PostResponse> postResponses1 = postService.findByVaccineType(VaccinationType.ASTRAZENECA, Long.MAX_VALUE, 10, optionalUser);
        List<PostResponse> postResponses2 = postService.findByVaccineType(VaccinationType.ASTRAZENECA, Long.MAX_VALUE, 2, optionalUser);
        List<PostResponse> postResponses3 = postService.findByVaccineType(VaccinationType.JANSSEN, Long.MAX_VALUE, 5, optionalUser);
        List<PostResponse> postResponses4 = postService.findByVaccineType(VaccinationType.PFIZER, Long.MAX_VALUE, 5, optionalUser);
        List<PostResponse> postResponses5 = postService.findByVaccineType(VaccinationType.ALL, Long.MAX_VALUE, 3, optionalUser);
        //then
        assertThat(postResponses1).size().isEqualTo(3);
        assertThat(postResponses1).extracting("content").containsExactlyElementsOf(Arrays.asList("Test 3", "Test 1", "Test 0"));
        assertThat(postResponses1).extracting("vaccinationType").containsOnly(VaccinationType.ASTRAZENECA);

        assertThat(postResponses2).size().isEqualTo(2);
        assertThat(postResponses2).extracting("content").containsExactlyElementsOf(Arrays.asList("Test 3", "Test 1"));
        assertThat(postResponses2).extracting("vaccinationType").containsOnly(VaccinationType.ASTRAZENECA);

        assertThat(postResponses3).size().isEqualTo(1);
        assertThat(postResponses3).extracting("content").containsExactlyElementsOf(Arrays.asList("Test 4"));
        assertThat(postResponses3).extracting("vaccinationType").containsOnly(VaccinationType.JANSSEN);

        assertThat(postResponses4).size().isEqualTo(1);
        assertThat(postResponses4).extracting("content").containsExactlyElementsOf(Arrays.asList("Test 2"));
        assertThat(postResponses4).extracting("vaccinationType").containsOnly(VaccinationType.PFIZER);

        assertThat(postResponses5).size().isEqualTo(3);
        assertThat(postResponses5).extracting("content").containsExactlyElementsOf(Arrays.asList("Test 4", "Test 3", "Test 2"));
        assertThat(postResponses5).extracting("vaccinationType").filteredOn(vaccinationType -> vaccinationType instanceof VaccinationType);
    }

    @DisplayName("게시글 좋아요 생성 - 성공")
    @Test
    void createLike() {
        //given
        //when
        Long postId = post.getId();
        postService.createLike(postId, optionalAnotherUser);
        Post post = postRepository.findWithLikesById(postId).get();
        //then
        assertThat(post.getLikesCount()).isEqualTo(2);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 게시글이 없는 경우")
    @Test
    void createLikeFailureWhenPostNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createLike(Long.MAX_VALUE, optionalAnotherUser))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 동일한 유저가 이미 좋아요를 누른 경우")
    @Test
    void createLikeFailureWhenAlreadyCreatedBySameUser() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createLike(post.getId(), optionalUser))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 좋아요 삭제 - 성공")
    @Test
    void deleteLike() {
        //given
        //when
        postService.deleteLike(post.getId(), optionalUser);
        Post actualPost = postRepository.findWithLikesById(this.post.getId())
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        //then
        assertThat(actualPost.getLikes().getLikes()).isEmpty();
    }

    @DisplayName("게시글 좋아요 삭제 - 실패 - 다른 유저인 경우 ")
    @Test
    void deleteLikeFailureWhenInvalidToken() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.deleteLike(post.getId(), optionalAnotherUser))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 좋아요 삭제 - 실패 - 삭제 요청한 좋아요가 해당 게시글에 없는 경우")
    @Test
    void deleteLikeFailureWhenLikeNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.deleteLike(post.getId() + 1, optionalUser))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("댓글 생성 - 성공")
    @Test
    void createComment() {
        //given
        //when
        CommentRequest newCommentRequest = new CommentRequest("새로운 댓글 내용");
        CommentResponse commentResponse = postService.createComment(post.getId(), optionalAnotherUser, newCommentRequest);
        Post foundPost = postRepository.findWithCommentsById(post.getId())
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 없습니다."));
        //then
        assertThat(commentResponse.getContent()).isEqualTo(newCommentRequest.getContent());
        assertThat(commentResponse.getWriter().getId()).isEqualTo(anotherUser.getId());
        assertThat(foundPost.getCommentsAsList()).extracting("id").contains(commentResponse.getId());
    }

    @DisplayName("댓글 생성 - 실패 - 비회원 댓글 작성 시도")
    @Test
    void createCommentWhenNotLoginUser() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createComment(post.getId(), Optional.empty(), commentRequest))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("댓글 수정 - 성공")
    @Test
    void updateComment() {
        //given
        CommentResponse commentResponse = postService.createComment(post.getId(), optionalUser, commentRequest);
        CommentRequest updateRequest = new CommentRequest("저녁 술 ㄱ?");
        //when
        postService.updateComment(post.getId(), commentResponse.getId(), optionalUser, updateRequest);
        Comment comment = commentRepository.findById(commentResponse.getId()).get();
        //then
        assertThat(comment.getContent()).isEqualTo(updateRequest.getContent());
    }

    @DisplayName("댓글 수정 - 실패 - 댓글을 찾을 수 없음")
    @Test
    void updateCommentWhenNoComment() {
        //given
        CommentRequest updateRequest = new CommentRequest("저녁 술 ㄱ?");
        //when
        //then
        assertThatThrownBy(() -> postService.updateComment(post.getId(), 0L, optionalUser, updateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("찾을 수 없는 댓글입니다.");
    }

    @DisplayName("댓글 수정 - 실패 - 댓글 작성자가 아님")
    @Test
    void updateCommentWhenWrongUser() {
        //given
        CommentResponse commentResponse = postService.createComment(post.getId(), optionalUser, commentRequest);
        CommentRequest updateRequest = new CommentRequest("저녁 술 ㄱ?");
        //when
        //then
        assertThatThrownBy(() -> postService.updateComment(post.getId(), commentResponse.getId(), optionalAnotherUser, updateRequest))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessage("다른 사용자의 게시글은 수정할 수 없습니다.");
    }

    @DisplayName("댓글 삭제 - 성공")
    @Test
    void deleteComment() {
        //given
        CommentResponse commentResponse = postService.createComment(post.getId(), optionalUser, commentRequest);
        //when
        postService.deleteComment(post.getId(), commentResponse.getId(), optionalUser);
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
        //when
        //then
        assertThatThrownBy(() -> postService.deleteComment(post.getId(), 0L, optionalUser))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("찾을 수 없는 댓글입니다.");
    }

    @DisplayName("댓글 삭제 - 실패 - 댓글 작성자가 아님")
    @Test
    void deleteCommentWhenWrongUser() {
        //given
        CommentResponse commentResponse = postService.createComment(post.getId(), optionalUser, commentRequest);
        //when
        //then
        assertThatThrownBy(() -> postService.deleteComment(post.getId(), commentResponse.getId(), optionalAnotherUser))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessage("다른 사용자의 게시글은 삭제할 수 없습니다.");
    }

    @DisplayName("게시글에 좋아요를 누른 후 본인이 다시 해당 글을 조회하면 hasLiked값이 true로 조회된다.")
    @Test
    void hasLiked() {
        //given
        //when
        PostResponse postResponse = postService.findById(post.getId(), optionalUser);
        //then
        assertThat(postResponse.isHasLiked()).isTrue();
    }

    @DisplayName("게시글에 좋아요를 누르지 않은 사람이 해당 글을 조회하면 hasLiked값이 false로 조회된다.")
    @Test
    void notHasLiked() {
        //given
        //when
        PostResponse postResponse = postService.findById(post.getId(), optionalAnotherUser);
        //then
        assertThat(postResponse.isHasLiked()).isFalse();
    }
}
