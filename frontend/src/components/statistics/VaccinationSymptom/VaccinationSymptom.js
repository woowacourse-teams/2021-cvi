import React from 'react';
import PropTypes from 'prop-types';
import { Frame, HorizontalBarChart } from '../../@common';
import { frameStyles, Title, Container, Source } from './VaccinationSymptom.styles';

const VaccinationSymptom = ({ title, withSource }) => (
  <Container>
    {title && <Title>{title}</Title>}
    <Frame showShadow={true} width="100%" height="30rem" styles={frameStyles}>
      <HorizontalBarChart />
      {withSource && <Source>출처: 자체 설문 조사</Source>}
    </Frame>
  </Container>
);

VaccinationSymptom.propTypes = {
  title: PropTypes.string,
  withSource: PropTypes.bool,
};

VaccinationSymptom.defaultProps = {
  title: '',
  withSource: false,
};

export default VaccinationSymptom;
