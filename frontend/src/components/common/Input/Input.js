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
  minLength,
  maxLength,
  onChange,
  ...props
}) => (
  <Label labelStyles={labelStyles}>
    <LabelText>{labelText}</LabelText>
    <InputElement
      type={type}
      placeholder={placeholder}
      width={width}
      value={value}
      inputStyles={inputStyles}
      minLength={minLength}
      maxLength={maxLength}
      onChange={onChange}
      {...props}
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
  minLength: PropTypes.number,
  maxLength: PropTypes.number,
  required: PropTypes.bool,
  onChange: PropTypes.func,
};

Input.defaultProps = {
  type: 'text',
  placeholder: '',
  width: '',
  labelText: '',
  value: '',
  minLength: 0,
  maxLength: 100000000,
  required: false,
  inputStyles: {},
  labelStyles: {},
  onChange: () => {},
};

export default Input;
