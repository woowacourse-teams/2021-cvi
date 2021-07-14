import PropTypes from 'prop-types';
import Frame from '../Frame/Frame';
import { Container, modalFrameStyles, CloseButton } from './Modal.styles';
import CloseIcon from '../../assets/icons/close.svg';

const Modal = ({ children, showCloseButton, onClickClose }) => (
  <Container>
    <Frame styles={modalFrameStyles}>
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
  onClickClose: PropTypes.func,
};

Modal.defaultProps = {
  showCloseButton: false,
  onClickClose: () => {},
};

export default Modal;
