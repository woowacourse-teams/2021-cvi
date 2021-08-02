package com.backjoongwon.cvi.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class EnvSecret {

    @Autowired
    Environment env;

    @PostConstruct
    public void init() {
        log.info("naver.client-secret = " + env.getProperty("security.auth.naver.client-secret"));
        log.info("jwt.secret-key = " + env.getProperty("security.jwt.token.secret-key"));
    }
}
