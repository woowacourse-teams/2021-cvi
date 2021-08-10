package com.backjoongwon.cvi.post.dto;

import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    @NotBlank(message = "게시글의 내용은 비어있을 수 없습니다.")
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
