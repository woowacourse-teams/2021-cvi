import { useState } from 'react';
import PropTypes from 'prop-types';
import { Avatar, Button } from '../../@common';
import { AVATAR_SIZE_TYPE } from '../../@common/Avatar/Avatar.styles';
import {
  Container,
  User,
  TextArea,
  BottomContainer,
  CommentMaxCount,
  NeedLogin,
  LoginLink,
} from './CommentForm.styles';
import {
  ALERT_MESSAGE,
  COMMENT_LIMIT,
  PATH,
  PLACEHOLDER,
  RESPONSE_STATE,
  SNACKBAR_MESSAGE,
} from '../../../constants';
import { useSnackbar } from '../../../hooks';
import customRequest from '../../../service/customRequest';
import { fetchPostComment } from '../../../service/fetch';

// TODO: 댓글 최적화
const CommentForm = ({
  accessToken,
  reviewId,
  nickname,
  socialProfileUrl,
  setCommentCount,
  setCommentList,
}) => {
  const [content, setContent] = useState('');

  const { openSnackbar } = useSnackbar();

  const createComment = async () => {
    if (!content.length) {
      alert(ALERT_MESSAGE.FAIL_TO_FULFILL_MIN_LENGTH);

      return;
    }

    const data = { content };
    const response = await customRequest(() => fetchPostComment(accessToken, reviewId, data));

    if (response.state === RESPONSE_STATE.FAILURE) {
      alert(ALERT_MESSAGE.FAIL_TO_CREATE_COMMENT);

      return;
    }

    openSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_CREATE_COMMENT);
    setCommentCount((prevstate) => prevstate + 1);
    setCommentList((prevState) => [response.data, ...prevState]);
    setContent('');
  };

  // TODO: Form으로 변경
  return (
    <Container>
      {accessToken ? (
        <>
          <User>
            <Avatar src={socialProfileUrl} sizeType={AVATAR_SIZE_TYPE.SMALL} />
            <div>{nickname}</div>
          </User>
          <TextArea
            value={content}
            placeholder={PLACEHOLDER.COMMENT_FORM}
            minLength={COMMENT_LIMIT.MIN_LENGTH}
            maxLength={COMMENT_LIMIT.MAX_LENGTH}
            readOnly={!accessToken}
            isLogin={accessToken}
            onChange={(event) => setContent(event.target.value)}
          />
          <BottomContainer>
            <div>
              {content.length} <CommentMaxCount>/ {COMMENT_LIMIT.MAX_LENGTH}</CommentMaxCount>
            </div>
            <Button onClick={createComment}>댓글 작성</Button>
          </BottomContainer>
        </>
      ) : (
        <NeedLogin>
          댓글을 작성하려면
          <LoginLink exact="true" to={PATH.LOGIN}>
            로그인
          </LoginLink>
          해주세요
        </NeedLogin>
      )}
    </Container>
  );
};

CommentForm.propTypes = {
  accessToken: PropTypes.string.isRequired,
  nickname: PropTypes.string.isRequired,
  reviewId: PropTypes.string.isRequired,
  socialProfileUrl: PropTypes.string.isRequired,
  setCommentList: PropTypes.func.isRequired,
  setCommentCount: PropTypes.func.isRequired,
};

CommentForm.defaultProps = {
  nickname: '',
  reviewId: '',
  socialProfileUrl: '',
};

export default CommentForm;
