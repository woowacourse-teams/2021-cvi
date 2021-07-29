import PropTypes from 'prop-types';
import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useSnackbar } from 'notistack';
import {
  Container,
  LogoContainer,
  MenuContainer,
  CloseIconContainer,
  NavLinkElement,
  LogoutButton,
  selectedNavStyles,
} from './SideBarMobile.styles';
import { LOCAL_STORAGE_KEY, PATH, SNACKBAR_MESSAGE, THEME_COLOR } from '../../../constants';
import { getMyInfoAsync, logout as logoutAction } from '../../../redux/authSlice';
import {
  HomeIcon,
  LoginIcon,
  LogoIcon,
  LogoutIcon,
  ReviewIcon,
  MyPageIcon,
} from '../../../assets/icons';
import Button from '../Button/Button';
import { css } from '@emotion/react';

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

  const isRelatedMyPage = (pathname) =>
    [
      PATH.MY_PAGE,
      PATH.MY_PAGE_ACCOUNT,
      PATH.MY_PAGE_REVIEWS,
      PATH.MY_PAGE_SHOT_VERIFICATION,
    ].includes(pathname);

  useEffect(() => {
    const accessToken = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN));

    if (!accessToken) return;

    dispatch(getMyInfoAsync(accessToken));
  }, []);

  return (
    <Container isOpenSideBar={isOpenSideBar}>
      <CloseIconContainer>
        <Button
          styles={css`
            padding: 0;
          `}
          onClick={() => setIsOpenSideBar(false)}
        >
          <HomeIcon width="32" height="32" stroke="white" />
        </Button>
      </CloseIconContainer>
      <LogoContainer to={PATH.HOME}>
        <LogoIcon width="112" fill={THEME_COLOR.WHITE} />
      </LogoContainer>
      <MenuContainer>
        <NavLinkElement
          exact
          to={PATH.HOME}
          activeStyle={selectedNavStyles}
          onClick={() => setIsOpenSideBar(false)}
        >
          <HomeIcon width="20" height="20" stroke="currentColor" /> 홈
        </NavLinkElement>
        <NavLinkElement
          to={PATH.REVIEW}
          activeStyle={selectedNavStyles}
          onClick={() => setIsOpenSideBar(false)}
        >
          <ReviewIcon width="20" height="20" stroke="currentColor" /> 접종후기
        </NavLinkElement>
        {!!Object.keys(user).length && (
          <NavLinkElement
            to={PATH.MY_PAGE_SHOT_VERIFICATION}
            activeStyle={selectedNavStyles}
            isActive={(_, { pathname }) => isRelatedMyPage(pathname)}
            onClick={() => setIsOpenSideBar(false)}
          >
            <MyPageIcon width="20" height="20" stroke="currentColor" fill="currentColor" />{' '}
            마이페이지
          </NavLinkElement>
        )}
      </MenuContainer>

      {Object.keys(user).length ? (
        <LogoutButton onClick={logout}>
          <LogoutIcon width="24" height="24" stroke="currentColor" /> 로그아웃
        </LogoutButton>
      ) : (
        <NavLinkElement
          to={PATH.LOGIN}
          activeStyle={selectedNavStyles}
          onClick={() => setIsOpenSideBar(false)}
        >
          <LoginIcon width="24" height="24" stroke="currentColor" /> 로그인
        </NavLinkElement>
      )}
    </Container>
  );
};

SideBarMobile.propTypes = {
  isOpenSideBar: PropTypes.bool,
  setIsOpenSideBar: PropTypes.bool,
};

SideBarMobile.defaultProps = {
  isOpenSideBar: false,
  setIsOpenSideBar: false,
};

export default SideBarMobile;
