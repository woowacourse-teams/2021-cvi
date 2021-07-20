package com.backjoongwon.cvi.auth.domain;

import lombok.Getter;

@Getter
public class Profile {
    private final String socialId;
    private final String profileUrl;

    public Profile(String socialId, String profileUrl) {
        this.socialId = socialId;
        this.profileUrl = profileUrl;
    }
}
