import PropTypes from 'prop-types';
import { Container, Img } from './Loading.styles';
import { LoadingIcon } from '../../../assets/icons';

const Loading = ({ isLoading, width, height, backgroundColor }) => (
  <Container isLoading={isLoading} backgroundColor={backgroundColor}>
    <Img src={LoadingIcon} width={width} height={height} />
  </Container>
);

Loading.propTypes = {
  isLoading: PropTypes.bool.isRequired,
  width: PropTypes.string,
  height: PropTypes.string,
  backgroundColor: PropTypes.string,
};

Loading.defaultProps = {
  width: '12rem',
  height: '12rem',
  backgroundColor: 'none',
};

export default Loading;
