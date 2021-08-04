package com.backjoongwon.cvi.comment.domain;

import java.util.List;

public interface CommentQueryDsl {

    List<Comment> findByUser(Long id);
}
