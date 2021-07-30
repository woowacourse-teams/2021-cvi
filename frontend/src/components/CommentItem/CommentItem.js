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
  TextArea,
  buttonStyles,
} from './CommentItem.styles';
import { toDate } from '../../utils';
import {
  ALERT_MESSAGE,
  CONFIRM_MESSAGE,
  FONT_COLOR,
  LENGTH_LIMIT,
  RESPONSE_STATE,
  SNACKBAR_MESSAGE,
  TO_DATE_TYPE,
} from '../../constants';
import { BUTTON_BACKGROUND_TYPE } from '../common/Button/Button.styles';
import { deleteCommentAsync, putCommentAsync } from '../../service';
import { useState } from 'react';
import { useSnackbar } from 'notistack';

const CommentItem = ({ accessToken, userId, reviewId, comment, getReview }) => {
  // TODO: 규칙 어긋나는데 확인하기
  const { id: commentId, writer, content, createdAt } = comment;

  const [isEditable, setIsEditable] = useState(false);
  const [editedContent, setEditedContent] = useState(content);

  const { enqueueSnackbar } = useSnackbar();

  const deleteComment = async () => {
    if (!window.confirm(CONFIRM_MESSAGE.DELETE_COMMENT)) return;

    const response = await deleteCommentAsync(accessToken, reviewId, commentId);

    if (!response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_COMMENT);

      return;
    }

    getReview();
  };

  const editComment = async () => {
    const data = { content: editedContent };
    const response = await putCommentAsync(accessToken, reviewId, commentId, data);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_EDIT_COMMENT);

      return;
    }

    setIsEditable(false);
    enqueueSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_EDIT_COMMENT);
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
              onClick={() => setIsEditable(!isEditable)}
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
      {isEditable ? (
        <>
          <TextArea
            value={editedContent}
            maxLength={LENGTH_LIMIT.COMMENT}
            onChange={(event) => setEditedContent(event.target.value)}
          />
          <Button onClick={editComment}>수정 완료</Button>
        </>
      ) : (
        <Content>{editedContent}</Content>
      )}
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
