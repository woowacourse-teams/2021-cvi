package com.backjoongwon.cvi.post.domain;

import com.backjoongwon.cvi.user.domain.User;

import java.util.List;

public interface PostQueryDsl {

    List<Post> findByVaccineType(VaccinationType vaccinationType);

    List<Post> findByUser(User user);
}
