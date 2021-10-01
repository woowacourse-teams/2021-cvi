import PropTypes from 'prop-types';
import { Modal } from '../../@common';
import { Container, Image } from './ImageModal.styles';

const ImageModal = ({ src, onClickClose }) => {
  const closeModal = (event) => {
    if (event.target !== event.currentTarget) return;

    onClickClose();
  };

  return (
    <Modal
      showShadow={false}
      backgroundColor="rgba(0, 0, 0, 0.7)"
      frameColor="trasparent"
      onClickClose={onClickClose}
    >
      <Container onClick={closeModal}>
        <Image src={src} />
      </Container>
    </Modal>
  );
};

ImageModal.propTypes = {
  src: PropTypes.string.isRequired,
  onClickClose: PropTypes.func.isRequired,
};

export default ImageModal;
