package com.backjoongwon.cvi.post.domain;

import java.util.List;
import java.util.Optional;

public interface PostQueryDsl {

    List<Post> findByVaccineType(VaccinationType vaccinationType);

    List<Post> findByVaccineType(VaccinationType vaccinationType, Long lastPostId, int size);

    List<Post> findByUser(Long id);

    Optional<Post> findWithLikesById(Long id);

    Optional<Post> findWithCommentsById(Long id);
}
