package com.backjoongwon.cvi.parser;

import com.backjoongwon.cvi.dto.VaccineParserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;

public class Parser {

    public static final String DATA_URL = "https://api.odcloud.kr/api/15077756/v1/vaccine-stat";

    public VaccineParserResponse parseToPublicData() {
        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("page", "1");
            parameters.put("perPage", "10");
            parameters.put("serviceKey", "bRU9Gbs0x/64WQS6gH05wpOxkM+X0GY0bXrUKRpW/072Bu6hlrJIqGVv6JC/uEz4mt4EC1l+l/8rxmz3ShAAAw==");
            parameters.put("cond[baseDate::EQ]", LocalDate.now() + " 00:00:00");

            URL url = new URL(DATA_URL + "?" + (ParameterStringBuilder.getParamsString(parameters)));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
            con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            return new ObjectMapper().readValue(content.toString(), VaccineParserResponse.class);
        } catch (Exception e) {
            throw new IllegalStateException("api 호출 예외입니다");
        }
    }
}
