import PropTypes from 'prop-types';
import { Background, Container } from './BaseLayout.styles';

const BaseLayout = ({ children }) => {
  return (
    <Background>
      <Container>{children}</Container>
    </Background>
  );
};

BaseLayout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default BaseLayout;
