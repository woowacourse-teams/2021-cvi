import {
  Container,
  Content,
  Writer,
  ViewCount,
  Date,
  TopContainer,
  BottomContainer,
  ShotVerified,
} from './ReviewItem.styles';
import Label from '../Label/Label';
import PropTypes from 'prop-types';
import { LABEL_SIZE_TYPE } from '../Label/Label.styles';
import { VACCINATION_COLOR, VACCINATION, FONT_COLOR } from '../../constants';

const ReviewItem = ({ review }) => {
  const { writer, content, viewCount, vaccinationType, createdAt } = review;
  const labelFontColor = vaccinationType === '아스트라제네카' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  return (
    <Container>
      <TopContainer>
        <Label
          backgroundColor={VACCINATION_COLOR[VACCINATION[vaccinationType]]}
          sizeType={LABEL_SIZE_TYPE.MEDIUM}
          fontColor={labelFontColor}
        >
          {vaccinationType}
        </Label>
        <ShotVerified>{writer?.shotVerified && '접종 확인'}</ShotVerified>
      </TopContainer>
      <Content>{content}</Content>
      <Writer>
        {writer?.nickname} · {writer?.age}대
      </Writer>
      <BottomContainer>
        <ViewCount>{viewCount}</ViewCount>
        <Date>{createdAt}</Date>
      </BottomContainer>
    </Container>
  );
};

ReviewItem.propTypes = {
  review: PropTypes.shape({
    writer: PropTypes.object.isRequired,
    content: PropTypes.string.isRequired,
    vaccinationType: PropTypes.string.isRequired,
    createdAt: PropTypes.string.isRequired,
    viewCount: PropTypes.number.isRequired,
  }).isRequired,
};
export default ReviewItem;
