import PropTypes from 'prop-types';
import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useSnackbar } from 'notistack';
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
import { FONT_COLOR, LOCAL_STORAGE_KEY, PATH, SNACKBAR_MESSAGE } from '../../../constants';
import { getMyInfoAsync, logout as logoutAction } from '../../../redux/authSlice';
import {
  HomeIcon,
  LoginIcon,
  LogoutIcon,
  ReviewIcon,
  MyPageIcon,
  CloseIcon,
  MyReviewMenuIcon,
  ShorVerificationMenuIcon,
} from '../../../assets/icons';
import Button from '../Button/Button';
import { css } from '@emotion/react';
import { AVATAR_SIZE_TYPE } from '../Avatar/Avatar.styles';
import Avatar from '../Avatar/Avatar';
import { BUTTON_BACKGROUND_TYPE } from '../Button/Button.styles';

const SideBarMobile = ({ isOpenSideBar, setIsOpenSideBar }) => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.authReducer?.user);

  const { enqueueSnackbar } = useSnackbar();

  const logout = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
    dispatch(logoutAction());

    enqueueSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_LOGOUT);
    setIsOpenSideBar(false);
  };

  const closeSideBar = (event) => {
    if (event.target !== event.currentTarget) return;

    setIsOpenSideBar(false);
  };

  useEffect(() => {
    const accessToken = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN));

    if (!accessToken) return;

    dispatch(getMyInfoAsync(accessToken));
  }, []);

  return (
    <Dimmer isOpenSideBar={isOpenSideBar} onClick={closeSideBar}>
      <Container isOpenSideBar={isOpenSideBar}>
        <CloseIconContainer>
          <Button
            backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
            styles={css`
              padding: 0;
            `}
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
            <div>{user.shotVerified ? '접종인증 완료' : '접종인증 미완료'}</div>
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
          {!!Object.keys(user).length && (
            <MyPageMenuContainer>
              마이페이지
              <MyPageLink to={PATH.MY_PAGE_ACCOUNT} onClick={() => setIsOpenSideBar(false)}>
                <MyPageIcon width="22" height="22" stroke="currentColor" fill="currentColor" />내
                정보 관리
              </MyPageLink>
              <MyPageLink
                to={PATH.MY_PAGE_SHOT_VERIFICATION}
                onClick={() => setIsOpenSideBar(false)}
              >
                <ShorVerificationMenuIcon width="22" height="22" stroke="currentColor" />
                접종 인증
              </MyPageLink>
              <MyPageLink to={PATH.MY_PAGE_REVIEWS} onClick={() => setIsOpenSideBar(false)}>
                <MyReviewMenuIcon width="22" height="22" stroke="currentColor" />
                내가 쓴 글
              </MyPageLink>
            </MyPageMenuContainer>
          )}
        </MenuContainer>

        {Object.keys(user).length ? (
          <LogoutButton onClick={logout}>
            <LogoutIcon width="28" height="28" stroke="currentColor" /> 로그아웃
          </LogoutButton>
        ) : (
          <NavLinkElement to={PATH.LOGIN} onClick={() => setIsOpenSideBar(false)}>
            <LoginIcon width="28" height="28" stroke="currentColor" /> 로그인
          </NavLinkElement>
        )}
      </Container>
    </Dimmer>
  );
};

SideBarMobile.propTypes = {
  isOpenSideBar: PropTypes.bool,
  setIsOpenSideBar: PropTypes.func,
};

SideBarMobile.defaultProps = {
  isOpenSideBar: false,
  setIsOpenSideBar: () => {},
};

export default SideBarMobile;
