package com.backjoongwon.cvi.like.domain;

import java.util.List;

public interface LikeQueryDsl {

    List<Like> findByUser(Long id);

    List<Like> findByUser(Long id, Long lastLikeId, int size);
}
