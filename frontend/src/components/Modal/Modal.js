import PropTypes from 'prop-types';
import Frame from '../Frame/Frame';
import { Container, modalFrameStyles, CloseButton } from './Modal.styles';
import CloseIcon from '../../assets/icons/close.svg';

const Modal = ({ children, showCloseButton, showShadow, onClickClose }) => (
  <Container>
    <Frame styles={modalFrameStyles} showShadow={showShadow}>
      {showCloseButton && (
        <CloseButton onClick={onClickClose}>
          <CloseIcon />
        </CloseButton>
      )}
      {children}
    </Frame>
  </Container>
);

Modal.propTypes = {
  children: PropTypes.node.isRequired,
  showCloseButton: PropTypes.bool,
  showShadow: PropTypes.bool,
  onClickClose: PropTypes.func,
};

Modal.defaultProps = {
  showCloseButton: false,
  showShadow: false,
  onClickClose: () => {},
};

export default Modal;
