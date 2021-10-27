package com.cvi.service;

import static com.cvi.PublicDataFactory.REGIONS;
import static com.cvi.PublicDataFactory.toVaccineParserResponse;
import static com.cvi.PublicDataFactory.toWorldVaccinationParserResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

import com.cvi.CustomParameterizedTest;
import com.cvi.dto.VaccinationStatisticResponse;
import com.cvi.parser.VaccinationParser;
import com.cvi.properties.PublicDataProperties;
import com.cvi.publicdata.domain.model.RegionPopulation;
import com.cvi.publicdata.domain.model.VaccinationStatistic;
import com.cvi.publicdata.domain.repository.VaccinationStatisticRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    private VaccinationParser vaccinationParser;

    public static Stream<Arguments> koreaVaccinationTargetDate() {
        return Stream.of(
            Arguments.of(LocalDate.now()),
            Arguments.of(LocalDate.now().minusDays(1)),
            Arguments.of(LocalDate.of(2021, 3, 11))
        );
    }

    public static Stream<Arguments> worldVaccinationTargetDate() {
        return Stream.of(
                Arguments.of(LocalDate.now()),
                Arguments.of(LocalDate.now().minusDays(1)),
                Arguments.of(LocalDate.now().minusDays(2))
        );
    }

    @BeforeEach
    void init() {
        vaccinationParser = mock(VaccinationParser.class);
        publicDataService = new PublicDataService(vaccinationParser, vaccinationStatisticRepository, publicDataProperties);
    }

    @AfterEach
    void clear() {
        vaccinationStatisticRepository.deleteAll();
    }

    @DisplayName("백신 정종률 데이터 저장 - 성공")
    @CustomParameterizedTest
    @MethodSource("koreaVaccinationTargetDate")
    void saveVaccinationStatistics(LocalDate targetDate) {
        //given
        //when
        백신_접종률_저장되어_있음(targetDate);
        List<VaccinationStatistic> publicData = vaccinationStatisticRepository.findByBaseDate(targetDate);
        //then
        assertThat(publicData).extracting(VaccinationStatistic::getBaseDate)
            .contains(targetDate);
    }

    @DisplayName("백신 정종률 데이터 조회 - 성공")
    @CustomParameterizedTest
    @MethodSource("koreaVaccinationTargetDate")
    void findVaccinationStatistics(LocalDate targetDate) {
        //given
        //when
        백신_접종률_저장되어_있음(targetDate);
        List<VaccinationStatisticResponse> vaccinationStatistics = publicDataService.findVaccinationStatistics(targetDate);
        //then
        assertThat(vaccinationStatistics).isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getAccumulatedFirstCnt)
            .isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getAccumulatedSecondCnt)
            .isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getBaseDate)
            .contains(targetDate);
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
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getTotalFirstRate)
            .isNotEmpty();
        assertThat(vaccinationStatistics).extracting(VaccinationStatisticResponse::getTotalSecondRate)
                .isNotEmpty();
    }

    @DisplayName("세계 백신 정좁률 데이터 - 저장 - 성공 ")
    @CustomParameterizedTest
    @MethodSource("worldVaccinationTargetDate")
    void saveWorldVaccinationStatistics(LocalDate targetDate) {
        //given
        //when
        List<VaccinationStatisticResponse> vaccinationStatisticResponses = 세계_백신_접종률_저장되어_있음(targetDate);
        //then
        assertThat(vaccinationStatisticResponses).extracting(VaccinationStatisticResponse::getBaseDate)
            .contains(targetDate);
        assertThat(vaccinationStatisticResponses).extracting(VaccinationStatisticResponse::getSido)
            .contains(RegionPopulation.WORLD.getRegion());
    }

    @DisplayName("세계 백신 접종률 데이터 - 조회 - 성공")
    @CustomParameterizedTest
    @MethodSource("worldVaccinationTargetDate")
    void findWorldVaccinationStatistics(LocalDate targetDate) {
        //given
        //when
        세계_백신_접종률_저장되어_있음(targetDate);
        final List<VaccinationStatisticResponse> vaccinationStatisticResponses = publicDataService.findWorldVaccinationStatistics(targetDate);
        //then
        assertThat(vaccinationStatisticResponses).extracting(VaccinationStatisticResponse::getBaseDate)
                .contains(targetDate);
        assertThat(vaccinationStatisticResponses).extracting(VaccinationStatisticResponse::getSido)
                .contains(RegionPopulation.WORLD.getRegion());
    }

    private void 백신_접종률_저장되어_있음(LocalDate targetDate) {
        willReturn(toVaccineParserResponse(targetDate))
            .given(vaccinationParser).parseToKoreaPublicData(any(LocalDate.class), anyString());
        publicDataService.saveVaccinationStatistics(targetDate);
    }

    private List<VaccinationStatisticResponse> 세계_백신_접종률_저장되어_있음(LocalDate targetDate) {
        willReturn(toWorldVaccinationParserResponse(targetDate)).given(vaccinationParser).parseToWorldPublicData();
        return publicDataService.saveWorldVaccinationStatistics(targetDate);
    }
}
