package com.backjoongwon.cvi.auth.dto;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthRequest {

    @NotNull(message = "OAuth 제공자는 null일 수 없습니다.")
    private SocialProvider provider;
    @NotBlank(message = "code는 비어있을 수 없습니다.")
    private String code;
    private String state;

    public AuthRequest(SocialProvider provider, String code, String state) {
        this.provider = provider;
        this.code = code;
        this.state = state;
    }
}
