import PropTypes from 'prop-types';
import { Container } from './ShotVerificationLabel.styles';

const ShotVerificationLabel = ({ shotVerification, trueText, falseText, styles }) => (
  <Container shotVerification={shotVerification} styles={styles}>
    {shotVerification ? trueText : falseText}
  </Container>
);

ShotVerificationLabel.propTypes = {
  shotVerification: PropTypes.bool,
  trueText: PropTypes.string.isRequired,
  falseText: PropTypes.string,
  styles: PropTypes.object,
};

ShotVerificationLabel.defaultProps = {
  shotVerification: false,
  falseText: '',
  styles: null,
};

export default ShotVerificationLabel;
