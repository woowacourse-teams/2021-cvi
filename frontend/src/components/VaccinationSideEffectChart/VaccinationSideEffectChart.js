import PropTypes from 'prop-types';

import {
  Container,
  SymptomContainer,
  Name,
  Bar,
  Percentage,
} from './VaccinationSideEffectChart.styles';

const VaccinationSideEffectChart = ({ symptomList }) => (
  <Container>
    {symptomList.map((symptom, index) => (
      <SymptomContainer key={index} index={index}>
        <Name>{symptom.name}</Name>
        <Bar index={index} percentage={symptom.percentage} />
        <Percentage>{`${symptom.percentage}%`}</Percentage>
      </SymptomContainer>
    ))}
  </Container>
);

VaccinationSideEffectChart.propTypes = {
  symptomList: PropTypes.arrayOf(
    PropTypes.shape({
      name: PropTypes.string.isRequired,
      percentage: PropTypes.number.isRequired,
    }),
  ).isRequired,
};

export default VaccinationSideEffectChart;
