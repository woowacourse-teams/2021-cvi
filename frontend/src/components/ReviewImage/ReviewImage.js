import PropTypes from 'prop-types';
import { ImageDetailIcon } from '../../assets/icons';
import { Container, Image, IconWrapper } from './ReviewImage.styles';

const ReviewImage = ({ src, onClick }) => (
  <Container onClick={onClick}>
    <IconWrapper>
      <ImageDetailIcon />
    </IconWrapper>
    <Image src={src} />
  </Container>
);

ReviewImage.propTypes = {
  src: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default ReviewImage;
