package com.cvi.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Parser {

    private static final String GET_METHOD = "GET";
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);

    public Parser() {
    }

    public String parse(String url) {
        HttpURLConnection con = new URLWrapper(url).openConnection();
        try {
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestMethod(GET_METHOD);
            return readInputStream(con);
        } catch (Exception e) {
            LOG.info("api 호출 예외입니다");
            throw new IllegalStateException("api 호출 예외입니다");
        } finally {
            con.disconnect();
        }
    }

    private String readInputStream(HttpURLConnection con) throws IOException {
        try (InputStream in = con.getInputStream();
             InputStreamReader inr = new InputStreamReader(in);
             BufferedReader br = new BufferedReader(inr);
        ) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        }
    }
}
