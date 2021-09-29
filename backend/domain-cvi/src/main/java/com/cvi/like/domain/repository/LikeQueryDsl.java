package com.cvi.like.domain.repository;

import com.cvi.like.domain.model.Like;

import java.util.List;

public interface LikeQueryDsl {

    List<Like> findByUserId(Long userId);

    List<Like> findByUserId(Long userId, int offset, int size);
}
