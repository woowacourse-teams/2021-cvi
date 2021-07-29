package com.backjoongwon.cvi.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeResponse {

    private Long id;
    private PostResponse postResponse;

    public LikeResponse(Long id, PostResponse postResponse) {
        this.id = id;
        this.postResponse = postResponse;
    }

    public static LikeResponse of(Long id, PostResponse postResponse) {
        return new LikeResponse(id, postResponse);
    }
}
