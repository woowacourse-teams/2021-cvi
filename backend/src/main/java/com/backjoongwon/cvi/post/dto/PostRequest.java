package com.backjoongwon.cvi.post.dto;

import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    private String content;
    private VaccinationType vaccinationType;

    public PostRequest(String content, VaccinationType vaccinationType) {
        this.content = content;
        this.vaccinationType = vaccinationType;
    }

    public Post toEntity() {
        return Post.builder()
                .content(content)
                .vaccinationType(vaccinationType)
                .build();
    }
}
