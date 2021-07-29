package com.backjoongwon.cvi.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeResponse {

    private Long id;

    public LikeResponse(Long id) {
        this.id = id;
    }

    public static LikeResponse from(Long id) {
        return new LikeResponse(id);
    }
}
