package com.backjoongwon.cvi.user.domain;

import com.backjoongwon.cvi.common.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("사용자 도메인 단위 테스트")
class UserTest {

    private User user;

    @BeforeEach
    void init() {
        this.user = User.builder()
                .id(1L)
                .ageRange(AgeRange.TEENS)
                .createdAt(LocalDateTime.now())
                .nickname("검프")
                .socialProfileUrl("www.gump.com")
                .socialProvider(SocialProvider.KAKAO)
                .build();
    }

    @DisplayName("사용자 수정 - 성공")
    @Test
    void update() {
        //given
        User updateUser = user = User.builder()
                .id(1L)
                .ageRange(AgeRange.TWENTIES)
                .createdAt(LocalDateTime.now())
                .nickname("인비")
                .socialProfileUrl("www.budae.com")
                .socialProvider(SocialProvider.KAKAO)
                .build();
        //when
        user.update(updateUser);
        //then
        assertThat(user.getAgeRange()).isEqualTo(updateUser.getAgeRange());
        assertThat(user.getNickname()).isEqualTo(updateUser.getNickname());
        assertThat(user.getSocialProfileUrl()).isEqualTo(updateUser.getSocialProfileUrl());
    }

    @DisplayName("사용자 백신 접종 여부 수정 - 성공")
    @Test
    void makeVerified(){
        //given
        //when
        user.makeVerified();
        //then
        assertThat(user.isShotVerified()).isTrue();
    }

    @DisplayName("사용자 회원가입 - 실패 - 닉네임 빈 문자열")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "라 이언", " gump", "yon "})
    void signupFailureWhenEmptyNickname(String nickname) {
        //given
        //when
        //then
        assertThatThrownBy(() -> User.builder().nickname(nickname).build())
                .isInstanceOf(InvalidInputException.class);
    }
}