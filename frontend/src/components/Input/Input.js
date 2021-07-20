import React from 'react';
import PropTypes from 'prop-types';
import { Label, LabelText, InputElement } from './Input.styles';

const Input = ({ type, placeholder, width, labelText, onChange }) => (
  <Label>
    <LabelText>{labelText}</LabelText>
    <InputElement type={type} placeholder={placeholder} width={width} onChange={onChange} />
  </Label>
);

Input.propTypes = {
  type: PropTypes.string,
  placeholder: PropTypes.string,
  width: PropTypes.string,
  labelText: PropTypes.string,
  onChange: PropTypes.func,
};

Input.defaultProps = {
  type: 'text',
  placeholder: '',
  width: '',
  labelText: '',
  onChange: () => {},
};

export default Input;
