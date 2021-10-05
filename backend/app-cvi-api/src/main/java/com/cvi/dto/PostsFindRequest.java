package com.cvi.dto;

import com.cvi.post.domain.model.Sort;
import com.cvi.post.domain.model.VaccinationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostsFindRequest {
    private VaccinationType vaccinationType;
    private int boundary;
    private long id;
    private int size;
    private Sort sort;

    @Builder
    public PostsFindRequest(VaccinationType vaccinationType, int boundary, long id, int size, Sort sort) {
        this.vaccinationType = vaccinationType;
        this.boundary = boundary;
        this.id = id;
        this.size = size;
        this.sort = sort;
    }
}
