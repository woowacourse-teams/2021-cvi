package com.backjoongwon.cvi.image.domain;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ImageRepository 테스트")
@DataJpaTest
class ImageRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageRepository imageRepository;

    private User user1;
    private Image image1;
    private Post post1;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .nickname("인비")
                .ageRange(AgeRange.TEENS)
                .socialProvider(SocialProvider.KAKAO)
                .socialId("1000")
                .profileUrl("profile url 1")
                .build();
        userRepository.save(user1);

        image1 = Image.builder()
                .url("image1_s3_url")
                .build();
        imageRepository.save(image1);

        post1 = Post.builder()
                .user(user1)
                .content("내용 1")
                .vaccinationType(VaccinationType.PFIZER)
                .build();
        postRepository.save(post1);
    }

    @DisplayName("Post에 Image 추가 시 양방향 매핑 테스트")
    @Test
    void assignImageToPost() {
        //given
        //when
        post1.assignImages(Collections.singletonList(image1));
        //then
        assertThat(post1.getImages().getImages().contains(image1)).isTrue();
        assertThat(image1.getPost()).isEqualTo(post1);
    }

    @DisplayName("Image에 Post 추가 시 양방향 매핑 테스트")
    @Test
    void assignPostToImage() {
        //given
        //when
        image1.assignPost(post1);
        //then
        assertThat(post1.getImages().getImages().contains(image1)).isTrue();
        assertThat(image1.getPost()).isEqualTo(post1);
    }
}
