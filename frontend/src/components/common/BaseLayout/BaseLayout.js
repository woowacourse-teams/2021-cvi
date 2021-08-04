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
import { useSelector } from 'react-redux';
import { LogoIcon, MenuIcon } from '../../../assets/icons';
import { PATH, THEME_COLOR } from '../../../constants';
import { useHistory, useLocation } from 'react-router-dom';
import { useState } from 'react';
import Button from '../Button/Button';
import { BUTTON_BACKGROUND_TYPE } from '../Button/Button.styles';
import { css } from '@emotion/react';
import SideBarMobile from '../SideBarMobile/SideBarMobile';

const BaseLayout = ({ children }) => {
  const history = useHistory();
  const location = useLocation();
  const isLogin = !!useSelector((state) => state.authReducer.accessToken);
  const user = useSelector((state) => state.authReducer.user);

  const [isOpenSideBar, setIsOpenSideBar] = useState(false);

  const goMyPage = () => {
    history.push(PATH.MY_PAGE_SHOT_VERIFICATION);
  };

  const goHomePage = () => {
    history.push(PATH.HOME);
  };

  return (
    <Container>
      <SideBar />
      <SideBarMobile isOpenSideBar={isOpenSideBar} setIsOpenSideBar={setIsOpenSideBar} />
      <NavBar isOpenSideBar={false}>
        <Button
          backgroundType={BUTTON_BACKGROUND_TYPE.TEXT}
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
