package com.cvi.user.domain.model;

import com.cvi.CustomParameterizedTest;
import com.cvi.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

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
    @CustomParameterizedTest
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
    @CustomParameterizedTest
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
    @CustomParameterizedTest
    @MethodSource
    void update(String name, AgeRange ageRange, boolean shotVerified) {
        //given
        User updateUser = User.builder()
            .id(1L)
            .ageRange(ageRange)
            .createdAt(LocalDateTime.now())
            .nickname(name)
            .profileUrl("www.budae.com")
            .shotVerified(shotVerified)
            .socialProvider(SocialProvider.KAKAO)
            .build();
        //when
        user.update(updateUser);
        //then
        assertThat(user.getNickname()).isEqualTo(updateUser.getNickname());
        assertThat(user.getAgeRange()).isEqualTo(updateUser.getAgeRange());
        assertThat(user.isShotVerified()).isEqualTo(updateUser.isShotVerified());
    }

    static Stream<Arguments> update() {
        return Stream.of(Arguments.of("1", AgeRange.TWENTIES, true),
            Arguments.of("2", AgeRange.THIRTIES, false));
    }
}
