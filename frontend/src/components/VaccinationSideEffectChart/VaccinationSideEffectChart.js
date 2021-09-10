import PropTypes from 'prop-types';

import {
  Container,
  SideEffectContainer,
  SideEffect,
  Bar,
  Percentage,
} from './VaccinationSideEffectChart.styles';

const VaccinationSideEffectChart = ({ sideEffects }) => (
  <Container>
    {sideEffects.map((sideEffect, index) => (
      <SideEffectContainer key={index} index={index}>
        <SideEffect>{sideEffect.symptom}</SideEffect>
        <Bar index={index} percentage={sideEffect.percentage} />
        <Percentage>{`${sideEffect.percentage}%`}</Percentage>
      </SideEffectContainer>
    ))}
  </Container>
);

VaccinationSideEffectChart.propTypes = {
  sideEffects: PropTypes.array.isRequired,
};

export default VaccinationSideEffectChart;
