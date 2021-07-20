package com.backjoongwon.cvi.user.dto;

import com.backjoongwon.cvi.user.domain.AgeRange;
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

    public UserResponse(Long id, String nickname, AgeRange ageRange, boolean shotVerified) {
        this.id = id;
        this.nickname = nickname;
        this.ageRange = new AgeRangeResponse(ageRange);
        this.shotVerified = shotVerified;
    }

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getNickname(),
                user.getAgeRange(), user.isShotVerified());
    }
}
