package com.backjoongwon.cvi.comment.dto;

import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.user.dto.UserResponse;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long id;
    private final UserResponse writer;
    private final String content;
    private final LocalDateTime createdAt;

    public CommentResponse(Long id, UserResponse writer, String content, LocalDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(comment.getId(), UserResponse.of(comment.getUser()), comment.getContent(), comment.getCreatedAt());
    }
}
