package com.cvi.image.repository;

import com.cvi.exception.NotFoundException;
import com.cvi.image.domain.Image;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.post.domain.repository.PostRepository;
import com.cvi.user.domain.model.AgeRange;
import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import com.cvi.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ImageRepository 테스트")
@DataJpaTest
class ImageRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageRepository imageRepository;

    private Image image;
    private Post post;

    @BeforeEach
    void setUp() {
        User user = User.builder()
            .nickname("인비")
            .ageRange(AgeRange.TEENS)
            .socialProvider(SocialProvider.KAKAO)
            .socialId("1000")
            .profileUrl("profile url 1")
            .build();
        userRepository.save(user);

        image = Image.builder()
            .url("image1_s3_url")
            .build();
        imageRepository.save(image);

        post = Post.builder()
            .user(user)
            .content("내용 1")
            .vaccinationType(VaccinationType.PFIZER)
            .build();
        postRepository.save(post);
    }

    @DisplayName("Post에 Image 추가 시 양방향 매핑 테스트 - 성공")
    @Test
    void assignImageToPost() {
        //given
        //when
        post.assignImages(Collections.singletonList(image));
        //then
        assertThat(post.getImages().getImages().contains(image)).isTrue();
        assertThat(image.getPost()).isEqualTo(post);
    }

    @DisplayName("Image에 Post 추가 시 양방향 매핑 테스트 - 성공")
    @Test
    void assignPostToImage() {
        //given
        //when
        image.assignPost(post);
        //then
        assertThat(post.getImages().getImages().contains(image)).isTrue();
        assertThat(image.getPost()).isEqualTo(post);
    }

    @DisplayName("Image에 Post 추가 시 양방향 매핑 테스트 - 성공 - null인 경우")
    @Test
    void assignPostToImageWhenNull() {
        //given
        //when
        //then
        assertThatThrownBy(() -> image.assignPost(null)).isInstanceOf(NotFoundException.class);
    }
}
