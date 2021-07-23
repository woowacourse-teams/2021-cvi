import PropTypes from 'prop-types';
import {
  Container,
  TopContainer,
  Content,
  BottomContainer,
  ShotVerified,
} from './PreviewItem.styles';
import { VACCINATION_COLOR, VACCINATION, FONT_COLOR, TO_DATE_TYPE } from '../../constants';
import { toDate } from '../../utils';
import { Label } from '../common';
import { LABEL_SIZE_TYPE } from '../common/Label/Label.styles';

const PreviewItem = ({ review, onClick }) => {
  const { writer, content, vaccinationType, createdAt } = review;
  const labelFontColor = vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  return (
    <Container onClick={onClick}>
      <TopContainer>
        <Label
          sizeType={LABEL_SIZE_TYPE.SMALL}
          backgroundColor={VACCINATION_COLOR[vaccinationType]}
          fontColor={labelFontColor}
        >
          {VACCINATION[vaccinationType]}
        </Label>
        <ShotVerified>{writer?.shotVerified && '접종 확인'}</ShotVerified>
      </TopContainer>
      <Content>{content}</Content>
      <BottomContainer>{toDate(TO_DATE_TYPE.DATE, createdAt)}</BottomContainer>
    </Container>
  );
};

PreviewItem.propTypes = {
  review: PropTypes.shape({
    writer: PropTypes.object.isRequired,
    content: PropTypes.string.isRequired,
    vaccinationType: PropTypes.string.isRequired,
    createdAt: PropTypes.string.isRequired,
  }).isRequired,
  onClick: PropTypes.func,
};

PreviewItem.defaultProps = {
  onClick: () => {},
};

export default PreviewItem;
