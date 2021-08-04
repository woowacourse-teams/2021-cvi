package com.backjoongwon.cvi.user.dto;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {

    private String nickname;
    private AgeRange ageRange;
    private boolean shotVerified;
    private SocialProvider socialProvider;
    private String socialId;
    private String socialProfileUrl;

    @Builder
    public UserRequest(String nickname, AgeRange ageRange, boolean shotVerified,
                       SocialProvider socialProvider, String socialId, String socialProfileUrl) {
        this.nickname = nickname;
        this.ageRange = ageRange;
        this.shotVerified = shotVerified;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.socialProfileUrl = socialProfileUrl;
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .ageRange(ageRange)
                .shotVerified(shotVerified)
                .socialProvider(socialProvider)
                .socialId(socialId)
                .profileUrl(socialProfileUrl)
                .build();
    }
}
