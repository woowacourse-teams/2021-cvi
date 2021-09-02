import PropTypes from 'prop-types';
import { THEME_COLOR } from '../../constants';
import { Button, Modal } from '../common';
import { BUTTON_BACKGROUND_TYPE } from '../common/Button/Button.styles';
import { Container, Image, nextButtonStyles, previousButtonStyles } from './ImageModal.styles';

const ImageModal = ({
  imageList,
  openedImageIndex,
  onClickClose,
  onClickPrevious,
  onClickNext,
}) => {
  const closeModal = (event) => {
    if (event.target !== event.currentTarget) return;

    onClickClose();
  };

  return (
    <Modal showShadow={false} backgroundColor="trasparent" onClickClose={onClickClose}>
      <Container onClick={closeModal}>
        {openedImageIndex > 0 && (
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.OUTLINE}
            color={THEME_COLOR.FULL_BLACK}
            styles={previousButtonStyles}
            onClick={onClickPrevious}
          />
        )}

        <Image src={imageList[openedImageIndex]} />
        {openedImageIndex < imageList.length - 1 && (
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.OUTLINE}
            color={THEME_COLOR.FULL_BLACK}
            styles={nextButtonStyles}
            onClick={onClickNext}
          />
        )}
      </Container>
    </Modal>
  );
};

ImageModal.propTypes = {
  imageList: PropTypes.array.isRequired,
  openedImageIndex: PropTypes.number.isRequired,
  onClickClose: PropTypes.func.isRequired,
  onClickPrevious: PropTypes.func.isRequired,
  onClickNext: PropTypes.func.isRequired,
};

export default ImageModal;
