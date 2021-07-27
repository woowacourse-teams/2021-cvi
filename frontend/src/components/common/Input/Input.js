import PropTypes from 'prop-types';
import { Label, LabelText, InputElement } from './Input.styles';

const Input = ({ type, placeholder, width, labelText, value, onChange }) => (
  <Label>
    <LabelText>{labelText}</LabelText>
    <InputElement
      type={type}
      placeholder={placeholder}
      width={width}
      value={value}
      onChange={onChange}
    />
  </Label>
);

Input.propTypes = {
  type: PropTypes.string,
  placeholder: PropTypes.string,
  width: PropTypes.string,
  labelText: PropTypes.string,
  value: PropTypes.string,
  onChange: PropTypes.func,
};

Input.defaultProps = {
  type: 'text',
  placeholder: '',
  width: '',
  labelText: '',
  value: '',
  onChange: () => {},
};

export default Input;
