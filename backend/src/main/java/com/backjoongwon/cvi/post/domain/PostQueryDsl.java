package com.backjoongwon.cvi.post.domain;

import com.querydsl.core.types.OrderSpecifier;

import java.util.List;
import java.util.Optional;

public interface PostQueryDsl {

    List<Post> findByVaccineType(VaccinationType vaccinationType);

    List<Post> findByVaccineType(VaccinationType vaccinationType, int offset, int size, OrderSpecifier orderSpecifier, int hours);

    List<Post> findByUserId(Long userId);

    Optional<Post> findWithLikesById(Long id);

    Optional<Post> findWithCommentsById(Long id);

    Optional<Post> findWithCommentsById(Long id, int offset, int size);

    List<Post> findByUserId(Long userId, int offset, int size);
}
