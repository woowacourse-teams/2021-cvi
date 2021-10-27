package com.cvi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cvi.comment.domain.model.Comment;
import com.cvi.comment.domain.repository.CommentRepository;
import com.cvi.dto.ImageRequest;
import com.cvi.dto.PostRequest;
import com.cvi.dto.PostResponse;
import com.cvi.exception.InvalidOperationException;
import com.cvi.exception.NotFoundException;
import com.cvi.exception.UnAuthorizedException;
import com.cvi.image.domain.Image;
import com.cvi.image.domain.ImageType;
import com.cvi.image.repository.ImageRepository;
import com.cvi.like.domain.model.Like;
import com.cvi.like.domain.repository.LikeRepository;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.post.domain.repository.PostRepository;
import com.cvi.service.post.ImageConverter;
import com.cvi.service.post.ImageFile;
import com.cvi.service.post.PostService;
import com.cvi.uploader.AwsS3Uploader;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("게시글 - 생성/수정/삭제 비즈니스 흐름 테스트")
class PostServiceTest {

    private static final String IMAGE1_S3_URL = "image1 s3 url";
    private static final String IMAGE2_S3_URL = "image2 s3 url";
    private static final String IMAGE3_S3_URL = "image3 s3 url";
    private static final String IMAGE4_S3_URL = "image4 s3 url";
    private static final String IMAGE5_S3_URL = "image5 s3 url";
    private static final String IMAGE1_BASE_64_DATA = "image1 base64 data";
    private static final String IMAGE2_BASE_64_DATA = "image2 base64 data";
    private static final String IMAGE3_BASE_64_DATA = "image3 base64 data";
    private static final String IMAGE4_BASE_64_DATA = "image4 base64 data";
    private static final String IMAGE5_BASE_64_DATA = "image5 base64 data";

    @MockBean
    private ImageConverter imageConverter;

    @MockBean
    private AwsS3Uploader awsS3Uploader;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostService postService;

    @PersistenceContext
    private EntityManager entityManager;

    private User user;
    private User anotherUser;
    private Optional<User> optionalUser;
    private Optional<User> optionalAnotherUser;
    private Optional<User> optionalUserNotSignedIn;
    private Image image1;
    private Image image2;
    private Image image3;
    private ImageFile imageFile1;
    private ImageFile imageFile2;
    private ImageFile imageFile3;
    private ImageFile imageFile4;
    private ImageFile imageFile5;
    private File file1;
    private File file2;
    private File file3;
    private File file4;
    private File file5;
    private Comment comment1;
    private Comment comment2;
    private Like like1;
    private Like like2;
    private Post post;
    private Post anotherPost;
    private Post postWithImages;
    private PostRequest postRequestWithImages;
    private PostRequest postRequestNotWithImages;
    private ImageRequest newImageRequest1;
    private ImageRequest newImageRequest2;
    private ImageRequest newImageRequest3;
    private List<ImageRequest> newImageRequests;
    private List<ImageRequest> updateImageRequests;

    @BeforeEach
    void init() {
        initUsers();
        initPosts();
        initImages();
        initComments();
        initLikes();
        initImageRequests();
        initUpdatedImageRequests();
        initPostRequestWithImages();
        initPostRequestNotWithImages();
        initMockImageFile();
        initMockImageConverter();
        initMockAwsS3Uploader();
        assertIdAssigned();
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
            .socialProvider(SocialProvider.NAVER)
            .socialId("NAVER_ID")
            .profileUrl("naver.com/profile")
            .build();
        optionalUser = Optional.of(user);
        optionalAnotherUser = Optional.of(anotherUser);
        optionalUserNotSignedIn = Optional.empty();
        userRepository.saveAll(Arrays.asList(user, anotherUser));
    }

    private void initPosts() {
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
        postWithImages = Post.builder()
            .content("Test")
            .vaccinationType(VaccinationType.ASTRAZENECA)
            .user(user)
            .createdAt(LocalDateTime.now())
            .build();
        postRepository.saveAll(Arrays.asList(post, anotherPost, postWithImages));
    }

    private void initImages() {
        image1 = Image.builder()
            .url(IMAGE1_S3_URL)
            .build();
        image1.assignPost(postWithImages);
        imageRepository.save(image1);

        image2 = Image.builder()
            .url(IMAGE2_S3_URL)
            .build();
        image2.assignPost(postWithImages);
        imageRepository.save(image2);

        image3 = Image.builder()
            .url(IMAGE3_S3_URL)
            .build();
        image3.assignPost(postWithImages);
        imageRepository.save(image3);
    }

    private void initComments() {
        comment1 = Comment.builder()
            .content("댓글 내용1")
            .user(user)
            .build();
        comment1.assignPost(postWithImages);
        commentRepository.save(comment1);

        comment2 = Comment.builder()
            .content("댓글 내용2")
            .user(anotherUser)
            .build();
        comment2.assignPost(postWithImages);
        commentRepository.save(comment2);
    }

    private void initLikes() {
        like1 = Like.builder()
            .user(user)
            .build();
        like1.assignPost(postWithImages);
        likeRepository.save(like1);

        like2 = Like.builder()
            .user(anotherUser)
            .build();
        like2.assignPost(postWithImages);
        likeRepository.save(like2);
    }

    private void initImageRequests() {
        newImageRequest1 = new ImageRequest(ImageType.JPG, IMAGE1_BASE_64_DATA);
        newImageRequest2 = new ImageRequest(ImageType.JPEG, IMAGE2_BASE_64_DATA);
        newImageRequest3 = new ImageRequest(ImageType.PNG, IMAGE3_BASE_64_DATA);
        newImageRequests = Arrays.asList(newImageRequest1, newImageRequest2, newImageRequest3);
    }

    private void initUpdatedImageRequests() {
        updateImageRequests = Arrays.asList(
            new ImageRequest(ImageType.JPG, IMAGE4_BASE_64_DATA),
            new ImageRequest(ImageType.PNG, IMAGE5_BASE_64_DATA));
    }

    private void initPostRequestWithImages() {
        postRequestWithImages = PostRequest.builder()
            .content("Test Conent")
            .vaccinationType(VaccinationType.PFIZER)
            .images(newImageRequests)
            .build();
    }

    private void initPostRequestNotWithImages() {
        postRequestNotWithImages = PostRequest.builder()
            .content("Test Conent")
            .vaccinationType(VaccinationType.PFIZER)
            .images(Collections.emptyList())
            .build();
    }

    private void initMockImageFile() {
        file1 = mock(File.class);
        file2 = mock(File.class);
        file3 = mock(File.class);
        file4 = mock(File.class);
        file5 = mock(File.class);
        imageFile1 = mock(ImageFile.class);
        imageFile2 = mock(ImageFile.class);
        imageFile3 = mock(ImageFile.class);
        imageFile4 = mock(ImageFile.class);
        imageFile5 = mock(ImageFile.class);
        willReturn(file1).given(imageFile1).getFile();
        willReturn(file2).given(imageFile2).getFile();
        willReturn(file3).given(imageFile3).getFile();
        willReturn(file4).given(imageFile4).getFile();
        willReturn(file5).given(imageFile5).getFile();
        willDoNothing().given(imageFile1).delete();
        willDoNothing().given(imageFile2).delete();
        willDoNothing().given(imageFile3).delete();
        willDoNothing().given(imageFile4).delete();
        willDoNothing().given(imageFile5).delete();
    }

    private void initMockImageConverter() {
        willReturn(imageFile1).given(imageConverter).convertBytesToImageFile(eq(IMAGE1_BASE_64_DATA), any(ImageType.class));
        willReturn(imageFile2).given(imageConverter).convertBytesToImageFile(eq(IMAGE2_BASE_64_DATA), any(ImageType.class));
        willReturn(imageFile3).given(imageConverter).convertBytesToImageFile(eq(IMAGE3_BASE_64_DATA), any(ImageType.class));
        willReturn(imageFile4).given(imageConverter).convertBytesToImageFile(eq(IMAGE4_BASE_64_DATA), any(ImageType.class));
        willReturn(imageFile5).given(imageConverter).convertBytesToImageFile(eq(IMAGE5_BASE_64_DATA), any(ImageType.class));
    }

    private void initMockAwsS3Uploader() {
        willReturn(IMAGE1_S3_URL).given(awsS3Uploader).upload(any(String.class), same(file1));
        willReturn(IMAGE2_S3_URL).given(awsS3Uploader).upload(any(String.class), same(file2));
        willReturn(IMAGE3_S3_URL).given(awsS3Uploader).upload(any(String.class), same(file3));
        willReturn(IMAGE4_S3_URL).given(awsS3Uploader).upload(any(String.class), same(file4));
        willReturn(IMAGE5_S3_URL).given(awsS3Uploader).upload(any(String.class), same(file5));
    }

    private void assertIdAssigned() {
        assertThat(comment1.getId()).isNotNull();
        assertThat(comment2.getId()).isNotNull();

        assertThat(image1.getId()).isNotNull();
        assertThat(image2.getId()).isNotNull();

        assertThat(like1.getId()).isNotNull();
        assertThat(like2.getId()).isNotNull();

        assertThat(postWithImages.getId()).isNotNull();
    }

    @DisplayName("게시글 생성 - 성공 - 이미지 포함하지 않음")
    @Test
    void createNotWithImages() {
        //given
        //when
        PostResponse postResponse = postService.create(optionalUser, postRequestNotWithImages);
        resetEntityManager();
        Post foundPost = postRepository.findById(postResponse.getId())
            .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        //then
        assertThat(postResponse.getWriter().getId()).isEqualTo(user.getId());
        assertThat(postResponse.getContent()).isEqualTo(postRequestNotWithImages.getContent());
        assertThat(postResponse.getImages()).isEmpty();

        assertThat(foundPost).isNotNull();
        assertThat(foundPost.getUser()).isNotNull();
        assertThat(foundPost.hasImages()).isFalse();

        verify(awsS3Uploader, times(0)).upload(any(String.class), any(File.class));
    }

    @DisplayName("게시글 생성 - 성공 - 이미지 포함")
    @Test
    void createWithImages() {
        //given
        //when
        PostResponse postResponse = postService.create(optionalUser, postRequestWithImages);
        resetEntityManager();
        Post foundPost = postRepository.findById(postResponse.getId())
            .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        //then
        assertThat(postResponse.getWriter().getId()).isEqualTo(user.getId());
        assertThat(postResponse.getContent()).isEqualTo(postRequestWithImages.getContent());
        assertThat(postResponse.getImages()).containsExactly(IMAGE1_S3_URL, IMAGE2_S3_URL, IMAGE3_S3_URL);

        assertThat(foundPost).isNotNull();
        assertThat(foundPost.hasImages()).isTrue();
        assertThat(foundPost.getUser()).isNotNull();
        assertThat(foundPost.getAllImagesAsList())
            .extracting("url").containsExactly(IMAGE1_S3_URL, IMAGE2_S3_URL, IMAGE3_S3_URL);

        verify(awsS3Uploader, times(3)).upload(any(String.class), any(File.class));
    }

    @DisplayName("게시글 생성 - 실패 - 존재하지 않는 유저")
    @Test
    void createFailureWhenWriterIsNull() {
        //given
        //when
        //then
        assertThatThrownBy(() -> postService.create(optionalUserNotSignedIn, postRequestWithImages))
            .isExactlyInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("게시글 단일 조회 - 성공")
    @Test
    void findById() {
        //given
        //when
        PostResponse findResponse = postService.findById(postWithImages.getId(), optionalUser);
        //then
        assertThat(findResponse.getId()).isEqualTo(postWithImages.getId());
        assertThat(findResponse.getImages()).containsExactly(IMAGE1_S3_URL, IMAGE2_S3_URL, IMAGE3_S3_URL);
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
        assertThat(findResponse).hasSize(3);
        assertThat(findResponse.get(0).getContent()).isEqualTo(post.getContent());
        assertThat(findResponse.get(0).getImages()).containsExactly(IMAGE1_S3_URL, IMAGE2_S3_URL, IMAGE3_S3_URL);
    }

    @DisplayName("게시글 수정 - 이미지 모두 제거 - 성공")
    @Test
    void updateWhenRemoveAllImages() {
        //given
        PostRequest updateRequest = new PostRequest("updated content", postRequestNotWithImages.getVaccinationType(), Collections.emptyList());
        //when
        postService.update(postWithImages.getId(), optionalUser, updateRequest);
        resetEntityManager();

        Post updatedPost = postRepository.findById(postWithImages.getId())
            .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        //then
        assertThat(updatedPost.getContent()).isEqualTo(updateRequest.getContent());
        assertThat(updatedPost.hasImages()).isFalse();
        assertThat(imageRepository.findById(image1.getId())).isEmpty();
        assertThat(imageRepository.findById(image2.getId())).isEmpty();
        assertThat(imageRepository.findById(image3.getId())).isEmpty();
        verify(awsS3Uploader, times(3)).delete(any(String.class), any(String.class));
        verify(awsS3Uploader, times(1)).delete(any(String.class), eq(image1.getName()));
        verify(awsS3Uploader, times(1)).delete(any(String.class), eq(image2.getName()));
        verify(awsS3Uploader, times(1)).delete(any(String.class), eq(image3.getName()));
    }

    @DisplayName("게시글 수정 - 이미지 수정 - 성공")
    @Test
    void updateWithImages() {
        //given
        PostRequest updateRequest = new PostRequest("updated content", postRequestWithImages.getVaccinationType(), updateImageRequests);
        //when
        postService.update(postWithImages.getId(), optionalUser, updateRequest);
        resetEntityManager();
        Post updatedPost = postRepository.findById(postWithImages.getId())
            .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없음."));
        //then
        assertThat(updatedPost.getContent()).isEqualTo(updateRequest.getContent());
        assertThat(updatedPost.getAllImagesAsList())
            .extracting("url").containsExactly(IMAGE4_S3_URL, IMAGE5_S3_URL);
        assertThat(imageRepository.findAll()).extracting("url").containsExactly(IMAGE4_S3_URL, IMAGE5_S3_URL);
        verify(awsS3Uploader, times(3)).delete(any(String.class), any(String.class));
        verify(awsS3Uploader, times(1)).delete(any(String.class), eq(image1.getName()));
        verify(awsS3Uploader, times(1)).delete(any(String.class), eq(image2.getName()));
        verify(awsS3Uploader, times(1)).delete(any(String.class), eq(image3.getName()));
        verify(awsS3Uploader, times(2)).upload(any(String.class), any(File.class));
    }

    @DisplayName("게시글 수정 - 실패 - 찾을 수 없는 게시글")
    @Test
    void updateFailureWhenCannotFind() {
        //given
        PostRequest changedContent = new PostRequest("updated content", postRequestWithImages.getVaccinationType());
        //when
        //then
        assertThatThrownBy(() -> postService.update(Long.MAX_VALUE, optionalUser, changedContent))
            .isExactlyInstanceOf(NotFoundException.class);
    }

    @DisplayName("게시글 수정 - 실패 - 다른 작성자의 게시글")
    @Test
    void updateFailureWhenOthersPost() {
        //given
        PostRequest changedContent = new PostRequest("changed content", postRequestWithImages.getVaccinationType());
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

    @DisplayName("Post를 삭제하면, 안에 있는 Comment, Like, Image 들이 모두 삭제된다.")
    @Test
    void deletePost() {
        //given
        //when
        postService.delete(postWithImages.getId(), optionalUser);
        resetEntityManager();
        //then
        assertThat(userRepository.findById(user.getId())).isNotEmpty();
        assertThat(userRepository.findById(anotherUser.getId())).isNotEmpty();

        assertThat(postRepository.findById(postWithImages.getId())).isEmpty();

        assertThat(commentRepository.findById(comment1.getId())).isEmpty();
        assertThat(commentRepository.findById(comment2.getId())).isEmpty();

        assertThat(likeRepository.findById(like1.getId())).isEmpty();
        assertThat(likeRepository.findById(like2.getId())).isEmpty();

        assertThat(imageRepository.findById(image1.getId())).isEmpty();
        assertThat(imageRepository.findById(image2.getId())).isEmpty();
        assertThat(imageRepository.findById(image3.getId())).isEmpty();

        verify(awsS3Uploader, times(3)).delete(any(String.class), any(String.class));
        verify(awsS3Uploader, times(1)).delete(any(String.class), eq(image1.getName()));
        verify(awsS3Uploader, times(1)).delete(any(String.class), eq(image2.getName()));
        verify(awsS3Uploader, times(1)).delete(any(String.class), eq(image3.getName()));
    }

    private void resetEntityManager() {
        entityManager.flush();
        entityManager.clear();
    }
}
