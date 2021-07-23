import PropTypes from 'prop-types';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { Avatar } from '../../components/common';
import { AVATAR_SIZE_TYPE } from '../../components/common/Avatar/Avatar.styles';
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
          <div>{user.shotVerified ? '접종 완료' : '접종 미완료'}</div>
        </ProfileContainer>
        <MenuContainer>
          <MenuItem>
            <MenuTitle>계정</MenuTitle>
            <div>
              <Link to={PATH.MY_PAGE_ACCOUNT}>내 정보 관리</Link>
            </div>
            <div>
              <Link to={PATH.MY_PAGE_SHOT_VERIFICATION}>접종 인증</Link>
            </div>
          </MenuItem>
          <MenuItem>
            <MenuTitle>글 관리</MenuTitle>
            <div>내가 쓴 글</div>
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
