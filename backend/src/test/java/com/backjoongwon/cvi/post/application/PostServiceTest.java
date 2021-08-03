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
import com.backjoongwon.cvi.post.domain.Filter;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.LikeResponse;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.domain.AgeRange;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    private LikeRepository likeRepository;

    @Autowired
    private PostService postService;

    @PersistenceContext
    private EntityManager em;

    private User user1;
    private User user2;
    private User anotherUser;
    private Post post1;
    private Post post2;
    private Post post3;
    private Comment comment1;
    private Comment comment2;
    private Optional<User> optionalUser1;
    private Optional<User> optionalUser2;
    private Optional<User> optionalAnotherUser;
    private PostRequest postRequest;
    private LikeResponse likeResponse;
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

        optionalUser1 = Optional.of(user1);
        optionalUser2 = Optional.of(user2);
        optionalAnotherUser = Optional.of(anotherUser);

        post1 = Post.builder()
                .content("Test Content111")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();

        post2 = Post.builder()
                .content("Test Content222")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(anotherUser)
                .createdAt(LocalDateTime.now())
                .build();

        post3 = Post.builder()
                .content("Test Content333")
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();

        comment1 = Comment.builder().content("다른 유저의 댓글 입니다.").build();
        comment2 = Comment.builder().content("유저의 댓글 입니다.").build();
        comment1.assignUser(anotherUser);
        comment2.assignUser(user1);

        post1.assignComment(comment1);
        post1.assignComment(comment2);

        post2.assignComment(comment1);
        post2.assignComment(comment2);
        postRepository.saveAll(Arrays.asList(post1, post2, post3));

        likeResponse = postService.createLike(post1.getId(), optionalUser1);
        likeResponse = postService.createLike(post1.getId(), optionalUser2);

        postRequest = new PostRequest("Test Content222", VaccinationType.PFIZER);
        commentRequest = new CommentRequest("방귀대장 라뿡연훈이");

        postService.createComment(post1.getId(), optionalUser1, commentRequest);
        postService.createComment(post1.getId(), optionalUser2, commentRequest);
        resetEntityManager();
    }

    @DisplayName("게시글 생성 - 성공")
    @Test
    void create() {
        //given
        //when
        PostResponse postResponse = postService.create(optionalUser1, postRequest);
        Post foundPost = postRepository.findById(postResponse.getId())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        //then
        assertThat(postResponse.getWriter().getId()).isEqualTo(user1.getId());
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
        PostResponse response = postService.findById(post1.getId(), optionalUser1);
        //then
        assertThat(response.getId()).isEqualTo(post1.getId());
    }

    @DisplayName("게시글 단일 조회 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void findByIdFailureWhenPostIsNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.findById(0L, optionalUser1))
                .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 전체 조회 - 성공")
    @Test
    void findAll() {
        //given
        //when
        List<PostResponse> response = postService.findByVaccineType(VaccinationType.ALL, optionalUser1);
        //then
        assertThat(response).hasSize(3);
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void update() {
        //given
        PostRequest changedRequest = new PostRequest("change content", postRequest.getVaccinationType());
        postService.update(post1.getId(), optionalUser1, changedRequest);
        Post changedPost = postRepository.findById(post1.getId())
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
        assertThatThrownBy(() -> postService.update(0L, optionalUser1, changedContent))
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
        //then
        assertThatThrownBy(() -> postService.update(post1.getId(), optionalAnotherUser, changedContent))
                .isExactlyInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void delete() {
        //given
        //when
        postService.delete(post1.getId(), optionalUser1);
        Optional<Post> foundPost = postRepository.findById(post1.getId());
        //then
        assertThat(foundPost).isEmpty();
    }

    @DisplayName("게시글 삭제 - 성공 - 댓글도 함꼐 삭제 되는지 확인")
    @Test
    void deleteWithComments() {
        //given
        //when
        CommentResponse commentResponse = postService.createComment(post1.getId(), optionalUser1, commentRequest);
        postService.delete(post1.getId(), optionalUser1);
        Optional<Post> foundPost = postRepository.findById(post1.getId());
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
        postService.delete(post1.getId(), optionalUser1);
        //then
        assertThat(likeRepository.findById(likeResponse.getId())).isEmpty();
    }

    @DisplayName("게시글 삭제 - 실패 - 게시글이 존재하지 않는 경우")
    @Test
    void deleteFailureWhenPostIsNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.delete(0L, optionalUser1))
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
        //when
        //then
        assertThatThrownBy(() -> postService.update(post1.getId(), optionalAnotherUser, postRequest))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 타입별 조회 - 성공")
    @ParameterizedTest
    @MethodSource
    void findByVaccineType(VaccinationType vaccinationType) {
        //given
        post1 = Post.builder()
                .content("Test Content222")
                .vaccinationType(vaccinationType)
                .user(user1)
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.save(post1);
        //when
        List<PostResponse> postResponses = postService.findByVaccineType(vaccinationType, optionalUser1);
        //then
        assertThat(postResponses).filteredOn(
                response -> response.getVaccinationType().equals(vaccinationType)
        );
    }

    @DisplayName("게시글 좋아요 생성 - 성공")
    @Test
    void createLike() {
        //given
        //when
        postService.createLike(post1.getId(), optionalAnotherUser);
        //then
        Post post = getPostWithLikesById(post1.getId());
        assertThat(post.getLikesCount()).isEqualTo(3);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 게시글이 없는 경우")
    @Test
    void createLikeFailureWhenPostNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createLike(post2.getId() + 100L, optionalAnotherUser))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 좋아요 생성 - 실패 - 동일한 유저가 이미 좋아요를 누른 경우")
    @Test
    void createLikeFailureWhenAlreadyCreatedBySameUser() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createLike(post1.getId(), optionalUser1))
                .isInstanceOf(InvalidOperationException.class);
    }

    @DisplayName("게시글 좋아요 삭제 - 성공")
    @Test
    void deleteLike() {
        //given
        //when
        postService.deleteLike(post1.getId(), optionalUser1);
        resetEntityManager();
        //then
        Post actualPost = postRepository.findWithLikesById(post1.getId())
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
        assertThat(actualPost.getLikes().getLikes()).hasSize(1);
    }

    @DisplayName("게시글 좋아요 삭제 - 실패 - 다른 유저인 경우 ")
    @Test
    void deleteLikeFailureWhenInvalidToken() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.deleteLike(post1.getId(), optionalAnotherUser))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 좋아요 삭제 - 실패 - 삭제 요청한 좋아요가 해당 게시글에 없는 경우")
    @Test
    void deleteLikeFailureWhenLikeNotExists() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.deleteLike(post1.getId() + 1, optionalUser1))
                .isInstanceOf(NotFoundException.class);
    }

    private void resetEntityManager() {
        em.flush();
        em.clear();
        em.close();
    }

    private Post getPostWithLikesById(Long id) {
        return postRepository.findWithLikesById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    @DisplayName("댓글 생성 - 성공")
    @Test
    void createComment() {
        //given
        //when
        CommentResponse commentResponse = postService.createComment(post1.getId(), optionalUser1, commentRequest);
        //then
        assertThat(commentResponse.getContent()).isEqualTo(commentRequest.getContent());
        assertThat(commentResponse.getWriter().getId()).isEqualTo(user1.getId());
        assertThat(post1.getCommentsAsList()).extracting("id").contains(comment1.getId());
    }

    @DisplayName("댓글 생성 - 실패 - 비회원 댓글 작성 시도")
    @Test
    void createCommentWhenNotLoginUser() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.createComment(post1.getId(), Optional.empty(), commentRequest))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("댓글 수정 - 성공")
    @Test
    void updateComment() {
        //given
        CommentResponse commentResponse = postService.createComment(post1.getId(), optionalUser1, commentRequest);
        CommentRequest updateRequest = new CommentRequest("저녁 술 ㄱ?");
        //when
        postService.updateComment(post1.getId(), commentResponse.getId(), optionalUser1, updateRequest);
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
        assertThatThrownBy(() -> postService.updateComment(post1.getId(), 0L, optionalUser1, updateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("찾을 수 없는 댓글입니다.");
    }

    @DisplayName("댓글 수정 - 실패 - 댓글 작성자가 아님")
    @Test
    void updateCommentWhenWrongUser() {
        //given
        CommentResponse commentResponse = postService.createComment(post1.getId(), optionalUser1, commentRequest);
        CommentRequest updateRequest = new CommentRequest("저녁 술 ㄱ?");
        //when
        //then
        assertThatThrownBy(() -> postService.updateComment(post1.getId(), commentResponse.getId(), optionalAnotherUser, updateRequest))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessage("다른 사용자의 게시글은 수정할 수 없습니다.");
    }

    @DisplayName("댓글 삭제 - 성공")
    @Test
    void deleteComment() {
        //given
        CommentResponse commentResponse = postService.createComment(post1.getId(), optionalUser1, commentRequest);
        //when
        postService.deleteComment(post1.getId(), commentResponse.getId(), optionalUser1);
        postRepository.flush();

        Optional<Comment> foundComment = commentRepository.findById(commentResponse.getId());
        Post foundPost = postRepository.findById(this.post1.getId()).get();
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
        assertThatThrownBy(() -> postService.deleteComment(post1.getId(), 0L, optionalUser1))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("찾을 수 없는 댓글입니다.");
    }

    @DisplayName("댓글 삭제 - 실패 - 댓글 작성자가 아님")
    @Test
    void deleteCommentWhenWrongUser() {
        //given
        CommentResponse commentResponse = postService.createComment(post1.getId(), optionalUser1, commentRequest);
        //when
        //then
        assertThatThrownBy(() -> postService.deleteComment(post1.getId(), commentResponse.getId(), optionalAnotherUser))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessage("다른 사용자의 게시글은 삭제할 수 없습니다.");
    }

    @DisplayName("내가 작성한 게시글 조회 - 성공")
    @Test
    void findByUserAndFilterNone() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByUserAndFilter(Optional.of(user1), Filter.NONE);
        //then
        List<Long> userIds = postResponses.stream()
                .map(postResponse -> postResponse.getWriter().getId())
                .distinct()
                .collect(Collectors.toList());
        assertThat(userIds).containsExactly(user1.getId());
    }

    @DisplayName("내가 좋아요 한 게시글 조회 - 성공")
    @Test
    void findByUserAndFilterLikes() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByUserAndFilter(Optional.of(user1), Filter.LIKES);
        //then
        List<Long> postIds = postResponses.stream()
                .map(PostResponse::getId)
                .collect(Collectors.toList());
        assertThat(postIds).containsExactly(post1.getId());
    }

    @DisplayName("내가 댓글을 단 게시글 조회 - 성공")
    @Test
    void findByUserAndFilterComments() {
        //given
        //when
        List<PostResponse> postResponses = postService.findByUserAndFilter(optionalUser1, Filter.COMMENTS);
        List<Long> postIds = postResponses.stream()
                .map(PostResponse::getId)
                .collect(Collectors.toList());
        //then
        assertThat(postIds).containsExactly(post1.getId());
    }

    @DisplayName("게시글에 좋아요를 누른 후 본인이 다시 해당 글을 조회하면 hasLiked값이 true로 조회된다.")
    @Test
    void hasLiked() {
        //given
        //when
        PostResponse postResponse = postService.findById(post1.getId(), optionalUser1);
        //then
        assertThat(postResponse.isHasLiked()).isTrue();
    }

    @DisplayName("게시글에 좋아요를 누르지 않은 사람이 해당 글을 조회하면 hasLiked값이 false로 조회된다.")
    @Test
    void notHasLiked() {
        //given
        //when
        PostResponse postResponse = postService.findById(post1.getId(), optionalAnotherUser);
        //then
        assertThat(postResponse.isHasLiked()).isFalse();
    }
}
