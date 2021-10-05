import PropTypes from 'prop-types';
import {
  Container,
  TopContainer,
  Title,
  FrameContent,
  PrimaryState,
  Info,
  InfoTitle,
  IncreaseIcon,
  buttonStyles,
  Source,
} from './VaccinationState.styles';
import { Frame, DonutChart, Button } from '../../@common';
import {
  fetchGetVaccinationStateList,
  fetchGetWorldVaccinationStateList,
} from '../../../service/fetch';
import { useFetch, useMovePage } from '../../../hooks';
import { numberWithCommas } from '../../../utils';
import { BUTTON_BACKGROUND_TYPE } from '../../@common/Button/Button.styles';
import { FONT_COLOR } from '../../../constants';
import { RightArrowIcon } from '../../../assets/icons';

const VaccinationState = ({ title, withWorld, withViewMore, withSource }) => {
  const { response, error } = useFetch([], fetchGetVaccinationStateList);
  const { response: worldState, error: worldError } = useFetch(
    [],
    fetchGetWorldVaccinationStateList,
  );
  const { goStatePage } = useMovePage();

  const koreanState = response[0];

  return (
    <Container>
      <TopContainer>
        {title && <Title>{title}</Title>}
        {withViewMore && (
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
            color={FONT_COLOR.BLUE_GRAY}
            withIcon={true}
            styles={buttonStyles}
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
              <div>누적 {numberWithCommas(Number(koreanState?.totalFirstCnt ?? 0))}</div>
              <div>
                신규 {numberWithCommas(Number(koreanState?.firstCnt ?? 0))}
                <IncreaseIcon>▴</IncreaseIcon>
              </div>
            </Info>
          </PrimaryState>
          <PrimaryState>
            <DonutChart target={koreanState?.totalSecondRate ?? 0} />
            <Info>
              <InfoTitle>국내 완전 접종</InfoTitle>
              <div>누적 {numberWithCommas(Number(koreanState?.totalSecondCnt ?? 0))}</div>
              <div>
                신규 {numberWithCommas(Number(koreanState?.secondCnt ?? 0))}
                <IncreaseIcon>▴</IncreaseIcon>
              </div>
            </Info>
          </PrimaryState>
          {withWorld && (
            <PrimaryState>
              <DonutChart target={worldState[0]?.totalSecondRate ?? 0} />
              <Info>
                <InfoTitle>세계 완전 접종</InfoTitle>
                <div>누적 {numberWithCommas(Number(worldState[0]?.totalSecondCnt ?? 0))}</div>
              </Info>
            </PrimaryState>
          )}
          {withSource && <Source>출처: 질병관리청, Our World in Data</Source>}
        </FrameContent>
      </Frame>
    </Container>
  );
};

VaccinationState.propTypes = {
  title: PropTypes.string,
  withWorld: PropTypes.bool,
  withViewMore: PropTypes.bool,
  withSource: PropTypes.bool,
};

VaccinationState.defaultProps = {
  title: '',
  withWorld: true,
  withViewMore: false,
  withSource: false,
};

export default VaccinationState;
