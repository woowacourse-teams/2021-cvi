package com.backjoongwon.cvi.user.dto;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {

    private String nickname;
    private AgeRange ageRange;
    private SocialProvider provider;
    private String socialId;
    private String profileUrl;

    public UserRequest(String nickname, AgeRange ageRange, SocialProvider provider, String socialId, String profileUrl) {
        this.nickname = nickname;
        this.ageRange = ageRange;
        this.provider = provider;
        this.socialId = socialId;
        this.profileUrl = profileUrl;
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .ageRange(ageRange)
                .socialProvider(provider)
                .socialId(socialId)
                .profileUrl(profileUrl)
                .build();
    }
}
