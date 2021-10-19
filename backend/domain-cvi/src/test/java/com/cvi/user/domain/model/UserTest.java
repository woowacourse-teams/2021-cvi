package com.cvi.user.domain.model;

import com.cvi.CustomParameterizeTest;
import com.cvi.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

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
                .profileUrl("www.gump.com")
                .socialProvider(SocialProvider.KAKAO)
                .build();
    }

    @DisplayName("사용자 생성 - 성공")
    @CustomParameterizeTest
    @ValueSource(strings = {"ㅁㅇㄹㅁㅇㄹ", "ㅓㅓㅓㅓ", "adfdf", "검프", "검프23213"})
    void save(String name) {
        //given
        //when
        //then
        assertThatCode(() -> User.builder()
                .ageRange(AgeRange.TWENTIES)
                .nickname(name)
                .profileUrl("www.budae.com")
                .socialProvider(SocialProvider.KAKAO)
                .build()
        ).doesNotThrowAnyException();
    }

    @DisplayName("사용자 생성 - 실패")
    @CustomParameterizeTest
    @NullSource
    @ValueSource(strings = {" ", "라 이언", " gump", "yon ", "", "1234567891234567891232", "ㅇㅇ\n"})
    void saveFailure(String name) {
        //given
        //when
        //then
        assertThatThrownBy(() -> User.builder()
                .ageRange(AgeRange.TWENTIES)
                .nickname(name)
                .profileUrl("www.budae.com")
                .socialProvider(SocialProvider.KAKAO)
                .build()
        ).isInstanceOf(InvalidInputException.class)
                .hasMessageContaining("올바른 닉네임 형식이 아닙니다(특수 문자, 공백 불가).");
    }


    @DisplayName("사용자 수정 - 성공")
    @CustomParameterizeTest
    @ValueSource(strings = {"ㅁㅇㄹㅁㅇㄹ", "ㅓㅓㅓㅓ", "adfdf", "검프", "검프23213"})
    void update(String name) {
        //given
        User updateUser = User.builder()
                .id(1L)
                .ageRange(AgeRange.TWENTIES)
                .createdAt(LocalDateTime.now())
                .nickname(name)
                .profileUrl("www.budae.com")
                .socialProvider(SocialProvider.KAKAO)
                .build();
        //when
        user.update(updateUser);
        //then
        assertThat(user.getNickname()).isEqualTo(updateUser.getNickname());
    }
}
