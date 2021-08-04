package com.backjoongwon.cvi.publicdata.application;

import com.backjoongwon.cvi.parser.VacinationParser;
import com.backjoongwon.cvi.publicdata.domain.PublicDataProperties;
import com.backjoongwon.cvi.publicdata.domain.VaccinationRate;
import com.backjoongwon.cvi.publicdata.domain.VaccinationRateRepository;
import com.backjoongwon.cvi.publicdata.dto.VaccinationRateResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.backjoongwon.cvi.publicdata.PublicDataFacotry.toVaccineParserResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("공공데이터 요청 서비스 테스트")
@Transactional
class PublicDataServiceTest {

    public static final List<String> REGIONS = Arrays.asList("전국", "부산광역시", "대구광역시", "대전광역시", "인천광역시", "광주광역시",
            "울산광역시", "경기도", "강원도", "세종특별자치시", "충청북도", "충청남도",
            "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도");

    @Autowired
    private VaccinationRateRepository vaccinationRateRepository;
    @Autowired
    private PublicDataProperties publicDataProperties;

    @Autowired
    private PublicDataService publicDataService;
    private VacinationParser vacinationParser;

    @BeforeEach
    void init() {
        vacinationParser = mock(VacinationParser.class);
        publicDataService = new PublicDataService(vacinationParser, vaccinationRateRepository, publicDataProperties);
        willReturn(toVaccineParserResponse(LocalDateTime.now())).given(vacinationParser).parseToPublicData(any(LocalDateTime.class), anyString());

        LocalDateTime targetDateTime = LocalDateTime.now();
        willReturn(toVaccineParserResponse(targetDateTime)).given(vacinationParser).parseToPublicData(any(LocalDateTime.class), anyString());
        publicDataService.saveVaccinationRates(targetDateTime.toLocalDate());
        willReturn(toVaccineParserResponse(targetDateTime)).given(vacinationParser).parseToPublicData(any(LocalDateTime.class), anyString());
        publicDataService.saveVaccinationRates(targetDateTime.minusDays(1).toLocalDate());
    }

    @AfterEach
    void clear() {
        vaccinationRateRepository.deleteAll();
    }

    @DisplayName("백신 정종률 데이터 조회 - 성공")
    @Test
    void findVaccinationRates() {
        //given
        //when
        List<VaccinationRateResponse> vaccinationRates = publicDataService.findVaccinationRates(LocalDate.now());
        //then
        assertThat(vaccinationRates).isNotEmpty();
        assertThat(vaccinationRates).extracting(VaccinationRateResponse::getAccumulatedFirstCnt)
                .isNotEmpty();
        assertThat(vaccinationRates).extracting(VaccinationRateResponse::getAccumulatedSecondCnt)
                .isNotEmpty();
        assertThat(vaccinationRates).extracting(VaccinationRateResponse::getBaseDate)
                .contains(LocalDateTime.now().toLocalDate().toString() + " 00:00:00");
        assertThat(vaccinationRates).extracting(VaccinationRateResponse::getSido)
                .containsAll(REGIONS);
        assertThat(vaccinationRates).extracting(VaccinationRateResponse::getFirstCnt)
                .isNotEmpty();
        assertThat(vaccinationRates).extracting(VaccinationRateResponse::getSecondCnt)
                .isNotEmpty();
        assertThat(vaccinationRates).extracting(VaccinationRateResponse::getTotalFirstCnt)
                .isNotEmpty();
        assertThat(vaccinationRates).extracting(VaccinationRateResponse::getTotalSecondCnt)
                .isNotEmpty();
        assertThat(vaccinationRates).extracting(VaccinationRateResponse::getTotalSecondCnt)
                .isNotEmpty();
    }

    @DisplayName("백신 정종률 데이터 저장 - 성공")
    @Test
    void saveVaccinationRates() {
        //given
        //when
        List<VaccinationRate> publicData = vaccinationRateRepository.findByBaseDate(LocalDateTime.now().toLocalDate() + " 00:00:00");
        //then
        assertThat(publicData).extracting("sido")
                .containsAll(REGIONS);
    }
}