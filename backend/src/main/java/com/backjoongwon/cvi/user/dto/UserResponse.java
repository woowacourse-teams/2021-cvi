package com.backjoongwon.cvi.user.dto;

import com.backjoongwon.cvi.auth.domain.Profile;
import com.backjoongwon.cvi.user.domain.SocialProvider;
import com.backjoongwon.cvi.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {

    private Long id;
    private String nickname;
    private AgeRangeResponse ageRange;
    private boolean shotVerified;
    private String accessToken;
    private SocialProvider socialProvider;
    private String socialId;
    private String socialProfileUrl;

    public UserResponse(Long id, String nickname, AgeRangeResponse ageRange, boolean shotVerified, String accessToken,
                        SocialProvider socialProvider, String socialId, String socialProfileUrl) {
        this.id = id;
        this.nickname = nickname;
        this.ageRange = ageRange;
        this.shotVerified = shotVerified;
        this.accessToken = accessToken;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.socialProfileUrl = socialProfileUrl;
    }

    public static UserResponse of(User user, String token) {
        return new UserResponse(user.getId(), user.getNickname(), new AgeRangeResponse(user.getAgeRange()),
                user.isShotVerified(), token, user.getSocialProvider(), user.getSocialId(), user.getProfileUrl());
    }
}
