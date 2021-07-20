import {
  Container,
  ViewCountContainer,
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
import { VACCINATION_COLOR, VACCINATION, FONT_COLOR, TO_DATE_TYPE } from '../../constants';
import { toDate } from '../../utils';
import EyeIcon from '../../assets/icons/eye.svg';

const ReviewItem = ({ review, onClick }) => {
  const { writer, content, viewCount, vaccinationType, createdAt } = review;
  const labelFontColor = vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  return (
    <Container onClick={onClick}>
      <TopContainer>
        <Label
          backgroundColor={VACCINATION_COLOR[vaccinationType]}
          sizeType={LABEL_SIZE_TYPE.MEDIUM}
          fontColor={labelFontColor}
        >
          {VACCINATION[vaccinationType]}
        </Label>
        <ShotVerified>{writer?.shotVerified && '접종 확인'}</ShotVerified>
      </TopContainer>
      <Content>{content}</Content>
      <Writer>
        {writer?.nickname} · {writer?.ageRange.meaning}
      </Writer>
      <BottomContainer>
        <ViewCountContainer>
          <EyeIcon width="18" height="18" stroke={FONT_COLOR.LIGHT_GRAY} />
          <ViewCount>{viewCount}</ViewCount>
        </ViewCountContainer>
        <Date>{toDate(TO_DATE_TYPE.TIME, createdAt)}</Date>
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
  onClick: PropTypes.func,
};

ReviewItem.defaultProps = {
  onClick: () => {},
};
export default ReviewItem;
