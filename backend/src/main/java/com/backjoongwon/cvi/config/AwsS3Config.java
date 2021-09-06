package com.backjoongwon.cvi.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class AwsS3Config {

    @Bean
    public AmazonS3Client amazonS3Client() {
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }
}
