import PropTypes from 'prop-types';
import {
  Container,
  Title,
  FrameContent,
  PrimaryState,
  Info,
  InfoTitle,
} from './VaccinationState.styles';
import { Frame, DonutChart } from '../common';

const VaccinationState = ({ title, withWorld }) => (
  <Container>
    {title && <Title>{title}</Title>}
    <Frame width="100%" showShadow={true}>
      <FrameContent withWorld={withWorld}>
        <PrimaryState>
          <DonutChart target="35.8" />
          <Info>
            <InfoTitle>국내 1차 접종</InfoTitle>
            <div>누적 18,382,137</div>
            <div>신규 477,853</div>
          </Info>
        </PrimaryState>
        <PrimaryState>
          <DonutChart target="13.7" />
          <Info>
            <InfoTitle>국내 완전 접종</InfoTitle>
            <div>누적 7,018,654</div>
            <div>신규 40,086</div>
          </Info>
        </PrimaryState>
        {withWorld && (
          <PrimaryState>
            <DonutChart target="14.6" />
            <Info>
              <InfoTitle>세계 완전 접종</InfoTitle>
              <div>누적 1,096,011,434</div>
              {/* <div>신규 40,086</div> */}
            </Info>
          </PrimaryState>
        )}
      </FrameContent>
    </Frame>
  </Container>
);

VaccinationState.propTypes = {
  title: PropTypes.string,
  withWorld: PropTypes.bool,
};

VaccinationState.defaultProps = {
  title: '',
  withWorld: true,
};

export default VaccinationState;
