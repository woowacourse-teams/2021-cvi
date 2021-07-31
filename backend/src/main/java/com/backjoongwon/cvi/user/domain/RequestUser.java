package com.backjoongwon.cvi.user.domain;

import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class RequestUser {

    private Long id;

    public RequestUser(Long id) {
        this.id = id;
    }

    public static RequestUser of(Long id) {
        return new RequestUser(id);
    }

    public static RequestUser guest() {
        return new RequestUser();
    }

    public void validateSignedin() {
        if (Objects.isNull(id)) {
            throw new UnAuthorizedException("인증되지 않은 사용자입니다.");
        }
    }

    public Long getId() {
        return id;
    }
}
