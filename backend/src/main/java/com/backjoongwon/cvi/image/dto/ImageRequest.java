package com.backjoongwon.cvi.image.dto;

import com.backjoongwon.cvi.image.ImageType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageRequest {

    private ImageType type;
    private String data;

    @Builder
    public ImageRequest(ImageType type, String data) {
        this.type = type;
        this.data = data;
    }
}
