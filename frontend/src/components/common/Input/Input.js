import PropTypes from 'prop-types';
import { Label, LabelText, InputElement } from './Input.styles';

const Input = ({
  type,
  placeholder,
  width,
  labelText,
  value,
  inputStyles,
  labelStyles,
  onChange,
}) => (
  <Label labelStyles={labelStyles}>
    <LabelText>{labelText}</LabelText>
    <InputElement
      type={type}
      placeholder={placeholder}
      width={width}
      value={value}
      inputStyles={inputStyles}
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
  inputStyles: PropTypes.object,
  labelStyles: PropTypes.object,
  onChange: PropTypes.func,
};

Input.defaultProps = {
  type: 'text',
  placeholder: '',
  width: '',
  labelText: '',
  value: '',
  inputStyles: {},
  labelStyles: {},
  onChange: () => {},
};

export default Input;
