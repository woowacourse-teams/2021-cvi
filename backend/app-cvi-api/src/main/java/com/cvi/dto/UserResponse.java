package com.cvi.dto;

import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
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

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getNickname(), new AgeRangeResponse(user.getAgeRange()),
            user.isShotVerified(), null, user.getSocialProvider(), user.getSocialId(), user.getProfileUrl());
    }

    public static UserResponse of(User user, String token) {
        return new UserResponse(user.getId(), user.getNickname(), new AgeRangeResponse(user.getAgeRange()),
            user.isShotVerified(), token, user.getSocialProvider(), user.getSocialId(), user.getProfileUrl());
    }

    public static UserResponse newUser(SocialProvider socialProvider, String socialId, String socialProfileUrl) {
        return new UserResponse(null, null, null, false,
            null, socialProvider, socialId, socialProfileUrl);
    }
}
