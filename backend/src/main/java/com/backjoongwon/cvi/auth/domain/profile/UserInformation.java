package com.backjoongwon.cvi.auth.domain.profile;

import lombok.Getter;

@Getter
public class UserInformation {

    private final String socialId;
    private final String socialProfileUrl;

    private UserInformation(String socialId, String socialProfileUrl) {
        this.socialId = socialId;
        this.socialProfileUrl = socialProfileUrl;
    }

    public static UserInformation of(SocialProfile socialProfile) {
        return new UserInformation(socialProfile.extractSocialId(), socialProfile.extractProfileUrl());
    }
}
