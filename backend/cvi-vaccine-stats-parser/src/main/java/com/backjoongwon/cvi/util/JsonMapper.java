package com.backjoongwon.cvi.util;

import com.backjoongwon.cvi.dto.WorldVaccinationParserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class JsonMapper {

    private static final Logger LOG = LoggerFactory.getLogger(JsonMapper.class);
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T toObject(String rawData, Class<T> object) {
        try {
            return objectMapper.readValue(rawData, object);
        } catch (JsonProcessingException e) {
            LOG.info("객체를 매핑할 수 없습니다.");
            throw new IllegalStateException("객체를 매핑할 수 없습니다.");
        }
    }

    public List<WorldVaccinationParserResponse> toWorldVaccinationParserResponse(String rawData) {
        try {
            return Arrays.asList(objectMapper.readValue(rawData, WorldVaccinationParserResponse[].class));
        } catch (JsonProcessingException e) {
            LOG.info("객체를 매핑할 수 없습니다.");
            throw new IllegalStateException("객체를 매핑할 수 없습니다.");
        }
    }
}
