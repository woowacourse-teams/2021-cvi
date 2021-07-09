import React from 'react';
import PropTypes from 'prop-types';
import { COLOR, VACCINE_COLOR } from '../../constants';
import { Container, LABEL_SIZE_TYPE } from './Label.styles';

const Label = ({ children, sizeType, backgroundColor, fontColor }) => {
  return (
    <Container backgroundColor={backgroundColor} fontColor={fontColor} sizeType={sizeType}>
      {children}
    </Container>
  );
};

Label.propTypes = {
  children: PropTypes.node.isRequired,
  sizeType: PropTypes.string,
  backgroundColor: PropTypes.string,
  fontColor: PropTypes.string,
};

Label.defaultProps = {
  sizeType: LABEL_SIZE_TYPE.SMALL,
  backgroundColor: VACCINE_COLOR.PFIZER,
  fontColor: COLOR.WHITE,
};

export default Label;
