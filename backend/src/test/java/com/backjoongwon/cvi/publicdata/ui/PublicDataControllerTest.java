package com.backjoongwon.cvi.publicdata.ui;

import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.publicdata.application.PublicDataService;
import com.backjoongwon.cvi.publicdata.dto.VaccinationStatisticResponse;
import com.backjoongwon.cvi.user.application.UserService;
import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.backjoongwon.cvi.publicdata.PublicDataFacotry.*;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("공공데이터 컨트롤러 Mock 테스트")
@WebMvcTest(controllers = PublicDataController.class)
class PublicDataControllerTest extends ApiDocument {

    private static final LocalDateTime TARGET_DATE_TIME = LocalDateTime.now();

    @MockBean
    public PublicDataService publicDataService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("백신 접종률 저장 - 성공")
    @Test
    void saveVaccinationStatistic() throws Exception {
        //given
        List<VaccinationStatisticResponse> vaccinationStatisticResponse = toVaccinationStatisticResponse(TARGET_DATE_TIME);
        //when
        willReturn(vaccinationStatisticResponse).given(publicDataService).saveVaccinationStatistics(TARGET_DATE_TIME);
        ResultActions response = 백신_접종률_저장_요청(TARGET_DATE_TIME);
        //then
        백신_접종률_저장_성공함(response);
    }

    @DisplayName("백신 접종률 조회 - 성공")
    @Test
    void findVaccinationStatistic() throws Exception {
        //given
        List<VaccinationStatisticResponse> vaccinationStatisticResponse = toVaccinationStatisticResponseWithWorldRegion(TARGET_DATE_TIME);
        //when
        willReturn(vaccinationStatisticResponse).given(publicDataService).findVaccinationStatistics(TARGET_DATE_TIME);
        ResultActions response = 백신_접종률_조회_요청(TARGET_DATE_TIME);
        //then
        백신_접종률_조회_성공함(response);
    }

    @DisplayName("백신 접종률 조회 - 성공 - 요청 후 api 데이터가 없을 때")
    @Test
    void findVaccinationStatisticEmpty() throws Exception {
        //given
        LocalDateTime tomorrowDateTime = TARGET_DATE_TIME.plusDays(1);
        //when
        willReturn(Collections.emptyList()).given(publicDataService).findVaccinationStatistics(tomorrowDateTime);
        ResultActions response = 백신_접종률_조회_요청(tomorrowDateTime);
        //then
        백신_접종률_없데이트전_조회_성공함(response);
    }

    @DisplayName("세계 백신 접종률 조회 - 성공")
    @Test
    void saveWorldVaccinationStatistic() throws Exception {
        //given
        //when
        willReturn(toVaccinationStatisticResponseOnlyWorldRegion(TARGET_DATE_TIME)).given(publicDataService).saveWorldVaccinationStatistics(TARGET_DATE_TIME);
        ResultActions response = 세계_백신_접종률_저장_요청(TARGET_DATE_TIME);
        //then
        세계_백신_접종률_저장_성공함(response);
    }

    @DisplayName("세계 백신 접종률 조회 - 성공")
    @Test
    void findWorldVaccinationStatistic() throws Exception {
        //given
        List<VaccinationStatisticResponse> expect = Collections.singletonList(toSingleWorldVaccinationStatisticResponse(TARGET_DATE_TIME));
        //when
        willReturn(expect).given(publicDataService).findWorldVaccinationStatistics(TARGET_DATE_TIME);
        ResultActions response = 세계_백신_접종률_조회_요청(TARGET_DATE_TIME);
        //then
        새걔_백신_접종률_조회_성공함(response);
    }

    private ResultActions 백신_접종률_저장_요청(LocalDateTime targetDateTime) throws Exception {
        return mockMvc.perform(post("/api/v1/publicdata/vaccinations")
                .param("targetDate", targetDateTime.toString()));
    }

    private void 백신_접종률_저장_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isCreated())
                .andDo(print())
                .andDo(toDocument("publicdata-vaccination-save"));
    }

    private ResultActions 백신_접종률_조회_요청(LocalDateTime targetDateTime) throws Exception {
        return mockMvc.perform(get("/api/v1/publicdata/vaccinations")
                .param("targetDate", targetDateTime.toString()));
    }

    private void 백신_접종률_조회_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("publicdata-vaccination-find"));
    }

    private void 백신_접종률_없데이트전_조회_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("publicdata-vaccination-nodata-find"));
    }

    private ResultActions 세계_백신_접종률_저장_요청(LocalDateTime targetDateTime) throws Exception {
        return mockMvc.perform(post("/api/v1/publicdata/vaccinations/world")
                .param("targetDate", targetDateTime.toString()));
    }

    private void 세계_백신_접종률_저장_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isCreated())
                .andDo(print())
                .andDo(toDocument("publicdata-world-vaccination-save"));
    }

    private ResultActions 세계_백신_접종률_조회_요청(LocalDateTime targetDateTime) throws Exception {
        return mockMvc.perform(get("/api/v1/publicdata/vaccinations/world")
                .param("targetDate", targetDateTime.toString()));
    }

    private void 새걔_백신_접종률_조회_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andDo(print())
                .andDo(toDocument("publicdata-world-vaccination-find"));
    }
}
