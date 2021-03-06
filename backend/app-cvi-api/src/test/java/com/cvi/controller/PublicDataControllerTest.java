package com.cvi.controller;

import static com.cvi.PublicDataFacotry.toSingleWorldVaccinationStatisticResponse;
import static com.cvi.PublicDataFacotry.toVaccinationStatisticResponse;
import static com.cvi.PublicDataFacotry.toVaccinationStatisticResponseOnlyWorldRegion;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cvi.ApiDocument;
import com.cvi.auth.JwtTokenProvider;
import com.cvi.dto.VaccinationStatisticResponse;
import com.cvi.service.PublicDataService;
import com.cvi.service.UserService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("공공데이터 컨트롤러 Mock 테스트")
@WebMvcTest(controllers = PublicDataController.class)
class PublicDataControllerTest extends ApiDocument {

    private static final LocalDate TARGET_DATE = LocalDate.now();

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
        List<VaccinationStatisticResponse> vaccinationStatisticResponse = toVaccinationStatisticResponse(TARGET_DATE);
        //when
        willReturn(vaccinationStatisticResponse).given(publicDataService).saveVaccinationStatistics(TARGET_DATE);
        ResultActions response = 백신_접종률_저장_요청(TARGET_DATE);
        //then
        백신_접종률_저장_성공함(response);
    }

    @DisplayName("백신 접종률 조회 - 성공")
    @Test
    void findVaccinationStatistic() throws Exception {
        //given
        List<VaccinationStatisticResponse> vaccinationStatisticResponse = toVaccinationStatisticResponse(TARGET_DATE);
        //when
        willReturn(vaccinationStatisticResponse).given(publicDataService).findVaccinationStatistics(TARGET_DATE);
        ResultActions response = 백신_접종률_조회_요청(TARGET_DATE);
        //then
        백신_접종률_조회_성공함(response);
    }

    @DisplayName("백신 접종률 조회 - 성공 - 요청 후 api 데이터가 없을 때")
    @Test
    void findVaccinationStatisticEmpty() throws Exception {
        //given
        LocalDate tomorrowDateTime = TARGET_DATE.plusDays(1);
        //when
        willReturn(Collections.emptyList()).given(publicDataService).findVaccinationStatistics(tomorrowDateTime);
        ResultActions response = 백신_접종률_조회_요청(tomorrowDateTime);
        //then
        백신_접종률_업데이트전_조회_성공함(response);
    }

    @DisplayName("세계 백신 접종률 조회 - 성공")
    @Test
    void saveWorldVaccinationStatistic() throws Exception {
        //given
        //when
        willReturn(toVaccinationStatisticResponseOnlyWorldRegion(TARGET_DATE)).given(publicDataService).saveWorldVaccinationStatistics(TARGET_DATE);
        ResultActions response = 세계_백신_접종률_저장_요청(TARGET_DATE);
        //then
        세계_백신_접종률_저장_성공함(response);
    }

    @DisplayName("세계 백신 접종률 조회 - 성공 - 요청 후 api 데이터가 없을 때")
    @Test
    void findWorldVaccinationStatistic() throws Exception {
        //given
        List<VaccinationStatisticResponse> expect = Collections.singletonList(toSingleWorldVaccinationStatisticResponse(TARGET_DATE));
        //when
        willReturn(expect).given(publicDataService).findWorldVaccinationStatistics(TARGET_DATE);
        ResultActions response = 세계_백신_접종률_조회_요청(TARGET_DATE);
        //then
        새걔_백신_접종률_조회_성공함(response);
    }

    private ResultActions 백신_접종률_저장_요청(LocalDate targetDate) throws Exception {
        return mockMvc.perform(post("/api/v1/publicdata/vaccinations")
            .param("targetDate", targetDate.toString()));
    }

    private void 백신_접종률_저장_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isCreated())
            .andDo(print())
            .andDo(toDocument("publicdata-vaccination-save"));
    }

    private ResultActions 백신_접종률_조회_요청(LocalDate targetDate) throws Exception {
        return mockMvc.perform(get("/api/v1/publicdata/vaccinations")
            .param("targetDate", targetDate.toString()));
    }

    private void 백신_접종률_조회_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
            .andDo(print())
            .andDo(toDocument("publicdata-vaccination-find"));
    }

    private void 백신_접종률_업데이트전_조회_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
            .andDo(print())
            .andDo(toDocument("publicdata-vaccination-nodata-find"));
    }

    private ResultActions 세계_백신_접종률_저장_요청(LocalDate targetDate) throws Exception {
        return mockMvc.perform(post("/api/v1/publicdata/vaccinations/world")
            .param("targetDate", targetDate.toString()));
    }

    private void 세계_백신_접종률_저장_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isCreated())
            .andDo(print())
            .andDo(toDocument("publicdata-world-vaccination-save"));
    }

    private ResultActions 세계_백신_접종률_조회_요청(LocalDate targetDate) throws Exception {
        return mockMvc.perform(get("/api/v1/publicdata/vaccinations/world")
            .param("targetDate", targetDate.toString()));
    }

    private void 새걔_백신_접종률_조회_성공함(ResultActions response) throws Exception {
        response.andExpect(status().isOk())
            .andDo(print())
            .andDo(toDocument("publicdata-world-vaccination-find"));
    }
}
