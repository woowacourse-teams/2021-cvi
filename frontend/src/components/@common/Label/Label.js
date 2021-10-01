import PropTypes from 'prop-types';
import { PALETTE, VACCINATION_COLOR } from '../../../constants';
import { Container, LABEL_SIZE_TYPE } from './Label.styles';

const Label = ({ children, sizeType, backgroundColor, fontColor }) => (
  <Container backgroundColor={backgroundColor} fontColor={fontColor} sizeType={sizeType}>
    {children}
  </Container>
);

Label.propTypes = {
  children: PropTypes.node,
  sizeType: PropTypes.string,
  backgroundColor: PropTypes.string,
  fontColor: PropTypes.string,
};

Label.defaultProps = {
  children: '',
  sizeType: LABEL_SIZE_TYPE.SMALL,
  backgroundColor: VACCINATION_COLOR.PFIZER,
  fontColor: PALETTE.WHITE,
};

export default Label;
