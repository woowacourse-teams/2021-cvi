import PropTypes from 'prop-types';
import Frame from '../Frame/Frame';
import { Container, modalFrameStyles, CloseButton } from './Modal.styles';

const Modal = ({ children, showCloseButton, onClickClose }) => {
  return (
    <Container>
      <Frame styles={modalFrameStyles}>
        {showCloseButton && (
          <CloseButton onClick={onClickClose}>
            <svg viewBox="0 0 40 40">
              <path d="M 10,10 L 30,30 M 30,10 L 10,30" />
            </svg>
          </CloseButton>
        )}
        {children}
      </Frame>
    </Container>
  );
};

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
