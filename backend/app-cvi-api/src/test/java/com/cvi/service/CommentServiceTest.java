package com.cvi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cvi.comment.domain.model.Comment;
import com.cvi.comment.domain.repository.CommentRepository;
import com.cvi.dto.CommentRequest;
import com.cvi.dto.CommentResponse;
import com.cvi.exception.NotFoundException;
import com.cvi.exception.UnAuthorizedException;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.post.domain.repository.PostRepository;
import com.cvi.service.post.PostService;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("댓글 비즈니스 흐름 테스트")
public class CommentServiceTest {

    private static final long COMMENT_ID = 0L;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    private User user;
    private User anotherUser;
    private Optional<User> optionalUser;
    private Optional<User> optionalAnotherUser;
    private Optional<User> optionalUserNotSignedIn;
    private Post post;
    private CommentRequest commentRequest;

    @BeforeEach
    void init() {
        initUsers();
        initPost();
        commentRequest = new CommentRequest("테스트 댓글");
    }

    private void initUsers() {
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
            .profileUrl("naver.com/profile")
            .socialId("NAVER_ID")
            .socialProvider(SocialProvider.NAVER)
            .build();
        optionalUser = Optional.of(user);
        optionalAnotherUser = Optional.of(anotherUser);
        optionalUserNotSignedIn = Optional.empty();
        userRepository.saveAll(Arrays.asList(user, anotherUser));
    }

    private void initPost() {
        post = Post.builder()
            .content("테스트게시글")
            .vaccinationType(VaccinationType.ASTRAZENECA)
            .user(user)
            .createdAt(LocalDateTime.now())
            .build();
        postRepository.save(post);
    }

    @DisplayName("댓글 생성 - 성공")
    @Test
    void createComment() {
        //given
        //when
        CommentResponse commentResponse = commentService.createComment(post.getId(), optionalAnotherUser, commentRequest);
        Post foundPost = postRepository.findWithCommentsByPostId(post.getId())
            .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 없습니다."));
        //then
        assertThat(foundPost.getCommentsAsList()).extracting("id").contains(commentResponse.getId());
    }

    @DisplayName("댓글 생성 - 실패 - 비회원 댓글 작성 시도")
    @Test
    void createCommentWhenNotLoginUser() {
        //given
        //when
        //then
        assertThatThrownBy(() -> commentService.createComment(post.getId(), optionalUserNotSignedIn, commentRequest))
            .isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("댓글 수정 - 성공")
    @Test
    void updateComment() {
        //given
        CommentResponse commentResponse = commentService.createComment(post.getId(), optionalUser, commentRequest);
        CommentRequest updateRequest = new CommentRequest("업데이트 댓글");
        //when
        commentService.updateComment(post.getId(), commentResponse.getId(), optionalUser, updateRequest);
        Comment comment = commentRepository.findById(commentResponse.getId()).get();
        //then
        assertThat(comment.getContent()).isEqualTo(updateRequest.getContent());
    }

    @DisplayName("댓글 수정 - 실패 - 존재하지 않는 댓글")
    @Test
    void updateCommentWhenNoComment() {
        //given
        CommentRequest updateRequest = new CommentRequest("업데이트 댓글 실패");
        //when
        //then
        assertThatThrownBy(() -> commentService.updateComment(post.getId(), COMMENT_ID, optionalUser, updateRequest))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("찾을 수 없는 댓글입니다.");
    }

    @DisplayName("댓글 수정 - 실패 - 댓글 작성자가 아님")
    @Test
    void updateCommentWhenWrongUser() {
        //given
        CommentResponse commentResponse = commentService.createComment(post.getId(), optionalUser, commentRequest);
        CommentRequest updateRequest = new CommentRequest("업데이트 댓글");
        //when
        //then
        assertThatThrownBy(() -> commentService.updateComment(post.getId(), commentResponse.getId(), optionalAnotherUser, updateRequest))
            .isInstanceOf(UnAuthorizedException.class)
            .hasMessage("다른 사용자의 댓글은 수정할 수 없습니다.입력 값: " + user.toString());
    }

    @DisplayName("댓글 삭제 - 성공")
    @Test
    void deleteComment() {
        //given
        CommentResponse commentResponse = commentService.createComment(post.getId(), optionalUser, commentRequest);
        //when
        commentService.deleteComment(post.getId(), commentResponse.getId(), optionalUser);
        postRepository.flush();

        Optional<Comment> foundComment = commentRepository.findById(commentResponse.getId());
        Post foundPost = postRepository.findById(this.post.getId()).get();
        //then
        assertThat(foundComment).isEmpty();
        assertThat(foundPost.getCommentsAsList()).isEmpty();
    }

    @DisplayName("댓글 삭제 - 실패 - 댓글을 찾을 수 없음")
    @Test
    void deleteCommentWhenNoComment() {
        //given
        //when
        //then
        assertThatThrownBy(() -> commentService.deleteComment(post.getId(), COMMENT_ID, optionalUser))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("찾을 수 없는 댓글입니다.");
    }

    @DisplayName("댓글 삭제 - 실패 - 댓글 작성자가 아님")
    @Test
    void deleteCommentWhenWrongUser() {
        //given
        CommentResponse commentResponse = commentService.createComment(post.getId(), optionalUser, commentRequest);
        //when
        //then
        assertThatThrownBy(() -> commentService.deleteComment(post.getId(), commentResponse.getId(), optionalAnotherUser))
            .isInstanceOf(UnAuthorizedException.class)
            .hasMessage("다른 사용자의 게시글은 삭제할 수 없습니다.");
    }

    @DisplayName("게시글 삭제시 댓글 삭제 - 성공")
    @Test
    void deleteCommentsWhenDeletePost() {
        //given
        CommentResponse commentResponse = commentService.createComment(post.getId(), optionalUser, commentRequest);
        //when
        postService.delete(post.getId(), optionalUser);
        Optional<Post> foundPost = postRepository.findById(post.getId());
        Optional<Comment> foundComment = commentRepository.findById(commentResponse.getId());
        //then
        assertThat(foundPost).isEmpty();
        assertThat(foundComment).isEmpty();
    }
}
