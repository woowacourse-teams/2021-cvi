package com.backjoongwon.cvi.auth.dto.profile;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserInformation {

    @NotBlank(message = "socialId(OAuth 고유의 id)는 null, '', ' ' 일 수 없습니다.")
    private final String socialId;

    @NotBlank(message = "socialId(OAuth 고유의 id)는 null, '', ' ' 일 수 없습니다.")
    private final String socialProfileUrl;

    private UserInformation(String socialId, String socialProfileUrl) {
        this.socialId = socialId;
        this.socialProfileUrl = socialProfileUrl;
    }

    public static UserInformation of(SocialProfile socialProfile) {
        return new UserInformation(socialProfile.extractSocialId(), socialProfile.extractProfileUrl());
    }
}
