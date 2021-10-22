import { useState } from 'react';
import PropTypes from 'prop-types';
import { Avatar, Button } from '../../@common';
import {
  Container,
  Info,
  InfoContainer,
  UpdateButtonContainer,
  Writer,
  CreatedAt,
  Content,
  TextArea,
  buttonStyles,
} from './CommentItem.styles';
import { toDate } from '../../../utils';
import {
  ALERT_MESSAGE,
  COMMENT_LIMIT,
  CONFIRM_MESSAGE,
  FONT_COLOR,
  RESPONSE_STATE,
  SNACKBAR_MESSAGE,
  THEME_COLOR,
  TO_DATE_TYPE,
} from '../../../constants';
import { BUTTON_BACKGROUND_TYPE } from '../../@common/Button/Button.styles';
import { useSnackbar } from '../../../hooks';
import { fetchDeleteComment, fetchPutComment } from '../../../service/fetch';
import customRequest from '../../../service/customRequest';

const CommentItem = ({
  accessToken,
  userId,
  reviewId,
  comment,
  setCommentList,
  setCommentCount,
}) => {
  const { id: commentId, writer, content, createdAt } = comment;

  const [isEditable, setIsEditable] = useState(false);
  const [editedContent, setEditedContent] = useState(content);

  const { openSnackbar } = useSnackbar();

  const deleteComment = async () => {
    if (!window.confirm(CONFIRM_MESSAGE.DELETE_COMMENT)) return;

    const response = await customRequest(() =>
      fetchDeleteComment(accessToken, reviewId, commentId),
    );

    if (!response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_DELETE_COMMENT);

      return;
    }

    openSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_DELETE_COMMENT);
    setCommentList((prevState) => prevState.filter((comment) => comment.id !== commentId));
    setCommentCount((prevState) => prevState - 1);
  };

  const editComment = async () => {
    if (!editedContent.length) {
      alert(ALERT_MESSAGE.FAIL_TO_FULFILL_MIN_LENGTH);

      return;
    }

    const data = { content: editedContent };
    const response = await customRequest(() =>
      fetchPutComment(accessToken, reviewId, commentId, data),
    );

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_EDIT_COMMENT);

      return;
    }

    setIsEditable(false);
    setCommentList((prevState) => {
      const targetIndex = prevState.findIndex((comment) => comment.id === commentId);

      prevState.splice(targetIndex, 1, {
        ...prevState[targetIndex],
        content: editedContent,
      });

      return prevState;
    });
    openSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_EDIT_COMMENT);
  };

  return (
    <Container>
      <InfoContainer>
        <Avatar src={writer?.socialProfileUrl} />
        <Info>
          <Writer>
            {writer.nickname} · {writer.ageRange.meaning}
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
              {isEditable ? '취소' : '수정'}
            </Button>

            {isEditable ? (
              <Button
                backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
                color={THEME_COLOR.PRIMARY}
                styles={buttonStyles}
                onClick={editComment}
              >
                완료
              </Button>
            ) : (
              <Button
                backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
                color={FONT_COLOR.GRAY}
                styles={buttonStyles}
                onClick={deleteComment}
              >
                삭제
              </Button>
            )}
          </UpdateButtonContainer>
        )}
      </InfoContainer>
      {isEditable ? (
        <>
          <TextArea
            value={editedContent}
            minLength={COMMENT_LIMIT.MIN_LENGTH}
            maxLength={COMMENT_LIMIT.MAX_LENGTH}
            onChange={(event) => setEditedContent(event.target.value)}
          />
        </>
      ) : (
        <Content>{content}</Content>
      )}
    </Container>
  );
};

CommentItem.propTypes = {
  accessToken: PropTypes.string.isRequired,
  userId: PropTypes.number.isRequired,
  reviewId: PropTypes.string.isRequired,
  comment: PropTypes.object.isRequired,
  setCommentList: PropTypes.func.isRequired,
  setCommentCount: PropTypes.func.isRequired,
};

export default CommentItem;
