package com.cvi.dto;

import java.util.Collections;
import java.util.List;

public class KoreaVaccineParserResponse {

    private int currentCount;
    private List<KoreaRegionVaccinationData> data;
    private int matchCount;
    private int page;
    private int perPage;
    private int totalCount;

    public KoreaVaccineParserResponse() {
    }

    public KoreaVaccineParserResponse(int currentCount, List<KoreaRegionVaccinationData> data, int matchCount, int page, int perPage, int totalCount) {
        this.currentCount = currentCount;
        this.data = data;
        this.matchCount = matchCount;
        this.page = page;
        this.perPage = perPage;
        this.totalCount = totalCount;
    }

    public static KoreaVaccineParserResponse empty() {
        return new KoreaVaccineParserResponse(0, Collections.emptyList(), 0, 1, 20, 0);
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public List<KoreaRegionVaccinationData> getData() {
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
