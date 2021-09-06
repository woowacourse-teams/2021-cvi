package com.backjoongwon.cvi.post.domain;

import com.backjoongwon.cvi.image.ImageType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
@Getter
public class ImageFile {

    private final File file;
    private final ImageType imageType;

    public ImageFile(File file, ImageType imageType) {
        this.file = file;
        this.imageType = imageType;
    }

    public void delete() {
        if (file.delete()) {
            log.debug("로컬에 저장되어있는 이미지 파일 삭제 성공");
            return;
        }
        log.debug("로컬에 저장되어있는 이미지 파일 삭제 실패");
        throw new IllegalStateException("로컬에 저장되어있는 이미지 파일 삭제에 실패했습니다.");
    }
}
