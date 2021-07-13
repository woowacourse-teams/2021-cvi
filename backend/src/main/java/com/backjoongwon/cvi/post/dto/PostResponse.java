package com.backjoongwon.cvi.post.dto;

import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.user.dto.UserResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private Long id;
    private UserResponse user;
    private String content;
    private int viewCount;
    private VaccinationType vaccinationType;
    private LocalDateTime createdAt;

    public PostResponse(Long id, UserResponse user, String content, int viewCount,
                        VaccinationType vaccinationType, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.viewCount = viewCount;
        this.vaccinationType = vaccinationType;
        this.createdAt = createdAt;
    }

    public static PostResponse of(Post post) {
        return new PostResponse(post.getId(), UserResponse.of(post.getUser()), post.getContent(),
                post.getViewCount(), post.getVaccinationType(), post.getCreatedAt());
    }
}
