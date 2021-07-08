package com.backjoongwon.cvi.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    private String content;
    private String vaccinationType;

    public PostRequest(String content, String vaccinationType) {
        this.content = content;
        this.vaccinationType = vaccinationType;
    }
}
