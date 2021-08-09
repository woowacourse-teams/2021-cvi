import PropTypes from 'prop-types';
import {
  Container,
  TopContainer,
  Content,
  BottomContainer,
  ShotVerified,
  IconContainer,
  IconItem,
  ViewCount,
  CreatedAt,
} from './PreviewItem.styles';
import { VACCINATION_COLOR, VACCINATION, FONT_COLOR, TO_DATE_TYPE } from '../../constants';
import { toDate } from '../../utils';
import { Label } from '../common';
import { LABEL_SIZE_TYPE } from '../common/Label/Label.styles';
import { CommentIcon, EyeIcon } from '../../assets/icons';
import { useLike } from '../../hooks';

const PreviewItem = ({ review, onClick, accessToken }) => {
  const {
    id,
    writer,
    content,
    vaccinationType,
    createdAt,
    commentCount,
    hasLiked,
    likeCount,
    viewCount,
  } = review;
  const labelFontColor = vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  const { onClickLike, ButtonLike, updatedHasLiked, updatedLikeCount } = useLike(
    accessToken,
    hasLiked,
    likeCount,
    id,
  );

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
      <BottomContainer>
        <IconContainer>
          <IconItem>
            <EyeIcon width="18" height="18" stroke={FONT_COLOR.LIGHT_GRAY} />
            <ViewCount>{viewCount}</ViewCount>
          </IconItem>
          <IconItem>
            <ButtonLike
              hasLiked={updatedHasLiked}
              likeCount={updatedLikeCount}
              onClickLike={onClickLike}
            />
          </IconItem>
          <IconItem>
            <CommentIcon width="16" height="16" stroke={FONT_COLOR.LIGHT_GRAY} />
            <ViewCount>{commentCount}</ViewCount>
          </IconItem>
        </IconContainer>
        <CreatedAt>{toDate(TO_DATE_TYPE.TIME, createdAt)}</CreatedAt>
      </BottomContainer>
    </Container>
  );
};

PreviewItem.propTypes = {
  review: PropTypes.shape({
    id: PropTypes.number.isRequired,
    writer: PropTypes.object.isRequired,
    content: PropTypes.string.isRequired,
    vaccinationType: PropTypes.string.isRequired,
    createdAt: PropTypes.string.isRequired,
    commentCount: PropTypes.number.isRequired,
    likeCount: PropTypes.number.isRequired,
    viewCount: PropTypes.number.isRequired,
    hasLiked: PropTypes.bool.isRequired,
  }).isRequired,
  accessToken: PropTypes.string,
  onClick: PropTypes.func,
};

PreviewItem.defaultProps = {
  accessToken: '',
  onClick: () => {},
};

export default PreviewItem;
