package com.backjoongwon.cvi.user.dto;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {

    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    @Length(max = 20, message = "닉네임의 길이는 최소 1자부터 20자까지 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임은 특수문자가 포함될 수 없습니다.")
    private String nickname;

    @NotNull(message = "연령대는 null일 수 없습니다.")
    private AgeRange ageRange;
    private boolean shotVerified;

    @NotNull(message = "OAuth 제공자는 null일 수 없습니다.")
    private SocialProvider socialProvider;

    @NotBlank(message = "OAuth 고유의 id는 null일 수 없습니다.")
    private String socialId;

    @NotBlank(message = "OAuth profile url은 null일 수 없습니다.")
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
