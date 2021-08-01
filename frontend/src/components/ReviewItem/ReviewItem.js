import React from 'react';
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
} from './ReviewItem.styles';
import { LABEL_SIZE_TYPE } from '../common/Label/Label.styles';
import { VACCINATION_COLOR, VACCINATION, FONT_COLOR, TO_DATE_TYPE } from '../../constants';
import { toDate } from '../../utils';
import { Label } from '../common';
import { EyeIcon, CommentIcon } from '../../assets/icons';
import { useLike } from '../../hooks';

const ReviewItem = ({ review, accessToken, getReviewList, onClick }) => {
  const {
    id,
    writer,
    content,
    viewCount,
    vaccinationType,
    createdAt,
    hasLiked,
    likeCount,
    comments,
  } = review;
  const labelFontColor = vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  const { onClickLike, ButtonLike } = useLike(accessToken, hasLiked, id, getReviewList);

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
            <ButtonLike hasLiked={hasLiked} likeCount={likeCount} onClickLike={onClickLike} />
          </IconContainer>
          <IconContainer>
            <CommentIcon width="16" height="16" stroke={FONT_COLOR.LIGHT_GRAY} />
            <ViewCount>{comments?.length}</ViewCount>
          </IconContainer>
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
    comments: PropTypes.array.isRequired,
  }).isRequired,
  accessToken: PropTypes.string.isRequired,
  getReviewList: PropTypes.func,
  onClick: PropTypes.func,
};

ReviewItem.defaultProps = {
  getReviewList: () => {},
  onClick: () => {},
};
export default ReviewItem;
