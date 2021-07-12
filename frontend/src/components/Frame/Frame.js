import React from 'react';
import PropTypes from 'prop-types';
import { Container } from './Frame.styles';

const Frame = ({ children, backgroundColor, width, height, styles }) => {
  return (
    <Container backgroundColor={backgroundColor} width={width} height={height} styles={styles}>
      {children}
    </Container>
  );
};

Frame.propTypes = {
  children: PropTypes.node.isRequired,
  backgroundColor: PropTypes.string.isRequired,
  width: PropTypes.string.isRequired,
  height: PropTypes.string.isRequired,
  styles: PropTypes.object,
};

Frame.defaultProps = {
  styles: {},
};

export default Frame;
