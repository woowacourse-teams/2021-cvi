import React from 'react';
import Frame from '../Frame/Frame';
import HomeIcon from '../../assets/icons/home.svg';
import ReviewIcon from '../../assets/icons/review.svg';
import LoginIcon from '../../assets/icons/login.svg';
import { Container, LogoContainer, MenuContainer, LinkContainer } from './SideBar.styles';
import { FONT_COLOR, PATH } from '../../constants';

const SideBar = () => {
  return (
    <Frame width="24rem" height="inherit">
      <Container>
        <LogoContainer>
          {/* example logo */}
          <HomeIcon width="80" height="80" stroke={FONT_COLOR.PURPLE_GRAY} />
        </LogoContainer>
        <MenuContainer>
          <LinkContainer to={PATH.HOME}>
            <HomeIcon width="20" height="20" stroke={FONT_COLOR.PURPLE_GRAY} /> 홈
          </LinkContainer>
          <LinkContainer to={PATH.REVIEW}>
            <ReviewIcon width="20" height="20" stroke={FONT_COLOR.PURPLE_GRAY} /> 접종후기
          </LinkContainer>
        </MenuContainer>
        <LinkContainer>
          <LoginIcon width="24" height="24" stroke={FONT_COLOR.PURPLE_GRAY} /> 로그인
        </LinkContainer>
      </Container>
    </Frame>
  );
};

export default SideBar;
