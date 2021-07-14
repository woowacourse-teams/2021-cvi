import React from 'react';
import PropTypes from 'prop-types';
import { InputElement } from './Input.styles';

const Input = ({ type, placeholder, width }) => (
  <label>
    <InputElement type={type} placeholder={placeholder} width={width} />
  </label>
);

Input.propTypes = {
  type: PropTypes.string,
  placeholder: PropTypes.string,
  width: PropTypes.string,
};

Input.defaultProps = {
  type: 'text',
  placeholder: '',
  width: '',
};

export default Input;
