package com.cvi.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class URLWrapper {

    private static final Logger LOG = LoggerFactory.getLogger(URLWrapper.class);
    private final URL url;

    public URLWrapper(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException exception) {
            LOG.info("URL 객체 생성 실패했습니다.");
            throw new IllegalStateException("URL 객체 생성 실패했습니다.");
        }
    }

    public HttpURLConnection openConnection() {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (IOException exception) {
            LOG.info("커넥션을 가져오지 못햇습니다.");
            throw new IllegalStateException("커넥션을 가져오지 못햇습니다.");
        }
    }
}
