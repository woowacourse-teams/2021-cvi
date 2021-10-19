import { VaccinationState, Preview, VaccinationSymptom } from '../../components';
import { Button } from '../../components/@common';
import {
  BUTTON_BACKGROUND_TYPE,
  BUTTON_SIZE_TYPE,
} from '../../components/@common/Button/Button.styles';
import {
  FILTER_TYPE,
  NAVER_LEFT_VACCINATION_URL,
  VACCINATION_RESERVATION_URL,
} from '../../constants';
import { useMovePage } from '../../hooks';
import { Container, VaccinationSymptomColumn, A, buttonStyles } from './HomePage.styles';

const HomePage = () => {
  const { goReviewPage } = useMovePage();

  return (
    <Container>
      <VaccinationState title="접종 현황" withViewMore={true} withSource={true} />
      <Preview title="최신 글" reviewType={FILTER_TYPE.CREATED_AT} />
      <Preview title="실시간 인기 글" reviewType={FILTER_TYPE.LIKE_COUNT} />
      <VaccinationSymptomColumn>
        <VaccinationSymptom title="접종 증상" withSource={true} />
        <A
          type="button"
          target="_blank"
          href={VACCINATION_RESERVATION_URL}
          rel="noopener noreferrer"
        >
          접종 예약 하러가기
        </A>
        <Button
          sizeType={BUTTON_SIZE_TYPE.LARGE}
          backgroundType={BUTTON_BACKGROUND_TYPE.OUTLINE}
          styles={buttonStyles}
          onClick={() => goReviewPage()}
        >
          접종 후기 쓰러가기
        </Button>
        <A
          type="button"
          target="_blank"
          href={NAVER_LEFT_VACCINATION_URL}
          rel="noopener noreferrer"
        >
          잔여 백신 보러가기
        </A>
      </VaccinationSymptomColumn>
    </Container>
  );
};

export default HomePage;
