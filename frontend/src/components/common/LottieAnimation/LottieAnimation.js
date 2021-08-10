import PropTypes from 'prop-types';
import Lottie from 'react-lottie';
import { Container, Designer, Description } from './LottieAnimation.styles';

const LottieAnimation = ({ data, width, designer, description, descriptionStyles }) => {
  const defaultOptions = {
    height: '100%',
    loop: true,
    autoplay: true,
    animationData: data,
    rendererSettings: {
      preserveAspectRatio: 'xMidYMid slice',
    },
  };

  return (
    <Container>
      <Lottie options={defaultOptions} width={width} />
      <Designer>Animation by {designer}</Designer>
      <Description styles={descriptionStyles}>{description}</Description>
    </Container>
  );
};

LottieAnimation.propTypes = {
  data: PropTypes.object.isRequired,
  width: PropTypes.string,
  designer: PropTypes.string.isRequired,
  description: PropTypes.string,
  descriptionStyles: PropTypes.object,
};

LottieAnimation.defaultProps = {
  width: '100%',
  description: '',
  descriptionStyles: {},
};

export default LottieAnimation;
