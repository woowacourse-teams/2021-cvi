package com.backjoongwon.cvi.publicdata.application;

import com.backjoongwon.cvi.common.exception.DuplicateException;
import com.backjoongwon.cvi.parser.VacinationParser;
import com.backjoongwon.cvi.publicdata.domain.PublicDataProperties;
import com.backjoongwon.cvi.publicdata.domain.VaccinationStatistic;
import com.backjoongwon.cvi.publicdata.domain.VaccinationStatisticRepository;
import com.backjoongwon.cvi.publicdata.dto.VaccinationStatisticResponse;
import com.backjoongwon.cvi.util.DateConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static com.backjoongwon.cvi.publicdata.PublicDataFacotry.REGIONS;
import static com.backjoongwon.cvi.publicdata.PublicDataFacotry.toVaccineParserResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("공공데이터 요청 서비스 흐름 테스트")
@Transactional
class PublicDataServiceTest {

    @Autowired
    private VaccinationStatisticRepository vaccinationStatisticRepository;
    @Autowired
    private PublicDataProperties publicDataProperties;

    @Autowired
    private PublicDataService publicDataService;
    private VacinationParser vacinationParser;

    @BeforeEach
    void init() {
        vacinationParser = mock(VacinationParser.class);
        publicDataService = new PublicDataService(vacinationParser, vaccinationStatisticRepository, publicDataProperties);
    }

    @AfterEach
    void clear() {
        vaccinationStatisticRepository.deleteAll();
    }

    public static Stream<Arguments> saveVaccinationStatistics() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59))),
                Arguments.of(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(9, 59, 59))),
                Arguments.of(LocalDateTime.of(LocalDate.of(2021, 3, 11), LocalTime.MIN))
        );
    }

    @DisplayName("백신 정종률 데이터 저장 - 성공 - 당일 오전 10시 후 or 오늘 이전 날짜로 요청시 성공")
    @ParameterizedTest
    @MethodSource
    void saveVaccinationStatistics(LocalDateTime targetDateTime) {
        //given
        //when
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
        백신_접종률_저장되어_있음(targetDateTime);
        List<VaccinationStatistic> publicData = vaccinationStatisticRepository.findByBaseDate(DateConverter.withZeroTime(targetDateTime));
        //then
        assertThat(publicData).extracting("baseDate")
                .contains(DateConverter.withZeroTime(targetDateTime));
    }

    @DisplayName("백신 정종률 데이터 저장 - 실패 - 오늘 날짜에 이미 저장되어 있음")
    @Test
    void saveVaccinationStatisticsFailureWhenAlreadySaved() {
        //given
        LocalDateTime targetDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        //when
        백신_접종률_저장되어_있음(targetDateTime);
        //then
        assertThatThrownBy(() -> publicDataService.saveVaccinationStatistics(targetDateTime))
                .isInstanceOf(DuplicateException.class);
    }

    @DisplayName("백신 정종률 데이터 조회 - 성공")
    @Test
    void findVaccinationStatistics() {
        //given
        LocalDateTime targetDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        //when
        백신_접종률_저장되어_있음(targetDateTime);
        List<VaccinationStatisticResponse> vaccinationStatistics = publicDataService.findVaccinationStatistics(targetDateTime);
        //then
        assertThat(vaccinationStatistics).isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getAccumulatedFirstCnt)
                .isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getAccumulatedSecondCnt)
                .isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getBaseDate)
                .contains(DateConverter.withZeroTime(targetDateTime));
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getSido)
                .containsAll(REGIONS);
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getFirstCnt)
                .isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getSecondCnt)
                .isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getTotalFirstCnt)
                .isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getTotalSecondCnt)
                .isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getTotalSecondCnt)
                .isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getAccumulateFirstRate)
                .isNotEmpty();
    }

    @DisplayName("백신 정종률 데이터 조회 - 성공 - 당일 오전 10시 전, 당일 데이터 요청일 시 전날 데이터 요청")
    @Test
    void findVaccinationStatisticsWhenTodayBeforeTen() {
        //given
        LocalDateTime targetDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 59, 59));
        //when
        백신_접종률_저장되어_있음(targetDateTime.minusDays(1));
        List<VaccinationStatisticResponse> vaccinationStatistics = publicDataService.findVaccinationStatistics(targetDateTime);
        //then
        assertThat(vaccinationStatistics).isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getBaseDate)
                .contains(DateConverter.withZeroTime(targetDateTime.minusDays(1)));
    }

    @DisplayName("백신 정종률 데이터 조회 - 성공 - 전일 오전 10시 전, 전일 데이터 요청일 시 해당 일 데이터 요청")
    @Test
    void findVaccinationStatisticsWhenYesterdayBeforeTen() {
        //given
        LocalDateTime targetDateTime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(9, 59, 59));
        //when
        백신_접종률_저장되어_있음(targetDateTime);
        List<VaccinationStatisticResponse> vaccinationStatistics = publicDataService.findVaccinationStatistics(targetDateTime);
        //then
        assertThat(vaccinationStatistics).isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getBaseDate)
                .contains(DateConverter.withZeroTime(targetDateTime));
    }

    private void 백신_접종률_저장되어_있음(LocalDateTime targetDateTime) {
        willReturn(toVaccineParserResponse(targetDateTime))
                .given(vacinationParser).parseToPublicData(any(LocalDateTime.class), anyString());
        publicDataService.saveVaccinationStatistics(targetDateTime);
    }
}
