import PropTypes from 'prop-types';
import { useCallback, useEffect, useState } from 'react';
import CommentForm from '../CommentForm/CommentForm';
import CommentItem from '../CommentItem/CommentItem';
import {
  Container,
  CommentCount,
  CommentFormContainer,
  ButtonContainer,
  buttonStyles,
} from './Comment.styles';
import { Button } from '../common';
import { getCommentListAsync } from '../../service';
import { FONT_COLOR, PAGING_SIZE, RESPONSE_STATE } from '../../constants';
import { BUTTON_BACKGROUND_TYPE } from '../common/Button/Button.styles';

const Comment = ({ accessToken, user, reviewId, commentCount, getReview }) => {
  const [offset, setOffset] = useState(0);
  const [commentList, setCommentList] = useState([]);

  const getCommentList = useCallback(async () => {
    const response = await getCommentListAsync(reviewId, offset);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert('failure - getCommentListAsync');

      return;
    }

    setCommentList((prevState) => [...prevState, ...response.data]);
  }, [offset]);

  const getMoreCommentList = () => {
    setOffset((prevState) => prevState + PAGING_SIZE);
  };

  useEffect(() => {
    getCommentList();
  }, [getCommentList]);

  return (
    <Container>
      <CommentCount>댓글 {commentCount}</CommentCount>
      <CommentFormContainer>
        <CommentForm
          accessToken={accessToken}
          reviewId={reviewId}
          nickname={user.nickname}
          socialProfileUrl={user?.socialProfileUrl}
          getReview={getReview}
        />
      </CommentFormContainer>
      {commentList?.map((comment) => (
        <CommentItem
          key={comment.id}
          accessToken={accessToken}
          userId={user?.id}
          reviewId={reviewId}
          comment={comment}
          getReview={getReview}
        />
      ))}
      {commentCount > commentList.length && (
        <ButtonContainer>
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
            color={FONT_COLOR.GRAY}
            styles={buttonStyles}
            onClick={getMoreCommentList}
          >
            더보기
          </Button>
        </ButtonContainer>
      )}
    </Container>
  );
};

Comment.propTypes = {
  accessToken: PropTypes.string.isRequired,
  commentCount: PropTypes.number.isRequired,
  getReview: PropTypes.func.isRequired,
  reviewId: PropTypes.string.isRequired,
  user: PropTypes.shape({
    id: PropTypes.number,
    nickname: PropTypes.string,
    socialProfileUrl: PropTypes.string,
  }).isRequired,
};

export default Comment;
