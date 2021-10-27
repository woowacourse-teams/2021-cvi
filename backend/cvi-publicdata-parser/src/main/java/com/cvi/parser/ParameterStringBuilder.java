package com.cvi.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParameterStringBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(ParameterStringBuilder.class);

    private ParameterStringBuilder() {
    }

    public static String getParamsString(Map<String, String> params) {
        try {
            StringBuilder result = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }
            return modifyResult(result.toString());
        } catch (UnsupportedEncodingException e) {
            LOG.info("지원하지 않는 인코딩 형식입니다.");
            throw new IllegalStateException("지원하지 않는 인코딩 형식입니다.");
        }
    }

    private static String modifyResult(String resultString) {
        if (resultString.length() > 0) {
            return resultString.substring(0, resultString.length() - 1);
        }
        return resultString;
    }
}
