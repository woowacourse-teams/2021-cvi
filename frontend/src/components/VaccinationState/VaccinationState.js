import { useHistory } from 'react-router-dom';
import PropTypes from 'prop-types';
import { css } from '@emotion/react';
import {
  Container,
  TopContainer,
  Title,
  FrameContent,
  PrimaryState,
  Info,
  InfoTitle,
  IncreaseIcon,
} from './VaccinationState.styles';
import { Frame, DonutChart, Button } from '../common';
import { requestVaccinationStateList, requestWorldVaccinationStateList } from '../../requests';
import { useFetch } from '../../hooks';
import { numberWithCommas } from '../../utils';
import { BUTTON_BACKGROUND_TYPE } from '../common/Button/Button.styles';
import { FONT_COLOR, PATH } from '../../constants';
import { RightArrowIcon } from '../../assets/icons';

const VaccinationState = ({ title, withWorld, withViewMore }) => {
  const history = useHistory();

  const { response, error } = useFetch([], requestVaccinationStateList);
  const { response: worldState, error: worldError } = useFetch(
    [],
    requestWorldVaccinationStateList,
  );
  const koreanState = response[0];

  const goStatePage = () => {
    history.push(PATH.STATE);
  };

  return (
    <Container>
      <TopContainer>
        {title && <Title>{title}</Title>}
        {withViewMore && (
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
            color={FONT_COLOR.BLUE_GRAY}
            withIcon={true}
            styles={css`
              padding-right: 0;
            `}
            onClick={goStatePage}
          >
            <div>더보기</div>
            <RightArrowIcon width="18" height="18" stroke={FONT_COLOR.BLUE_GRAY} />
          </Button>
        )}
      </TopContainer>
      <Frame width="100%" showShadow={true}>
        <FrameContent withWorld={withWorld}>
          <PrimaryState>
            <DonutChart target={koreanState?.totalFirstRate ?? 0} />
            <Info>
              <InfoTitle>국내 1차 접종</InfoTitle>
              <div>누적 {numberWithCommas(Number(koreanState?.totalFirstCnt))}</div>
              <div>
                신규 {numberWithCommas(Number(koreanState?.firstCnt))}
                <IncreaseIcon>▴</IncreaseIcon>
              </div>
            </Info>
          </PrimaryState>
          <PrimaryState>
            <DonutChart target={koreanState?.totalSecondRate ?? 0} />
            <Info>
              <InfoTitle>국내 완전 접종</InfoTitle>
              <div>누적 {numberWithCommas(Number(koreanState?.totalSecondCnt))}</div>
              <div>
                신규 {numberWithCommas(Number(koreanState?.secondCnt))}
                <IncreaseIcon>▴</IncreaseIcon>
              </div>
            </Info>
          </PrimaryState>
          {withWorld && (
            <PrimaryState>
              <DonutChart target={worldState[0]?.totalSecondRate ?? 0} />
              <Info>
                <InfoTitle>세계 완전 접종</InfoTitle>
                <div>누적 {numberWithCommas(Number(worldState[0]?.totalSecondCnt))}</div>
              </Info>
            </PrimaryState>
          )}
        </FrameContent>
      </Frame>
    </Container>
  );
};

VaccinationState.propTypes = {
  title: PropTypes.string,
  withWorld: PropTypes.bool,
  withViewMore: PropTypes.bool,
};

VaccinationState.defaultProps = {
  title: '',
  withWorld: true,
  withViewMore: false,
};

export default VaccinationState;
