package com.cvi.util;

import com.cvi.dto.WorldVaccinationParserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class JsonMapper {

    private static final Logger LOG = LoggerFactory.getLogger(JsonMapper.class);
    private static final ObjectMapper OBJECT_MAPPER;

    public JsonMapper() {
    }

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T toObject(String rawData, Class<T> object) {
        try {
            return OBJECT_MAPPER.readValue(rawData, object);
        } catch (JsonProcessingException e) {
            LOG.info("객체를 매핑할 수 없습니다.");
            throw new IllegalStateException("객체를 매핑할 수 없습니다.");
        }
    }

    public List<WorldVaccinationParserResponse> toWorldVaccinationParserResponse(String rawData) {
        try {
            return Arrays.asList(OBJECT_MAPPER.readValue(rawData, WorldVaccinationParserResponse[].class));
        } catch (JsonProcessingException e) {
            LOG.info("객체를 매핑할 수 없습니다.");
            throw new IllegalStateException("객체를 매핑할 수 없습니다.");
        }
    }
}
