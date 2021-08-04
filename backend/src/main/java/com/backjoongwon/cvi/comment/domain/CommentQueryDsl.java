package com.backjoongwon.cvi.comment.domain;

import java.util.List;

public interface CommentQueryDsl {

    List<Comment> findByUser(Long id);

    List<Comment> findByUser(Long id, Long lastCommentId, int size);
}
