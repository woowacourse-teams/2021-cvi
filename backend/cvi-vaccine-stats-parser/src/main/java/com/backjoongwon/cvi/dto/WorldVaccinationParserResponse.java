package com.backjoongwon.cvi.dto;

import java.util.Collections;
import java.util.List;

public class WorldVaccinationParserResponse {

    private String country;
    private String iso_code;
    private List<WorldVaccinationData> data;

    public WorldVaccinationParserResponse() {
    }

    public WorldVaccinationParserResponse(String country, String iso_code, List<WorldVaccinationData> data) {
        this.country = country;
        this.iso_code = iso_code;
        this.data = data;
    }

    public static WorldVaccinationParserResponse empty() {
        return new WorldVaccinationParserResponse("World", "OWID_WRL", Collections.emptyList());
    }

    public String getCountry() {
        return country;
    }

    public String getIso_code() {
        return iso_code;
    }

    public List<WorldVaccinationData> getData() {
        return data;
    }
}
