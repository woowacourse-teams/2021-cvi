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
  ReviewContent,
  Writer,
  PreviewImage,
  PreviewImageContainer,
  MoreImageCount,
} from './MyCommentsItem.styles';

const MyCommentsItem = ({ myCommentReview, myComments, innerRef, onClick }) => {
  const { vaccinationType, content, writer, images } = myCommentReview;
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
          <div>
            <Label fontColor={labelFontColor} backgroundColor={VACCINATION_COLOR[vaccinationType]}>
              {VACCINATION[vaccinationType]}
            </Label>
            <ReviewContent>{content}</ReviewContent>
            <Writer>
              {writer.nickname} · {writer.ageRange.meaning}
            </Writer>
          </div>
          {!!images?.length && (
            <PreviewImageContainer>
              <PreviewImage src={images[0]} />
              {images.length > 1 && <MoreImageCount>+ {images.length - 1}</MoreImageCount>}
            </PreviewImageContainer>
          )}
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
    }).isRequired,
    images: PropTypes.array.isRequired,
  }).isRequired,
  myComments: PropTypes.array.isRequired,
  innerRef: PropTypes.number,
  onClick: PropTypes.func.isRequired,
};

MyCommentsItem.defaultProps = {
  innerRef: '',
};

export default MyCommentsItem;
