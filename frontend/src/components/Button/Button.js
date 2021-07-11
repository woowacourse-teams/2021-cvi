import React from 'react';
import PropTypes from 'prop-types';
import { BUTTON_BACKGROUND_TYPE, BUTTON_SIZE_TYPE, Container } from './Button.styles';
import { PALETTE } from '../../constants';

const Button = ({
  children,
  backgroundType,
  sizeType,
  withIcon,
  color,
  isSelected,
  selectedStyles,
  styles,
}) => {
  return (
    <Container
      backgroundType={backgroundType}
      sizeType={sizeType}
      withIcon={withIcon}
      color={color}
      isSelected={isSelected}
      selectedStyles={selectedStyles}
      styles={styles}
    >
      {children}
    </Container>
  );
};

Button.propTypes = {
  children: PropTypes.node.isRequired,
  backgroundType: PropTypes.string,
  sizeType: PropTypes.string,
  withIcon: PropTypes.bool,
  color: PropTypes.string,
  isSelected: PropTypes.bool,
  selectedStyles: PropTypes.node,
  styles: PropTypes.node,
};

Button.defaultProps = {
  backgroundType: BUTTON_BACKGROUND_TYPE.FILLED,
  sizeType: BUTTON_SIZE_TYPE.MEDIUM,
  withIcon: false,
  color: PALETTE.RED300,
  isSelected: false,
  selectedStyles: null,
  styles: null,
};

export default Button;
