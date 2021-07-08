package com.backjoongwon.cvi.post.dto;

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
    private String vaccinationType;
    private LocalDateTime createdAt;

    public PostResponse(Long id, UserResponse user, String content, int viewCount, String vaccinationType, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.viewCount = viewCount;
        this.vaccinationType = vaccinationType;
        this.createdAt = createdAt;
    }
}
