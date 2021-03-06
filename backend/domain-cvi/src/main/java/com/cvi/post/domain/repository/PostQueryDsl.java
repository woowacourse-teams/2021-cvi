package com.cvi.post.domain.repository;

import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;
import java.util.Optional;

public interface PostQueryDsl {

    List<Post> findByVaccineType(VaccinationType vaccinationType);

    List<Post> findByVaccineType(VaccinationType vaccinationType, int offset, int size, OrderSpecifier orderSpecifier);

    List<Post> findByUserId(Long userId);

    Optional<Post> findWithLikesByPostId(Long id);

    Optional<Post> findWithCommentsByPostId(Long id);

    List<Post> findByUserId(Long userId, int offset, int size);
}
