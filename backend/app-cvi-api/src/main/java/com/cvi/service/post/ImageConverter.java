package com.cvi.service.post;

import com.cvi.exception.FileConvertException;
import com.cvi.image.domain.ImageType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImageConverter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public ImageFile convertBytesToImageFile(String data, ImageType imageType) {
        final byte[] imageBytes = Base64.decodeBase64(data);
        final File file = saveLocal(imageBytes, imageType);
        return new ImageFile(file, imageType);
    }

    private File saveLocal(byte[] imageBytes, ImageType imageType) {
        final String fileNameExtension = imageType.name().toLowerCase();
        try {
            final String formattedDateTime = LocalDateTime.now().format(DATE_TIME_FORMATTER);
            final File imageFile = new File("post-image_" + formattedDateTime + "_" + UUID.randomUUID() + "." + fileNameExtension);
            final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            ImageIO.write(bufferedImage, fileNameExtension, imageFile);
            return imageFile;
        } catch (IOException e) {
            log.info("이미지 파일을 로컬에 저장하는데에 실패했습니다.");
            throw new FileConvertException("이미지 로컬 저장 중 예외 발생");
        }
    }
}
