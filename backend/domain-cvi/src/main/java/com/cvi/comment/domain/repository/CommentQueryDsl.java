package com.cvi.comment.domain.repository;

import com.cvi.comment.domain.model.Comment;

import java.util.List;

public interface CommentQueryDsl {

    List<Comment> findByUserId(Long userId);

    List<Comment> findByUserId(Long userId, int offset, int size);
}
