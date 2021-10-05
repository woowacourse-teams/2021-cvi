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
import { Button } from '../../@common';
import { FONT_COLOR, PAGING_SIZE, RESPONSE_STATE } from '../../../constants';
import { BUTTON_BACKGROUND_TYPE } from '../../@common/Button/Button.styles';
import customRequest from '../../../service/customRequest';
import { fetchGetCommentList } from '../../../service/fetch';

const Comment = ({ accessToken, user, reviewId, commentCount, setCommentCount }) => {
  const [offset, setOffset] = useState(0);
  const [commentList, setCommentList] = useState([]);

  const isMoreButtonShowing = commentCount > PAGING_SIZE && commentCount > commentList.length;

  const getCommentList = useCallback(async () => {
    const response = await customRequest(() => fetchGetCommentList(reviewId, offset));

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

    return () => setCommentList([]);
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
          setCommentCount={setCommentCount}
          setCommentList={setCommentList}
        />
      </CommentFormContainer>
      {commentList?.map((comment) => (
        <CommentItem
          key={comment.id}
          accessToken={accessToken}
          userId={user?.id ?? 0}
          reviewId={reviewId}
          comment={comment}
          setCommentList={setCommentList}
          setCommentCount={setCommentCount}
        />
      ))}
      {isMoreButtonShowing && (
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
  setCommentCount: PropTypes.func.isRequired,
};

export default Comment;
