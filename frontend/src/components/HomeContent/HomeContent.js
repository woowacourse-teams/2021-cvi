import React from 'react';
import { useHistory } from 'react-router-dom';
import PropTypes from 'prop-types';
import { Button, Frame, HorizontalBarChart } from '../common';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE } from '../common/Button/Button.styles';
import {
  buttonStyles,
  frameStyles,
  Title,
  Container,
  Content,
  A,
  Source,
} from './HomeContent.styles';
import { NAVER_LEFT_VACCINATION_URL, PATH, VACCINATION_RESERVATION_URL } from '../../constants';

const HomeContent = ({ title, withSource }) => {
  const history = useHistory();

  const goReviewPage = () => {
    history.push(PATH.REVIEW);
  };

  return (
    <Container>
      <Content>
        {title && <Title>{title}</Title>}
        <Frame showShadow={true} width="100%" height="30rem" styles={frameStyles}>
          <HorizontalBarChart />
          {withSource && <Source>출처: 자체 설문 조사</Source>}
        </Frame>
      </Content>
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

HomeContent.propTypes = {
  title: PropTypes.string,
  withSource: PropTypes.bool,
};

HomeContent.defaultProps = {
  title: '',
  withSource: false,
};

export default HomeContent;
