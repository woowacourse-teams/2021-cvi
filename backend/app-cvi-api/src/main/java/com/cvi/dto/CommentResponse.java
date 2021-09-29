package com.cvi.dto;

import com.cvi.comment.domain.model.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<CommentResponse> toList(List<Comment> comments) {
        return comments.stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }
}
