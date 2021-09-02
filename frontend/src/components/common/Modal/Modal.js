import PropTypes from 'prop-types';
import { CloseIcon } from '../../../assets/icons';
import { THEME_COLOR } from '../../../constants';
import Frame from '../Frame/Frame';
import { Container, modalFrameStyles, CloseButton } from './Modal.styles';

const Modal = ({ children, showCloseButton, showShadow, backgroundColor, onClickClose }) => {
  const closeModal = (event) => {
    if (event.target !== event.currentTarget) return;

    onClickClose();
  };

  return (
    <Container onClick={closeModal}>
      <Frame styles={modalFrameStyles} showShadow={showShadow} backgroundColor={backgroundColor}>
        {showCloseButton && (
          <CloseButton onClick={onClickClose}>
            <CloseIcon />
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
  showShadow: PropTypes.bool,
  backgroundColor: PropTypes.string,
  onClickClose: PropTypes.func,
};

Modal.defaultProps = {
  showCloseButton: false,
  showShadow: false,
  backgroundColor: THEME_COLOR.WHITE,
  onClickClose: () => {},
};

export default Modal;
