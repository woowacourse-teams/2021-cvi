package com.backjoongwon.cvi.comment.domain;

import com.backjoongwon.cvi.user.domain.User;

import java.util.List;

public interface CommentQueryDsl {

    List<Comment> findByUser(User user);
}
