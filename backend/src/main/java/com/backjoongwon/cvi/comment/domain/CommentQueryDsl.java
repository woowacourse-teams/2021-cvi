package com.backjoongwon.cvi.comment.domain;

import java.util.List;

public interface CommentQueryDsl {

    List<Comment> findByUserId(Long userId);

    List<Comment> findByUserId(Long userId, Long lastCommentId, int size);
}
