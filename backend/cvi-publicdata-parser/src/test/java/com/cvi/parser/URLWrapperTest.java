package com.cvi.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("URL Wrap 기능 테스트")
class URLWrapperTest {

    @DisplayName("URL 생성 - 실패 - url형식이 아닐때 ")
    @Test
    void createURLWrapper() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new URLWrapper("htt://www.naver.com"))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("URL 연결 - 성공 ")
    @Test
    void openConnection() throws IOException {
        //given
        URLWrapper urlWrapper = new URLWrapper("https://www.naver.com");
        //when
        HttpURLConnection httpURLConnection = urlWrapper.openConnection();
        //then
        assertThat(httpURLConnection.getResponseCode()).isEqualTo(HttpURLConnection.HTTP_OK);
    }
}
