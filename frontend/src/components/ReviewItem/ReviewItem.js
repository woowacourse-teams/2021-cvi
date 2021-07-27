import {
  Container,
  Content,
  Writer,
  ViewCount,
  TopContainer,
  BottomContainer,
  ShotVerified,
  IconContainer,
  InfoContainer,
} from './ReviewItem.styles';
import PropTypes from 'prop-types';
import { LABEL_SIZE_TYPE } from '../common/Label/Label.styles';
import { VACCINATION_COLOR, VACCINATION, FONT_COLOR, TO_DATE_TYPE } from '../../constants';
import { toDate } from '../../utils';
import { Label } from '../common';
import { EyeIcon, CommentIcon, LikeIcon } from '../../assets/icons';

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
        <InfoContainer>
          <IconContainer>
            <EyeIcon width="18" height="18" stroke={FONT_COLOR.LIGHT_GRAY} />
            <ViewCount>{viewCount}</ViewCount>
          </IconContainer>
          <IconContainer>
            <LikeIcon
              width="18"
              height="18"
              stroke={FONT_COLOR.LIGHT_GRAY}
              fill={FONT_COLOR.LIGHT_GRAY}
            />
            <ViewCount>{viewCount}</ViewCount>
          </IconContainer>
          <IconContainer>
            <CommentIcon width="16" height="16" stroke={FONT_COLOR.LIGHT_GRAY} />
            <ViewCount>{viewCount}</ViewCount>
          </IconContainer>
        </InfoContainer>
        <div>{toDate(TO_DATE_TYPE.TIME, createdAt)}</div>
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
