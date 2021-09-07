package com.cvi.controller;

import com.cvi.exception.InvalidOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String findProfile() {
        List<String> deployProfiles = Arrays.asList("deploy1", "deploy2");

        return Arrays.stream(env.getActiveProfiles())
                .filter(deployProfiles::contains)
                .findAny()
                .orElseThrow(() -> new InvalidOperationException("현재 실행중인 JAR 파일의 profile중에 deploy1와 deploy2가 모두 없습니다."));
    }
}
