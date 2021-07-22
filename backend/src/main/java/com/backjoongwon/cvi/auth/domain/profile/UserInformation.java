package com.backjoongwon.cvi.auth.domain.profile;

import lombok.Getter;

@Getter
public class UserInformation {

    private final String socialId;
    private final String profileUrl;

    public UserInformation(String socialId, String profileUrl) {
        this.socialId = socialId;
        this.profileUrl = profileUrl;
    }
}
