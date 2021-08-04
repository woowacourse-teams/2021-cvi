package com.backjoongwon.cvi.publicdata.ui;

import com.backjoongwon.cvi.ApiDocument;
import com.backjoongwon.cvi.publicdata.application.PublicDataService;
import com.backjoongwon.cvi.publicdata.dto.VaccinationRateResponse;
import com.backjoongwon.cvi.user.domain.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("공공데이터 컨트롤러 Mock 테스트")
@WebMvcTest(controllers = PublicDataController.class)
class PublicDataControllerTest extends ApiDocument {

    @MockBean
    public PublicDataService publicDataService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("백신 접종률 조회 - 성공")
    @Test
    void findVaccinationRate() throws Exception {
        //given
        List<VaccinationRateResponse> vaccinationRateResponses = toVaccinationRateResponse(LocalDateTime.now());
        //when
        willReturn(vaccinationRateResponses).given(publicDataService).findVaccinationRates();
        ResultActions response = 백신_정봉률_조회_요청();
        //then
        백신_접종률_조회_성공함(response);
    }

    @DisplayName("백신 접종률 조회 - 성공 - 요청 후 api 데이터가 없을 때")
    @Test
    void findVaccinationRateEmtpy() throws Exception {
        //given
        //when
        willReturn(Collections.emptyList()).given(publicDataService).findVaccinationRates();
        ResultActions response = 백신_정봉률_조회_요청();
        //then
        백신_접종률_없데이트전_조회_성공함(response);
    }


    private ResultActions 백신_정봉률_조회_요청() throws Exception {
        return mockMvc.perform(get("/api/v1/publicdatas/vaccinations"));
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

    private List<VaccinationRateResponse> toVaccinationRateResponse(LocalDateTime targetDateTime) {
        String expectDateTime = targetDateTime.toLocalDate().toString() + " 00:00:00";
        return Arrays.asList(
                new VaccinationRateResponse(19473657, 7146602, expectDateTime,
                        473850, 35955, "전국", 19947507, 7182557),
                new VaccinationRateResponse(3646765, 1329982, expectDateTime,
                        77877, 7123, "서울특별시", 3724642, 1337105),
                new VaccinationRateResponse(1352394, 477845, expectDateTime,
                        31252, 2628, "부산광역시", 1383646, 480473),
                new VaccinationRateResponse(848397, 309317, expectDateTime,
                        22726, 2688, "대구광역시", 871123, 312005),
                new VaccinationRateResponse(523814, 197608, expectDateTime,
                        12990, 959, "대전광역시", 536804, 198567),
                new VaccinationRateResponse(1032052, 360923, expectDateTime,
                        27394, 1760, "인천광역시", 1059446, 362683),
                new VaccinationRateResponse(549706, 204415, expectDateTime,
                        13700, 830, "광주광역시", 563406, 205245),
                new VaccinationRateResponse(396793, 124839, expectDateTime,
                        11275, 471, "울산광역시", 408068, 125310),
                new VaccinationRateResponse(4636237, 1636357, expectDateTime,
                        118717, 7895, "경기도", 4754954, 1644252),
                new VaccinationRateResponse(668422, 273253, expectDateTime,
                        14908, 1010, "강원도", 683330, 274263),
                new VaccinationRateResponse(109202, 37624, expectDateTime,
                        3028, 179, "세종특별자치시", 112230, 37803),
                new VaccinationRateResponse(639514, 240704, expectDateTime,
                        15886, 1324, "충청북도", 655400, 242028),
                new VaccinationRateResponse(849914, 330591, expectDateTime,
                        22167, 2054, "충청남도", 872081, 332645),
                new VaccinationRateResponse(789017, 311961, expectDateTime,
                        17331, 1270, "전라북도", 806348, 313231),
                new VaccinationRateResponse(859228, 344275, expectDateTime,
                        19083, 1378, "전라남도", 878311, 345653),
                new VaccinationRateResponse(1070878, 409752, expectDateTime,
                        27263, 2016, "경상북도", 1098141, 411768),
                new VaccinationRateResponse(1249910, 464110, expectDateTime,
                        31098, 1943, "경상남도", 1281008, 466053),
                new VaccinationRateResponse(251414, 93046, expectDateTime,
                        7155, 427, "제주특별자치도", 258569, 93473)
        );
    }
}