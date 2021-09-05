package com.backjoongwon.cvi.like.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeQueryDsl {
    void deleteAllByPostId(Long postId);
}
