import PropTypes from 'prop-types';
import {
  Container,
  Title,
  FrameContent,
  PrimaryState,
  Info,
  InfoTitle,
  IncreaseIcon,
} from './VaccinationState.styles';
import { Frame, DonutChart } from '../common';
import { requestVaccinationStateList } from '../../requests';
import { useFetch } from '../../hooks';
import { numberWithCommas } from '../../utils';

const VaccinationState = ({ title, withWorld }) => {
  const { response, error, loading } = useFetch([], requestVaccinationStateList);
  const koreanState = response[0];

  return (
    <Container>
      {title && <Title>{title}</Title>}
      <Frame width="100%" showShadow={true}>
        <FrameContent withWorld={withWorld}>
          <PrimaryState>
            <DonutChart target={koreanState?.accumulatedFirstRate} />
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
            <DonutChart target={koreanState?.accumulatedSecondRate} />
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
              <DonutChart target={15.3} />
              <Info>
                <InfoTitle>세계 완전 접종</InfoTitle>
                <div>누적 1,096,011,434</div>
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
};

VaccinationState.defaultProps = {
  title: '',
  withWorld: true,
};

export default VaccinationState;
