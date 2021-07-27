package com.backjoongwon.cvi.like.dto;

import com.backjoongwon.cvi.post.dto.PostResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LikeResponse {

    private Long id;
    private PostResponse postResponse;

    public LikeResponse(Long id, PostResponse postResponse) {
        this.id = id;
        this.postResponse = postResponse;
    }
}
