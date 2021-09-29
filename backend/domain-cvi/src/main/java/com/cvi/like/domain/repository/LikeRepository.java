package com.cvi.like.domain.repository;

import com.cvi.like.domain.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeQueryDsl {
}
