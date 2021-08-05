package com.backjoongwon.cvi.like.domain;

import java.util.List;

public interface LikeQueryDsl {

    List<Like> findByUserId(Long userId);

    List<Like> findByUserId(Long userId, int offset, int size);
}
