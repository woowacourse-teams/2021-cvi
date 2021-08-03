package com.backjoongwon.cvi.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParameterStringBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(ParameterStringBuilder.class);

    public static String getParamsString(Map<String, String> params) {
        try {
            StringBuilder result = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        } catch (UnsupportedEncodingException e) {
            LOG.info("지원하지 않는 인코딩 형식입니다.");
            throw new IllegalStateException("지원하지 않는 인코딩 형식입니다.");
        }
    }
}