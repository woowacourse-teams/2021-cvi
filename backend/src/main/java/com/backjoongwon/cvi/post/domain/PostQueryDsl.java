package com.backjoongwon.cvi.post.domain;

import java.util.List;

public interface PostQueryDsl {

    List<Post> findByVaccineType(VaccinationType vaccinationType);
}
