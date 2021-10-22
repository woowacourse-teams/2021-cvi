import PropTypes from 'prop-types';
import { Container } from './Snackbar.styles';

const Snackbar = ({ children }) => <Container>{children}</Container>;

Snackbar.propTypes = {
  children: PropTypes.string.isRequired,
};

export default Snackbar;
