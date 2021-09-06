package com.backjoongwon.cvi.aws.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.backjoongwon.cvi.common.exception.FileConvertException;
import com.backjoongwon.cvi.image.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3Uploader {

    private final AmazonS3Client amazonS3;

    @Value("${aws.s3.bucket_name}")
    private String s3BucketName;
    @Value("${aws.cloudfront.url}")
    private String cloudFrontUrl;

    public String upload(ImageType imageType, byte[] imageBytes) {
        final File file = saveLocal(imageType, imageBytes);
        return uploadToS3(file);
    }

    private String uploadToS3(File uploadFile) {
        final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, uploadFile.getPath(), uploadFile);
        amazonS3.putObject(putObjectRequest);
        final String url = uploadFile.getPath();
        removeLocalSavedImageFile(uploadFile);
        return cloudFrontUrl + "/" + url;
    }

    private void removeLocalSavedImageFile(File targetFile) {
        if (targetFile.delete()) {
            log.debug("이미지 파일 삭제 성공");
            return;
        }
        log.debug("이미지 파일 삭제 실패");
    }

    private File saveLocal(ImageType imageType, byte[] imageBytes) {
        final String fileNameExtension = imageType.getFileNameExtension();
        try {
            final File imageFile = new File("post-image-" + LocalDateTime.now() + "-" + UUID.randomUUID() + "." + fileNameExtension);
            final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            ImageIO.write(bufferedImage, fileNameExtension, imageFile);
            return imageFile;
        } catch (IOException e) {
            log.debug("이미지 파일을 로컬에 저장하는데에 실패했습니다.");
            throw new FileConvertException("이미지 로컬 저장 중 예외 발생");
        }
    }

    public void delete(String imageS3Path) {
        try {
            log.debug("S3에서 삭제할 이미지 경로: {}", imageS3Path);
            final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(s3BucketName, imageS3Path);
            amazonS3.deleteObject(deleteObjectRequest);
            log.debug("S3의 {} 파일 삭제 성공", imageS3Path);
        } catch (AmazonServiceException e) {
            log.debug("S3의 {} 파일 삭제 실패", imageS3Path);
        }
    }
}
