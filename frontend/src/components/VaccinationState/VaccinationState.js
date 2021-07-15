import React from 'react';
import PropTypes from 'prop-types';
import Frame from '../Frame/Frame';
import { Title } from './VaccinationState.styles';

const VaccinationState = ({ title }) => (
  <div>
    {title && <Title>{title}</Title>}
    <Frame width="100%">
      <div style={{ height: '10rem' }}></div>
    </Frame>
  </div>
);

VaccinationState.propTypes = {
  title: PropTypes.string,
};

VaccinationState.defaultProps = {
  title: '',
};

export default VaccinationState;
