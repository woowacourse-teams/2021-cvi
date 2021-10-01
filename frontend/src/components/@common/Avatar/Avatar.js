import PropTypes from 'prop-types';
import { AVATAR_SIZE_TYPE, Container } from './Avatar.styles';
import defaultImg from '../../../assets/images/default_profile.png';

const Avatar = ({ sizeType, src, styles, onClick }) => (
  <Container
    sizeType={sizeType}
    src={src ? src : defaultImg}
    styles={styles}
    onClick={onClick}
  ></Container>
);

Avatar.propTypes = {
  sizeType: PropTypes.string,
  src: PropTypes.string,
  styles: PropTypes.object,
  onClick: PropTypes.func,
};

Avatar.defaultProps = {
  sizeType: AVATAR_SIZE_TYPE.MEDIUM,
  src: defaultImg,
  styles: null,
  onClick: () => {},
};

export default Avatar;
