package com.cvi.image.model;

import com.cvi.image.domain.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Image 도메인 테스트")
class ImageTest {

    @DisplayName("객체 주소값, 내용이 달라도 id만 같으면 동일한 객체 - 성공")
    @Test
    void equalsAndHashcode() {
        //given
        final Image image1 = Image.builder()
            .id(1L)
            .url("aaaa/image1")
            .build();
        final Image image2 = Image.builder()
            .id(1L)
            .url("bbbb/image2")
            .build();
        //when
        //then
        assertThat(image1)
            .isNotSameAs(image2)
            .isEqualTo(image2)
            .hasSameHashCodeAs(image2);
        assertThat(image1.getName()).isEqualTo("image1");
        assertThat(image2.isSameAs(1L)).isTrue();
    }
}

