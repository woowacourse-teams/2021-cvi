import PropTypes from 'prop-types';
import { Avatar } from '../common';
import {
  Container,
  Info,
  InfoContainer,
  Writer,
  CreatedAt,
  ShotVerified,
  Content,
} from './CommentItem.styles';
import { toDate } from '../../utils';
import { TO_DATE_TYPE } from '../../constants';

const CommentItem = ({ comment }) => {
  const { writer, content, createdAt } = comment;

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
      </InfoContainer>
      <Content>{content}</Content>
    </Container>
  );
};

CommentItem.propTypes = {
  comment: PropTypes.object.isRequired,
};

CommentItem.defaultProps = {};

export default CommentItem;
