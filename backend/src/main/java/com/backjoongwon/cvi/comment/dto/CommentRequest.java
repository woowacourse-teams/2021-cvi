package com.backjoongwon.cvi.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequest {

    private String comment;

    public CommentRequest(String comment) {
        this.comment = comment;
    }
}
