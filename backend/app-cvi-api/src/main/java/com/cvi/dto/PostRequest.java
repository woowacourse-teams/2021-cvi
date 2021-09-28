package com.cvi.dto;

import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    @NotBlank(message = "게시글의 내용은 비어있을 수 없습니다.")
    private String content;
    private VaccinationType vaccinationType;
    private List<ImageRequest> images;

    @Builder
    public PostRequest(String content, VaccinationType vaccinationType, List<ImageRequest> images) {
        this.content = content;
        this.vaccinationType = vaccinationType;
        this.images = images;
    }

    public PostRequest(String content, VaccinationType vaccinationType) {
        this(content, vaccinationType, Collections.emptyList());
    }

    public Post toEntity() {
        return Post.builder()
            .content(content)
            .vaccinationType(vaccinationType)
            .build();
    }
}
