import React from 'react';
import { useHistory } from 'react-router-dom';
import { Button, Frame } from '../common';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from '../common/Button/Button.styles';
import { Img, buttonStyles, Title, Container, Calendar, A } from './VaccinationSchedule.styles';
import calendar from '../../assets/images/calendar.png';
import { NAVER_LEFT_VACCINATION_URL, PATH, VACCINATION_RESERVATION_URL } from '../../constants';

const VaccinationSchedule = () => {
  const history = useHistory();

  const goReviewPage = () => {
    history.push(PATH.REVIEW);
  };

  return (
    <Container>
      <Calendar>
        <Title>접종 일정</Title>
        <Frame showShadow={true} height="fit-content">
          <Img src={calendar} />
        </Frame>
      </Calendar>
      <A type="button" target="_blank" href={VACCINATION_RESERVATION_URL}>
        접종 예약 하러가기
      </A>
      <Button
        sizeType={BUTTON_SIZE_TYPE.LARGE}
        backgroundType={BUTTON_BACKGROUND_TYPE.OUTLINE}
        styles={buttonStyles}
        onClick={goReviewPage}
      >
        접종 후기 쓰러가기
      </Button>
      <A type="button" target="_blank" href={NAVER_LEFT_VACCINATION_URL}>
        잔여 백신 보러가기
      </A>
    </Container>
  );
};

export default VaccinationSchedule;
