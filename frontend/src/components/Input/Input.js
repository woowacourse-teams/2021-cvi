import React from 'react';
import PropTypes from 'prop-types';
import { Label, LabelText, InputElement } from './Input.styles';

const Input = ({ type, placeholder, width, labelText }) => (
  <Label>
    <LabelText>{labelText}</LabelText>
    <InputElement type={type} placeholder={placeholder} width={width} />
  </Label>
);

Input.propTypes = {
  type: PropTypes.string,
  placeholder: PropTypes.string,
  width: PropTypes.string,
  labelText: PropTypes.string,
};

Input.defaultProps = {
  type: 'text',
  placeholder: '',
  width: '',
  labelText: '',
};

export default Input;
