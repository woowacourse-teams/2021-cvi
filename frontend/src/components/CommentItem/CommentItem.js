import PropTypes from 'prop-types';
import { Avatar, Button } from '../common';
import {
  Container,
  Info,
  InfoContainer,
  UpdateButtonContainer,
  Writer,
  CreatedAt,
  ShotVerified,
  Content,
  buttonStyles,
} from './CommentItem.styles';
import { toDate } from '../../utils';
import {
  ALERT_MESSAGE,
  CONFIRM_MESSAGE,
  FONT_COLOR,
  RESPONSE_STATE,
  TO_DATE_TYPE,
} from '../../constants';
import { BUTTON_BACKGROUND_TYPE } from '../common/Button/Button.styles';
import { deleteCommentAsync } from '../../service';

const CommentItem = ({ accessToken, userId, reviewId, comment, getReview }) => {
  const { id: commentId, writer, content, createdAt } = comment;

  const deleteComment = async () => {
    if (!window.confirm(CONFIRM_MESSAGE.DELETE_COMMENT)) return;

    const response = await deleteCommentAsync(accessToken, reviewId, commentId);

    if (!response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_COMMENT);

      return;
    }

    getReview();
  };

  return (
    <Container>
      <InfoContainer>
        <Avatar src={writer.socialProfileUrl} />
        <Info>
          <Writer>
            {writer.nickname} · {writer.ageRange.meaning}
            {writer.shotVerified && <ShotVerified>접종 확인</ShotVerified>}
          </Writer>
          <CreatedAt>{toDate(TO_DATE_TYPE.TIME, createdAt)}</CreatedAt>
        </Info>
        {userId === writer.id && (
          <UpdateButtonContainer>
            <Button
              backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
              color={FONT_COLOR.GRAY}
              styles={buttonStyles}
            >
              수정
            </Button>
            <Button
              backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
              color={FONT_COLOR.GRAY}
              styles={buttonStyles}
              onClick={deleteComment}
            >
              삭제
            </Button>
          </UpdateButtonContainer>
        )}
      </InfoContainer>
      <Content>{content}</Content>
    </Container>
  );
};

CommentItem.propTypes = {
  accessToken: PropTypes.string.isRequired,
  userId: PropTypes.number.isRequired,
  reviewId: PropTypes.string.isRequired,
  comment: PropTypes.object.isRequired,
  getReview: PropTypes.func.isRequired,
};

export default CommentItem;
