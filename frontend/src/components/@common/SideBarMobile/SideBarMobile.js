import { useSelector } from 'react-redux';
import PropTypes from 'prop-types';
import {
  Dimmer,
  Container,
  MyPageMenuContainer,
  MenuContainer,
  CloseIconContainer,
  NavLinkElement,
  LogoutButton,
  MyPageLink,
  ProfileContainer,
  User,
} from './SideBarMobile.styles';
import { FONT_COLOR, PATH } from '../../../constants';
import {
  HomeIcon,
  LoginIcon,
  LogoutIcon,
  ReviewIcon,
  MyPageIcon,
  CloseIcon,
  StateIcon,
} from '../../../assets/icons';
import Button from '../Button/Button';
import { css } from '@emotion/react';
import { AVATAR_SIZE_TYPE } from '../Avatar/Avatar.styles';
import Avatar from '../Avatar/Avatar';
import { BUTTON_BACKGROUND_TYPE } from '../Button/Button.styles';

const SideBarMobile = ({ isOpenSideBar, setIsOpenSideBar, logout }) => {
  const user = useSelector((state) => state.authReducer?.user);

  const closeSideBar = (event) => {
    if (event.target !== event.currentTarget) return;

    setIsOpenSideBar(false);
  };

  return (
    <Dimmer isOpenSideBar={isOpenSideBar} onClick={closeSideBar}>
      <Container isOpenSideBar={isOpenSideBar}>
        <CloseIconContainer>
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
            styles={css`
              padding: 0;
            `}
            aria-label="close-button"
            onClick={() => setIsOpenSideBar(false)}
          >
            <CloseIcon width="32" height="32" stroke={FONT_COLOR.BLACK} />
          </Button>
        </CloseIconContainer>
        {Object.keys(user).length ? (
          <ProfileContainer>
            <Avatar sizeType={AVATAR_SIZE_TYPE.LARGE} src={user.socialProfileUrl} />
            <User>
              {user.nickname} · {user.ageRange?.meaning}
            </User>
          </ProfileContainer>
        ) : (
          <div style={{ height: '24.2rem' }} />
        )}

        <MenuContainer>
          <NavLinkElement exact to={PATH.HOME} onClick={() => setIsOpenSideBar(false)}>
            <HomeIcon width="22" height="22" stroke="currentColor" /> 홈
          </NavLinkElement>
          <NavLinkElement to={PATH.REVIEW} onClick={() => setIsOpenSideBar(false)}>
            <ReviewIcon width="23" height="23" stroke="currentColor" /> 접종후기
          </NavLinkElement>
          <NavLinkElement to={PATH.STATE} onClick={() => setIsOpenSideBar(false)}>
            <StateIcon width="23" height="23" stroke="currentColor" /> 접종현황
          </NavLinkElement>
          {!!Object.keys(user).length && (
            <MyPageMenuContainer>
              <NavLinkElement to={PATH.MY_PAGE_ACCOUNT} onClick={() => setIsOpenSideBar(false)}>
                <MyPageIcon width="22" height="22" stroke="currentColor" fill="currentColor" />
                마이페이지
              </NavLinkElement>

              <MyPageLink to={PATH.MY_PAGE_ACCOUNT} onClick={() => setIsOpenSideBar(false)}>
                정보 관리
              </MyPageLink>
              <MyPageLink to={PATH.MY_PAGE_REVIEW} onClick={() => setIsOpenSideBar(false)}>
                내가 쓴 글
              </MyPageLink>
              <MyPageLink to={PATH.MY_PAGE_COMMENT_REVIEW} onClick={() => setIsOpenSideBar(false)}>
                댓글 단 글
              </MyPageLink>
              <MyPageLink to={PATH.MY_PAGE_LIKE_REVIEW} onClick={() => setIsOpenSideBar(false)}>
                좋아요 누른 글
              </MyPageLink>
            </MyPageMenuContainer>
          )}
        </MenuContainer>

        {Object.keys(user).length ? (
          <LogoutButton onClick={logout}>
            <LogoutIcon width="24" height="24" stroke="currentColor" /> 로그아웃
          </LogoutButton>
        ) : (
          <NavLinkElement to={PATH.LOGIN} onClick={() => setIsOpenSideBar(false)}>
            <LoginIcon width="24" height="24" stroke="currentColor" /> 로그인
          </NavLinkElement>
        )}
      </Container>
    </Dimmer>
  );
};

SideBarMobile.propTypes = {
  logout: PropTypes.func.isRequired,
  isOpenSideBar: PropTypes.bool,
  setIsOpenSideBar: PropTypes.func,
};

SideBarMobile.defaultProps = {
  isOpenSideBar: false,
  setIsOpenSideBar: () => {},
};

export default SideBarMobile;
