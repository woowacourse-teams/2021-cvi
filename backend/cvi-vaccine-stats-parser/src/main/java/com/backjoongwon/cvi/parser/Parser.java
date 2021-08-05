package com.backjoongwon.cvi.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Parser {

    private static final String GET_METHOD = "GET";
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);

    public String parse(String url) {
        try {
            HttpURLConnection con = new URLWrapper(url).openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod(GET_METHOD);

            con.getResponseCode();
            StringBuffer content = readInputStream(con);
            con.disconnect();
            return content.toString();
        } catch (Exception e) {
            LOG.info("api 호출 예외입니다");
            throw new IllegalStateException("api 호출 예외입니다");
        }
    }

    private StringBuffer readInputStream(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content;
    }
}
