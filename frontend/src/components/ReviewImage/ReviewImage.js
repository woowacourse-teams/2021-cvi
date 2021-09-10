import PropTypes from 'prop-types';
import { ImageDetailIcon } from '../../assets/icons';
import { FONT_COLOR } from '../../constants';
import { Button } from '../common';
import {
  Container,
  Image,
  DetailIconWrapper,
  CloseIconWrapper,
  deleteImageButtonStyles,
} from './ReviewImage.styles';

const ReviewImage = ({
  src,
  width,
  showDetailIcon,
  showCloseIcon,
  onClick,
  onClickDeleteButton,
}) => (
  <Container onClick={onClick}>
    {showDetailIcon && (
      <DetailIconWrapper>
        <ImageDetailIcon />
      </DetailIconWrapper>
    )}
    {showCloseIcon && (
      <CloseIconWrapper>
        <Button
          color={FONT_COLOR.BLUE_GRAY}
          styles={deleteImageButtonStyles}
          onClick={onClickDeleteButton}
        >
          +
        </Button>
      </CloseIconWrapper>
    )}
    <Image src={src} width={width} />
  </Container>
);

ReviewImage.propTypes = {
  src: PropTypes.string.isRequired,
  onClick: PropTypes.func,
  showDetailIcon: PropTypes.bool,
  showCloseIcon: PropTypes.bool,
  onClickDeleteButton: PropTypes.func,
  width: PropTypes.string,
};

ReviewImage.defaultProps = {
  showDetailIcon: false,
  showCloseIcon: false,
  width: '',
  onClickDeleteButton: () => {},
  onClick: () => {},
};

export default ReviewImage;
