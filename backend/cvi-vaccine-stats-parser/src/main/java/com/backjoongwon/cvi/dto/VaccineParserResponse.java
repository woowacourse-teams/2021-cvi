package com.backjoongwon.cvi.dto;

import java.util.Collections;
import java.util.List;

public class VaccineParserResponse {

    private int currentCount;
    private List<RegionVaccinationData> data;
    private int matchCount;
    private int page;
    private int perPage;
    private int totalCount;

    public VaccineParserResponse() {
    }

    public VaccineParserResponse(int currentCount, List<RegionVaccinationData> data, int matchCount, int page, int perPage, int totalCount) {
        this.currentCount = currentCount;
        this.data = data;
        this.matchCount = matchCount;
        this.page = page;
        this.perPage = perPage;
        this.totalCount = totalCount;
    }

    public static VaccineParserResponse empty() {
        return new VaccineParserResponse(0, Collections.emptyList(), 0, 1, 20, 0);
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public List<RegionVaccinationData> getData() {
        return data;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isEmptyData() {
        return data.isEmpty();
    }
}
