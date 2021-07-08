package com.backjoongwon.cvi.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private Long id;

    public PostResponse(Long id) {
        this.id = id;
    }
}
