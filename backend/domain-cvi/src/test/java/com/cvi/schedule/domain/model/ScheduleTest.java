package com.cvi.schedule.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("스케줄러 도메인 테스트")
class ScheduleTest {

    @DisplayName("isRunning 상태를 변경한다.")
    @Test
    void reverseRunningState() {
        //given
        Schedule schedule = new Schedule(1L, LocalDateTime.now(), LocalDateTime.now(), "Public Data", 1, false);
        //when
        assertThat(schedule.isRunning()).isFalse();
        schedule.reverseRunningState();
        //then
        assertThat(schedule.isRunning()).isTrue();
    }
}
