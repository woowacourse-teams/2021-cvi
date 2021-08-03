package com.backjoongwon.cvi.like.domain;

import com.backjoongwon.cvi.user.domain.User;

import java.util.List;

public interface LikeQueryDsl {

    List<Like> findByUser(User user);
}
