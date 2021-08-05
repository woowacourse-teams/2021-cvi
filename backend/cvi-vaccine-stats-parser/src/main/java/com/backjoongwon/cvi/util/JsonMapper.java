package com.backjoongwon.cvi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    public static <T> T toObject(String rawData, Class<T> object) {
        try {
            return new ObjectMapper().readValue(rawData, object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("객체를 매핑할 수 없습니다.");
        }
    }
}
