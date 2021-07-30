import PropTypes from 'prop-types';
import { Avatar, Button } from '../common';
import { AVATAR_SIZE_TYPE } from '../common/Avatar/Avatar.styles';
import { Container, User, TextArea, BottomContainer, CommentMaxCount } from './CommentForm.styles';
import {
  ALERT_MESSAGE,
  LENGTH_LIMIT,
  PLACEHOLDER,
  RESPONSE_STATE,
  SNACKBAR_MESSAGE,
} from '../../constants';
import { useState } from 'react';
import { postCommentAsync } from '../../service';
import { useParams } from 'react-router-dom';
import { useSnackbar } from 'notistack';

const CommentForm = ({ accessToken, nickname, socialProfileUrl }) => {
  const [content, setContent] = useState('');
  const { id } = useParams();
  const { enqueueSnackbar } = useSnackbar();

  const createComment = async () => {
    const data = { content };
    const response = await postCommentAsync(accessToken, id, data);

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_CREATE_COMMENT);

      return;
    }

    enqueueSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_CREATE_COMMENT);
    location.reload();
  };

  return (
    <Container>
      <User>
        <Avatar src={socialProfileUrl} sizeType={AVATAR_SIZE_TYPE.SMALL} />
        <div>{nickname}</div>
      </User>
      <TextArea
        value={content}
        placeholder={PLACEHOLDER.COMMENT_FORM}
        maxLength={LENGTH_LIMIT.COMMENT}
        onChange={(event) => setContent(event.target.value)}
      />
      <BottomContainer>
        <div>
          {content.length} <CommentMaxCount>/ {LENGTH_LIMIT.COMMENT}</CommentMaxCount>
        </div>
        <Button onClick={createComment}>댓글 작성</Button>
      </BottomContainer>
    </Container>
  );
};

CommentForm.propTypes = {
  accessToken: PropTypes.string.isRequired,
  nickname: PropTypes.string.isRequired,
  reviewId: PropTypes.string.isRequired,
  socialProfileUrl: PropTypes.string.isRequired,
};

CommentForm.defaultProps = {
  nickname: '',
  reviewId: '',
  socialProfileUrl: '',
};

export default CommentForm;
