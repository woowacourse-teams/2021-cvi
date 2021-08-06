package com.backjoongwon.cvi.parser;

import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.backjoongwon.cvi.dto.WorldVaccinationParserResponse;
import com.backjoongwon.cvi.util.DateConverter;
import com.backjoongwon.cvi.util.JsonMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VaccinationParser {

    private static final String DATA_URL = "https://api.odcloud.kr/api/15077756/v1/vaccine-stat";
    private static final String WORLD_DATA_URL = "https://raw.githubusercontent.com/owid/covid-19-data/master/public/data/vaccinations/vaccinations.json";
    private static final LocalDateTime START_DATE = LocalDateTime.of(2021, 3, 11, 0, 0, 0);
    private static final int UPDATE_HOURS = 10;

    private final Parser parser;
    private final JsonMapper jsonMapper;

    public VaccinationParser(Parser parser, JsonMapper jsonMapper) {
        this.parser = parser;
        this.jsonMapper = jsonMapper;
    }


    public VaccineParserResponse parseToPublicData(LocalDateTime targetDateTime, String apiSecretKey) {
        if (isInvalidDateTime(targetDateTime)) {
            return VaccineParserResponse.empty();
        }
        return jsonMapper.toObject(getRawData(targetDateTime, apiSecretKey), VaccineParserResponse.class);
    }

    public WorldVaccinationParserResponse parseToWorldPublicData() {
        String rawData = parser.parse(WORLD_DATA_URL);
        List<WorldVaccinationParserResponse> worldVaccinationParserResponses = jsonMapper.toWorldVaccinationParserResponse(rawData);

        Collections.reverse(worldVaccinationParserResponses);
        for (WorldVaccinationParserResponse worldVaccinationParserResponse : worldVaccinationParserResponses) {
            if ("World".equals(worldVaccinationParserResponse.getCountry())) {
                return worldVaccinationParserResponse;
            }
        }
        return WorldVaccinationParserResponse.empty();
    }

    private boolean isInvalidDateTime(LocalDateTime targetDateTime) {
        LocalDate nowDate = LocalDate.now();
        if (targetDateTime.toLocalDate().isEqual(nowDate) && targetDateTime.getHour() < UPDATE_HOURS) {
            return true;
        }
        if (targetDateTime.isBefore(START_DATE)) {
            return true;
        }
        return targetDateTime.toLocalDate().isAfter(nowDate);
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
        parameters.put("cond[baseDate::EQ]", DateConverter.convertTimeToZero(targetDateTime));
        return parameters;
    }
}
