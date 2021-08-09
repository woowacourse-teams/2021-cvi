import PropTypes from 'prop-types';
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
  CreatedAt,
  buttonStyles,
} from './ReviewItem.styles';
import { LABEL_SIZE_TYPE } from '../common/Label/Label.styles';
import {
  VACCINATION_COLOR,
  VACCINATION,
  FONT_COLOR,
  TO_DATE_TYPE,
  PATH,
  CONFIRM_MESSAGE,
} from '../../constants';
import { toDate } from '../../utils';
import { Button, Label } from '../common';
import { EyeIcon, CommentIcon } from '../../assets/icons';
import { useLike } from '../../hooks';
import { BUTTON_BACKGROUND_TYPE } from '../common/Button/Button.styles';

const ReviewItem = ({ review, accessToken, innerRef, path, onClick }) => {
  const {
    id,
    writer,
    content,
    viewCount,
    vaccinationType,
    createdAt,
    hasLiked,
    likeCount,
    commentCount,
  } = review;

  const labelFontColor = vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;
  const { onClickLike, ButtonLike, updatedHasLiked, updatedLikeCount, deleteLike } = useLike(
    accessToken,
    hasLiked,
    likeCount,
    id,
  );

  const cancelLike = (event) => {
    event.stopPropagation();

    if (!window.confirm(CONFIRM_MESSAGE.CANCEL_LIKE)) return;

    deleteLike();
    location.reload();
  };

  return (
    <Container ref={innerRef} onClick={onClick}>
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
            <ButtonLike
              hasLiked={updatedHasLiked}
              likeCount={updatedLikeCount}
              onClickLike={onClickLike}
            />
          </IconContainer>
          <IconContainer>
            <CommentIcon width="16" height="16" stroke={FONT_COLOR.LIGHT_GRAY} />
            <ViewCount>{commentCount}</ViewCount>
          </IconContainer>
          {path === PATH.MY_PAGE_LIKE_REVIEW && (
            <IconContainer>
              <Button
                backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
                styles={buttonStyles}
                onClick={cancelLike}
              >
                좋아요 취소
              </Button>
            </IconContainer>
          )}
        </InfoContainer>
        <CreatedAt>{toDate(TO_DATE_TYPE.TIME, createdAt)}</CreatedAt>
      </BottomContainer>
    </Container>
  );
};

ReviewItem.propTypes = {
  review: PropTypes.shape({
    id: PropTypes.number.isRequired,
    writer: PropTypes.object.isRequired,
    content: PropTypes.string.isRequired,
    vaccinationType: PropTypes.string.isRequired,
    createdAt: PropTypes.string.isRequired,
    viewCount: PropTypes.number.isRequired,
    hasLiked: PropTypes.bool.isRequired,
    likeCount: PropTypes.number.isRequired,
    commentCount: PropTypes.number,
  }).isRequired,
  accessToken: PropTypes.string.isRequired,
  innerRef: PropTypes.func,
  path: PropTypes.string,
  onClick: PropTypes.func,
};

ReviewItem.defaultProps = {
  innerRef: null,
  path: '',
  onClick: () => {},
};
export default ReviewItem;
