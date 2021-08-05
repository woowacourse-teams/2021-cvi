package com.backjoongwon.cvi.post.domain;

import com.querydsl.core.types.OrderSpecifier;

import java.util.List;
import java.util.Optional;

public interface PostQueryDsl {

    List<Post> findByVaccineType(VaccinationType vaccinationType);

    List<Post> findByVaccineType(VaccinationType vaccinationType, Long lastPostId, int size, OrderSpecifier orderSpecifier);

    List<Post> findByUserId(Long userId);

    Optional<Post> findWithLikesById(Long id);

    Optional<Post> findWithCommentsById(Long id);

    List<Post> findByUserId(Long userId, Long lastPostId, int size);
}
