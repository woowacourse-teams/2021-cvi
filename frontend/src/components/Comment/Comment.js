import PropTypes from 'prop-types';
import CommentForm from '../CommentForm/CommentForm';
import CommentItem from '../CommentItem/CommentItem';
import { Container, CommentCount, CommentFormContainer } from './Comment.styles';

const Comment = ({ accessToken, user, comments, reviewId, getReview }) => (
  <Container>
    <CommentCount>댓글 {comments?.length}</CommentCount>
    <CommentFormContainer>
      <CommentForm
        accessToken={accessToken}
        reviewId={reviewId}
        nickname={user.nickname}
        socialProfileUrl={user.socialProfileUrl}
        getReview={getReview}
      />
    </CommentFormContainer>
    {comments?.map((comment) => (
      <CommentItem
        key={comment.id}
        accessToken={accessToken}
        userId={user.id}
        reviewId={reviewId}
        comment={comment}
        getReview={getReview}
      />
    ))}
  </Container>
);

Comment.propTypes = {
  accessToken: PropTypes.string.isRequired,
  comments: PropTypes.array.isRequired,
  getReview: PropTypes.func.isRequired,
  reviewId: PropTypes.number.isRequired,
  user: PropTypes.shape({
    id: PropTypes.number.isRequired,
    nickname: PropTypes.string.isRequired,
    socialProfileUrl: PropTypes.string.isRequired,
  }).isRequired,
};

export default Comment;
