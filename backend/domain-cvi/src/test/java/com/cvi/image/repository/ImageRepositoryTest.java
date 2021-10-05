package com.cvi.image.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cvi.image.domain.Image;
import com.cvi.image.repository.ImageRepository;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.post.domain.repository.PostRepository;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
