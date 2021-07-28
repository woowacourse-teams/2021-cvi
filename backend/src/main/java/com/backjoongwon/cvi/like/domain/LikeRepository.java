package com.backjoongwon.cvi.like.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByIdAndUserId(Long id, Long userId);
}
