package com.backjoongwon.cvi.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("User 도메인 단위 테스트")
class UserTest {

    private User user;

    @BeforeEach
    void init() {
        this.user = User.builder()
                .id(1L)
                .ageRange(AgeRange.TEENS)
                .createdAt(LocalDateTime.now())
                .nickname("검프")
//                .shotVerified(false)
                .socialProfileUrl("www.gump.com")
                .socialProvider(SocialProvider.KAKAO)
                .build();
    }

    @DisplayName("유저 수정 - 성공")
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

    @DisplayName("유저 백신 접종 여부 변경 - 성공")
    void makeVerified(){
        //given
        //when
        user.makeVerified();
        //then
        assertThat(user.isShotVerified()).isTrue();
    }
}