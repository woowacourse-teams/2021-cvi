package com.cvi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class WorldVaccinationParserResponse {

    private String country;
    @JsonProperty(value = "iso_code")
    private String isoCode;
    private List<WorldVaccinationData> data;

    public WorldVaccinationParserResponse() {
    }

    public WorldVaccinationParserResponse(String country, String isoCode, List<WorldVaccinationData> data) {
        this.country = country;
        this.isoCode = isoCode;
        this.data = data;
    }

    public static WorldVaccinationParserResponse empty() {
        return new WorldVaccinationParserResponse("World", "OWID_WRL", Collections.emptyList());
    }

    public String getCountry() {
        return country;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public List<WorldVaccinationData> getData() {
        return data;
    }
}
