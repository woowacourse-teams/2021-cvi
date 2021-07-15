import React from 'react';
import PropTypes from 'prop-types';
import { Container } from './Frame.styles';
import { THEME_COLOR } from '../../constants';

const Frame = ({ children, backgroundColor, width, height, styles }) => {
  return (
    <Container backgroundColor={backgroundColor} width={width} height={height} styles={styles}>
      {children}
    </Container>
  );
};

Frame.propTypes = {
  children: PropTypes.node.isRequired,
  backgroundColor: PropTypes.string,
  width: PropTypes.string,
  height: PropTypes.string,
  styles: PropTypes.object,
};

Frame.defaultProps = {
  backgroundColor: THEME_COLOR.WHITE,
  width: 'fit-content',
  height: 'fit-content',
  styles: {},
};

export default Frame;
