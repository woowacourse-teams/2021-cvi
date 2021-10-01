import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { Avatar } from '../../components/@common';
import { AVATAR_SIZE_TYPE } from '../../components/@common/Avatar/Avatar.styles';
import { PATH } from '../../constants';
import {
  Container,
  RightSideBar,
  ProfileContainer,
  User,
  MenuContainer,
  MenuTitle,
  MenuItem,
} from './MyPage.styles';

const MyPage = ({ children }) => {
  const user = useSelector((state) => state.authReducer.user);

  return (
    <Container>
      {children}
      <RightSideBar>
        <ProfileContainer>
          <Avatar sizeType={AVATAR_SIZE_TYPE.LARGE} src={user.socialProfileUrl} />
          <User>
            {user.nickname} · {user.ageRange?.meaning}
          </User>
        </ProfileContainer>
        <MenuContainer>
          <MenuItem>
            <MenuTitle>계정</MenuTitle>
            <div>
              <Link to={PATH.MY_PAGE_ACCOUNT}>내 정보 관리</Link>
            </div>
          </MenuItem>
          <MenuItem>
            <MenuTitle>글 관리</MenuTitle>
            <div>
              <Link to={PATH.MY_PAGE_REVIEW}>내가 쓴 글</Link>
            </div>
            <div>
              <Link to={PATH.MY_PAGE_COMMENT_REVIEW}>댓글 단 글</Link>
            </div>
            <div>
              <Link to={PATH.MY_PAGE_LIKE_REVIEW}>좋아요 누른 글</Link>
            </div>
          </MenuItem>
        </MenuContainer>
      </RightSideBar>
    </Container>
  );
};

MyPage.propTypes = {
  children: PropTypes.node.isRequired,
};

export default MyPage;
