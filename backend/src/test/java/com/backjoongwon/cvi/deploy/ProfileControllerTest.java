package com.backjoongwon.cvi.deploy;

import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("deploy profile 체크 테스트 ")
class ProfileControllerTest {

    @DisplayName("active된 profile들 중에 deploy1이 포함되어 있을 때, deploy1이 반환횐다.")
    @ParameterizedTest
    @ValueSource(strings = {"deploy1", "deploy2"})
    void findProfileDeploy1(String expectedProfile) {
        //given
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("prod");
        env.addActiveProfile("oauth");

        ProfileController profileController = new ProfileController(env);
        //when
        String profile = profileController.findProfile();
        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @DisplayName("active된 profile들 중에 deploy1과 deploy2가 모두 존재하지 않을 때, 예외가 발생한다.")
    @Test
    void failureWhenProfileDeploy1AndDeploy2NotActivated() {
        //given
        MockEnvironment env = new MockEnvironment();
        ProfileController profileController = new ProfileController(env);
        //when
        //then
        assertThatThrownBy(profileController::findProfile)
                .isInstanceOf(InvalidOperationException.class);
    }
}
