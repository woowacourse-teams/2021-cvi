import PropTypes from 'prop-types';
import { Container, Video } from './Loading.styles';
import { LoadingIcon } from '../../../assets/icons';

const Loading = ({ isLoading, backgroundColor }) => (
  <Container isLoading={isLoading} backgroundColor={backgroundColor}>
    <Video autoPlay loop>
      <source src={LoadingIcon} type="video/mp4" />
    </Video>
  </Container>
);

Loading.propTypes = {
  isLoading: PropTypes.bool.isRequired,
  width: PropTypes.string,
  height: PropTypes.string,
  backgroundColor: PropTypes.string,
};

Loading.defaultProps = {
  width: '6rem',
  height: '6rem',
  backgroundColor: 'none',
};

export default Loading;
