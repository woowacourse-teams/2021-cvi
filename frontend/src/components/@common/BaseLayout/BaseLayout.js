import PropTypes from 'prop-types';
import Avatar from '../Avatar/Avatar';
import SideBar from '../SideBar/SideBar';
import {
  Container,
  MainContainer,
  TopContainer,
  avatarStyles,
  NavBar,
  LogoContainer,
} from './BaseLayout.styles';
import { useDispatch, useSelector } from 'react-redux';
import { LogoIcon, MenuIcon } from '../../../assets/icons';
import { LOCAL_STORAGE_KEY, PATH, SNACKBAR_MESSAGE, THEME_COLOR } from '../../../constants';
import { useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import Button from '../Button/Button';
import { BUTTON_BACKGROUND_TYPE } from '../Button/Button.styles';
import { css } from '@emotion/react';
import SideBarMobile from '../SideBarMobile/SideBarMobile';
import { getMyInfoAsync, logout as logoutAction } from '../../../redux/authSlice';
import { useMovePage, useSnackbar } from '../../../hooks';

const BaseLayout = ({ children }) => {
  const location = useLocation();
  const dispatch = useDispatch();
  const isLogin = !!useSelector((state) => state.authReducer.accessToken);
  const user = useSelector((state) => state.authReducer.user);

  const [isOpenSideBar, setIsOpenSideBar] = useState(false);

  const { openSnackbar } = useSnackbar();
  const { goMyPage, goHomePage } = useMovePage();

  const logout = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
    dispatch(logoutAction());

    openSnackbar(SNACKBAR_MESSAGE.SUCCESS_TO_LOGOUT);
    setIsOpenSideBar(false);
    goHomePage();
  };

  useEffect(() => {
    const accessToken = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN));

    if (!accessToken) return;

    dispatch(getMyInfoAsync(accessToken));
  }, []);

  return (
    <Container>
      <SideBar logout={logout} />
      <SideBarMobile
        isOpenSideBar={isOpenSideBar}
        setIsOpenSideBar={setIsOpenSideBar}
        logout={logout}
      />
      {/* mobile */}
      <NavBar isOpenSideBar={false}>
        <Button
          backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
          aria-label="open-sidebar-in-mobile"
          styles={css`
            padding: 0;
          `}
          onClick={() => setIsOpenSideBar(true)}
        >
          <MenuIcon width="36" height="36" stroke={THEME_COLOR.PRIMARY} />
        </Button>
        <LogoContainer onClick={goHomePage}>
          <LogoIcon width="56" fill={THEME_COLOR.PRIMARY} />
        </LogoContainer>
        {isLogin ? (
          <Avatar src={user.socialProfileUrl} styles={avatarStyles} onClick={goMyPage} />
        ) : (
          <div style={{ minWidth: '3.6rem' }} />
        )}
      </NavBar>
      {/* web */}
      <MainContainer>
        {!location.pathname.includes(PATH.MY_PAGE) && (
          <TopContainer isOpenSideBar={isOpenSideBar}>
            {isLogin && (
              <Avatar src={user.socialProfileUrl} styles={avatarStyles} onClick={goMyPage} />
            )}
          </TopContainer>
        )}
        {children}
      </MainContainer>
    </Container>
  );
};

BaseLayout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default BaseLayout;
