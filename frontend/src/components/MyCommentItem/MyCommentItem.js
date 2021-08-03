import React from 'react';
import { FONT_COLOR, TO_DATE_TYPE, VACCINATION, VACCINATION_COLOR } from '../../constants';
import { toDate } from '../../utils';
import { Frame, Label } from '../common';
import {
  Container,
  ReviewContainer,
  TopContainer,
  CreatedAt,
  CommentContent,
  ShotVerified,
  ReviewContent,
  Writer,
  frameStyle,
} from './MyCommentItem.styles';

const MyCommentItem = ({ myCommentReview, myComment, onClick }) => {
  const labelFontColor =
    myCommentReview.vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  return (
    <Container onClick={onClick}>
      <CreatedAt>{toDate(TO_DATE_TYPE.TIME, myComment.createdAt)}</CreatedAt>
      <CommentContent>{myComment.content}</CommentContent>
      <ReviewContainer>
        <Frame showShadow={true} styles={frameStyle}>
          <TopContainer>
            <Label
              fontColor={labelFontColor}
              backgroundColor={VACCINATION_COLOR[myCommentReview.vaccinationType]}
            >
              {VACCINATION[myCommentReview.vaccinationType]}
            </Label>
            <ShotVerified>{myCommentReview.writer.shotVerified && '접종 확인'}</ShotVerified>
          </TopContainer>
          <ReviewContent>{myCommentReview.content}</ReviewContent>
          <Writer>
            {myCommentReview.writer.nickname} · {myCommentReview.writer.ageRange.meaning}
          </Writer>
        </Frame>
      </ReviewContainer>
    </Container>
  );
};

export default MyCommentItem;
