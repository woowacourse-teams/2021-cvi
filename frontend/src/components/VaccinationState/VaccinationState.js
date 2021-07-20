import React from 'react';
import PropTypes from 'prop-types';
import Frame from '../Frame/Frame';
import { Container, Title } from './VaccinationState.styles';

const VaccinationState = ({ title }) => (
  <Container>
    {title && <Title>{title}</Title>}
    <Frame width="100%" showShadow={true}>
      <div style={{ display: 'flex', alignItems: 'center', height: '12rem' }}>준비 중입니다</div>
    </Frame>
  </Container>
);

VaccinationState.propTypes = {
  title: PropTypes.string,
};

VaccinationState.defaultProps = {
  title: '',
};

export default VaccinationState;
