import PropTypes from 'prop-types';
import React from 'react';
import { FONT_COLOR, TO_DATE_TYPE, VACCINATION, VACCINATION_COLOR } from '../../constants';
import { toDate } from '../../utils';
import { Label } from '../common';
import {
  Container,
  Content,
  CreatedAt,
  CommentContent,
  ReviewContainer,
  TopContainer,
  ShotVerified,
  ReviewContent,
  Writer,
} from './MyCommentsItem.styles';

const MyCommentsItem = ({ myCommentReview, myComments, innerRef, onClick }) => {
  const labelFontColor =
    myCommentReview.vaccinationType === 'ASTRAZENECA' ? FONT_COLOR.GRAY : FONT_COLOR.WHITE;

  return (
    <Container ref={innerRef} onClick={onClick}>
      <Content>
        {myComments.map((myComment) => {
          return (
            <div key={myComment.id}>
              <CreatedAt>{toDate(TO_DATE_TYPE.TIME, myComment.createdAt)}</CreatedAt>
              <CommentContent>{myComment.content}</CommentContent>
            </div>
          );
        })}
        <ReviewContainer>
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
        </ReviewContainer>
      </Content>
    </Container>
  );
};

MyCommentsItem.propTypes = {
  myCommentReview: PropTypes.shape({
    content: PropTypes.string.isRequired,
    vaccinationType: PropTypes.string.isRequired,
    writer: PropTypes.shape({
      ageRange: PropTypes.shape({
        meaning: PropTypes.string.isRequired,
      }).isRequired,
      nickname: PropTypes.string.isRequired,
      shotVerified: PropTypes.bool.isRequired,
    }).isRequired,
  }).isRequired,
  myComments: PropTypes.array.isRequired,
  innerRef: PropTypes.number,
  onClick: PropTypes.func.isRequired,
};

MyCommentsItem.defaultProps = {
  innerRef: '',
};

export default MyCommentsItem;
