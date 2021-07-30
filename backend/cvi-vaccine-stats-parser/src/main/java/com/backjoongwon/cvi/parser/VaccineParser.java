package com.backjoongwon.cvi.parser;

import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.util.JsonMapper;

import java.time.LocalDate;
import java.util.HashMap;

public class VaccineParser {

    public static final String DATA_URL = "https://api.odcloud.kr/api/15077756/v1/vaccine-stat";

    private final Parser parser;

    public VaccineParser(Parser parser) {
        this.parser = parser;
    }

    public VaccineParserResponse parseToPublicData() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("page", "1");
        parameters.put("perPage", "10");
        parameters.put("serviceKey", "bRU9Gbs0x/64WQS6gH05wpOxkM+X0GY0bXrUKRpW/072Bu6hlrJIqGVv6JC/uEz4mt4EC1l+l/8rxmz3ShAAAw==");
        parameters.put("cond[baseDate::EQ]", LocalDate.now() + " 00:00:00");
        String params = ParameterStringBuilder.getParamsString(parameters);
        String rawData = parser.parse(DATA_URL + "?" + params);

        return JsonMapper.toObject(rawData, VaccineParserResponse.class);
    }
}
