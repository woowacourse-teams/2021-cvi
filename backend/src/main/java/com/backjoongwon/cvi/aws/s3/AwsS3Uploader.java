package com.backjoongwon.cvi.aws.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket_name}")
    private String s3BucketName;
    @Value("${aws.cloudfront.url}")
    private String cloudFrontUrl;

    public String upload(String s3DirPath, File file) {
        final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, s3DirPath + file.getName(), file);
        amazonS3.putObject(putObjectRequest);
        return cloudFrontUrl + "/" + s3DirPath + file.getName();
    }

    public void delete(String s3DirPath, String fileName) {
        try {
            log.debug("S3에서 삭제할 이미지 경로: {}", s3DirPath + fileName);
            final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(s3BucketName, s3DirPath + fileName);
            amazonS3.deleteObject(deleteObjectRequest);
            log.debug("S3의 {} 파일 삭제 성공", s3DirPath + fileName);
        } catch (AmazonServiceException e) {
            log.info("S3의 {} 파일 삭제 실패", s3DirPath + fileName);
        }
    }
}
