package com.backjoongwon.cvi.post.controller;

import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.comment.dto.CommentResponse;
import com.backjoongwon.cvi.image.dto.ImageRequest;
import com.backjoongwon.cvi.image.ImageType;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.dto.UserResponse;
import com.backjoongwon.cvi.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("게시글, 좋아요, 댓글 컨트롤러 Mock 테스트 - 환경설정")
public abstract class PreprocessPostControllerTest extends ApiDocument {

    protected static final Long USER_ID = 1L;
    protected static final Long POST_ID = 1L;
    protected static final Long COMMENT_ID = 1L;
    protected static final Long LIKE_ID = 1L;
    protected static final String BEARER = "Bearer ";
    protected static final String ACCESS_TOKEN = "{ACCESS TOKEN generated by JWT}";
    protected static final String IMAGE1_DATA_VALUE = "{이미지1 Base64 인코딩 된 문자열값}";
    protected static final String IMAGE2_DATA_VALUE = "{이미지2 Base64 인코딩 된 문자열값}";
    protected static final String IMAGE3_DATA_VALUE = "{이미지3 Base64 인코딩 된 문자열값}";
    protected static final String IMAGE4_DATA_VALUE = "{이미지4 Base64 인코딩 된 문자열값}";
    protected static final String IMAGE5_DATA_VALUE = "{이미지5 Base64 인코딩 된 문자열값}";
    protected static final String IMAGE1_S3_URL = "{이미지1 S3 URL}";
    protected static final String IMAGE2_S3_URL = "{이미지2 S3 URL}";
    protected static final String IMAGE3_S3_URL = "{이미지3 S3 URL}";
    protected static final String IMAGE4_S3_URL = "{이미지4 S3 URL}";

    @MockBean
    protected UserService userService;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    protected User user;
    protected User anotherUser;
    protected UserResponse userResponse;

    protected Post post;

    protected PostRequest createPostRequest;
    protected PostRequest updatePostRequest;
    protected PostResponse postResponse;

    protected List<CommentResponse> commentResponses;

    protected List<ImageRequest> newPostImagesRequests;
    protected List<ImageRequest> updatePostImagesRequests;
    protected List<String> imageUrls;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(USER_ID)
                .nickname("user")
                .ageRange(AgeRange.TEENS)
                .profileUrl("naver.com/profile")
                .socialId("{Unique ID received from social provider}")
                .socialProvider(SocialProvider.NAVER)
                .build();
        anotherUser = User.builder()
                .id(USER_ID + 1)
                .nickname("another_user")
                .ageRange(AgeRange.TWENTIES)
                .profileUrl("kakao.com/profile")
                .socialId("{Unique ID received from social provider}")
                .socialProvider(SocialProvider.KAKAO)
                .build();
        newPostImagesRequests = Arrays.asList(
                new ImageRequest(ImageType.JPG, IMAGE1_DATA_VALUE),
                new ImageRequest(ImageType.JPEG, IMAGE2_DATA_VALUE),
                new ImageRequest(ImageType.PNG, IMAGE3_DATA_VALUE));
        updatePostImagesRequests = Arrays.asList(
                new ImageRequest( ImageType.JPG, IMAGE4_DATA_VALUE),
                new ImageRequest( ImageType.SVG, IMAGE5_DATA_VALUE));
        imageUrls = Arrays.asList(
                IMAGE1_S3_URL,
                IMAGE2_S3_URL,
                IMAGE3_S3_URL,
                IMAGE4_S3_URL);
        post = Post.builder()
                .id(POST_ID)
                .user(user)
                .vaccinationType(VaccinationType.ASTRAZENECA)
                .content("Post Content")
                .createdAt(LocalDateTime.now())
                .viewCount(0)
                .build();

        createPostRequest = new PostRequest("글 내용", VaccinationType.PFIZER, newPostImagesRequests);
        updatePostRequest = new PostRequest("수정된 글 내용", VaccinationType.ASTRAZENECA, updatePostImagesRequests);

        userResponse = UserResponse.of(user, null);
        postResponse = PostResponse.of(post, user, imageUrls);

        given(jwtTokenProvider.isValidToken(ACCESS_TOKEN)).willReturn(true);
        given(jwtTokenProvider.getPayload(ACCESS_TOKEN)).willReturn(String.valueOf(user.getId()));
        given(userService.findUserById(any(Long.class))).willReturn(user);
    }
}
