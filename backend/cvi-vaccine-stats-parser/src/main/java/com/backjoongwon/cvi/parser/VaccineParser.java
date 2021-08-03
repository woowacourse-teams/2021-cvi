package com.backjoongwon.cvi.parser;

import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.util.JsonMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;

public class VaccineParser {

    private static final String DATA_URL = "https://api.odcloud.kr/api/15077756/v1/vaccine-stat";
    private static final LocalDate START_DATE = LocalDate.of(2021,3,11);

    private final Parser parser;

    public VaccineParser(Parser parser) {
        this.parser = parser;
    }

    public VaccineParserResponse parseToPublicData(String targetDate, String apiSecretKey) {
        LocalDate localDate = LocalDate.parse(targetDate);
        if (localDate.isBefore(START_DATE)) {
            return VaccineParserResponse.empty();
        }
        if (localDate.isAfter(LocalDate.now())) {
            return VaccineParserResponse.empty();
        }
        if (LocalTime.now().isBefore(LocalTime.of(10, 0))) {
            return VaccineParserResponse.empty();
        }
        return JsonMapper.toObject(getRawData(targetDate, apiSecretKey), VaccineParserResponse.class);
    }

    private String getRawData(String targetDate, String apiSecretKey) {
        Map<String, String> parameters = makeParameters(targetDate, apiSecretKey);
        String params = ParameterStringBuilder.getParamsString(parameters);
        return parser.parse(DATA_URL + "?" + params);
    }

    private Map<String, String> makeParameters(String targetDate, String apiSecretKey) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("page", "1");
        parameters.put("perPage", "20");
        parameters.put("serviceKey", apiSecretKey);
        parameters.put("cond[baseDate::EQ]", targetDate + " 00:00:00");
        return parameters;
    }
}
