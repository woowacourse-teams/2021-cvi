package com.cvi.service;

import org.ehcache.event.CacheEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

@DisplayName("CacheEventLogger 기능 테스트")
class CacheEventLoggerTest {

    @DisplayName("이벤트 호출 확인")
    @Test
    void onEvent() {
        //given
        CacheEventLogger cacheEventLogger = new CacheEventLogger();
        final CacheEvent cacheEvent = mock(CacheEvent.class);
        //when
        willReturn(1L).given(cacheEvent).getKey();
        willReturn("이전 캐시").given(cacheEvent).getOldValue();
        willReturn("새로운 캐시").given(cacheEvent).getNewValue();
        //then
        cacheEventLogger.onEvent(cacheEvent);
    }
}
