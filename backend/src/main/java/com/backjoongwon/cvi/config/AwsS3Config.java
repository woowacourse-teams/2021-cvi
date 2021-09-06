//package com.backjoongwon.cvi.config;
//
//import com.amazonaws.auth.InstanceProfileCredentialsProvider;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//@Profile("!test")
//@Configuration
//public class AwsS3Config {
//
//    @Value("${cloud.aws.credentials.accessKey}")
//    private String accessKey;
//
//    @Value("${cloud.aws.credentials.secretKey}")
//    private String secretKey;
//
//    @Value("${cloud.aws.region.static}")
//    private String region;
//
//    @Bean
//    public AmazonS3Client amazonS3Client() {
//        final InstanceProfileCredentialsProvider credentialsProvider = InstanceProfileCredentialsProvider.getInstance();
//        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
//                .withCredentials(credentialsProvider)
//                .withRegion(region)
//                .build();
//    }
//}