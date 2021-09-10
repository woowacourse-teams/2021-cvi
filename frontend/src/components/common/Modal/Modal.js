import PropTypes from 'prop-types';
import { CloseIcon } from '../../../assets/icons';
import { THEME_COLOR } from '../../../constants';
import Frame from '../Frame/Frame';
import { Container, modalFrameStyles, CloseButton } from './Modal.styles';

const Modal = ({
  children,
  showCloseButton,
  showShadow,
  backgroundColor,
  frameColor,
  onClickClose,
}) => {
  const closeModal = (event) => {
    if (event.target !== event.currentTarget) return;

    onClickClose();
  };

  return (
    <Container backgroundColor={backgroundColor} onClick={closeModal}>
      <Frame styles={modalFrameStyles} showShadow={showShadow} backgroundColor={frameColor}>
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
  frameColor: PropTypes.string,
  backgroundColor: PropTypes.string,
  onClickClose: PropTypes.func,
};

Modal.defaultProps = {
  showCloseButton: false,
  showShadow: false,
  frameColor: THEME_COLOR.WHITE,
  backgroundColor: 'rgba(0, 0, 0, 0.5)',
  onClickClose: () => {},
};

export default Modal;
