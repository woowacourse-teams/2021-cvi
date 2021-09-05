package com.backjoongwon.cvi.image;

import lombok.Getter;

@Getter
public enum ImageType {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private final String fileNameExtension;

    ImageType(String fileNameExtension) {
        this.fileNameExtension = fileNameExtension;
    }
}
