package com.backjoongwon.cvi.parser;

import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.util.JsonMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class VaccineParser {

    private static final String DATA_URL = "https://api.odcloud.kr/api/15077756/v1/vaccine-stat";
    private static final LocalDateTime START_DATE = LocalDateTime.of(2021, 3, 11, 0, 0, 0);
    public static final int UPDATE_HOURS = 10;

    private final Parser parser;

    public VaccineParser(Parser parser) {
        this.parser = parser;
    }

    public VaccineParserResponse parseToPublicData(LocalDateTime targetDateTime, String apiSecretKey) {
        if (targetDateTime.isBefore(START_DATE)) {
            return VaccineParserResponse.empty();
        }
        if (targetDateTime.isAfter(LocalDateTime.now())) {
            return VaccineParserResponse.empty();
        }
        if (targetDateTime.toLocalDate().isEqual(LocalDate.now()) && targetDateTime.getHour() < UPDATE_HOURS) {
            return VaccineParserResponse.empty();
        }
        return JsonMapper.toObject(getRawData(targetDateTime, apiSecretKey), VaccineParserResponse.class);
    }

    private String getRawData(LocalDateTime targetDateTime, String apiSecretKey) {
        Map<String, String> parameters = makeParameters(targetDateTime, apiSecretKey);
        String params = ParameterStringBuilder.getParamsString(parameters);
        return parser.parse(DATA_URL + "?" + params);
    }

    private Map<String, String> makeParameters(LocalDateTime targetDateTime, String apiSecretKey) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("page", "1");
        parameters.put("perPage", "20");
        parameters.put("serviceKey", apiSecretKey);
        parameters.put("cond[baseDate::EQ]", toRequestDateTime(targetDateTime));
        return parameters;
    }

    private String toRequestDateTime(LocalDateTime targetDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        targetDateTime = targetDateTime.truncatedTo(ChronoUnit.DAYS);
        return targetDateTime.format(formatter);
    }
}
