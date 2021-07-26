import PropTypes from 'prop-types';
import { Avatar, Button } from '../common';
import { AVATAR_SIZE_TYPE } from '../common/Avatar/Avatar.styles';
import { Container, User, TextArea, BottomContainer, CommentMaxCount } from './CommentForm.styles';
import { PLACEHOLDER } from '../../constants';

const CommentForm = ({ nickname, socialProfileUrl }) => {
  return (
    <Container>
      <User>
        <Avatar src={socialProfileUrl} sizeType={AVATAR_SIZE_TYPE.SMALL} />
        <div>{nickname}</div>
      </User>
      <TextArea placeholder={PLACEHOLDER.COMMENT_FORM} />
      <BottomContainer>
        <div>
          123 <CommentMaxCount>/ 300</CommentMaxCount>
        </div>
        <Button>댓글 작성</Button>
      </BottomContainer>
    </Container>
  );
};

CommentForm.propTypes = {
  nickname: PropTypes.string.isRequired,
  socialProfileUrl: PropTypes.string.isRequired,
};

export default CommentForm;
