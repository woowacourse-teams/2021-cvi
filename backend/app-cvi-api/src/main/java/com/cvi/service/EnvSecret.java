package com.cvi.service;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvSecret {

    private final Environment env;

    @PostConstruct
    public void init() {
        log.info("naver.client-secret = " + env.getProperty("security.auth.naver.client-secret"));
        log.info("jwt.secret-key = " + env.getProperty("security.jwt.token.secret-key"));
    }
}
