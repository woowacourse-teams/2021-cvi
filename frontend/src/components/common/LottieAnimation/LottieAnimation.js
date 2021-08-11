import PropTypes from 'prop-types';
import Lottie from 'react-lottie';
import { Container, Designer, Description } from './LottieAnimation.styles';

const LottieAnimation = ({
  data,
  width,
  mobileWidth,
  designer,
  description,
  descriptionStyles,
}) => {
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
    <Container width={width} mobileWidth={mobileWidth}>
      <Lottie options={defaultOptions} />
      {designer && <Designer mobileWidth={mobileWidth}>Animation by {designer}</Designer>}
      {description && (
        <Description mobileWidth={mobileWidth} styles={descriptionStyles}>
          {description}
        </Description>
      )}
    </Container>
  );
};

LottieAnimation.propTypes = {
  data: PropTypes.object.isRequired,
  width: PropTypes.string,
  mobileWidth: PropTypes.string,
  designer: PropTypes.string,
  description: PropTypes.string,
  descriptionStyles: PropTypes.object,
};

LottieAnimation.defaultProps = {
  width: '100%',
  mobileWidth: '80%',
  designer: '',
  description: '',
  descriptionStyles: {},
};

export default LottieAnimation;
