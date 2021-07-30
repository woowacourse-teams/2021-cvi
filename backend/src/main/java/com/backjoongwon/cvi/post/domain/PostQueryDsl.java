package com.backjoongwon.cvi.post.domain;

import com.backjoongwon.cvi.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface PostQueryDsl {

    List<Post> findByVaccineType(VaccinationType vaccinationType);

    List<Post> findByUser(User user);

    Optional<Post> findWithLikesById(Long id);

    Optional<Post> findWithCommentsById(Long id);
}
