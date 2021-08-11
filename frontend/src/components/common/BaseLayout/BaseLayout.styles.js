import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { THEME_COLOR } from '../../../constants';

const Container = styled.div`
  display: flex;
  margin: 0 auto;
  width: 100%;
  height: 100%;
  background-color: ${THEME_COLOR.BACKGROUND};
  position: relative;

  @media screen and (max-width: 1024px) {
    flex-direction: column;
  }
`;

const NavBar = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 6rem;
  padding: 0 1.2rem 0 1rem;
  background-color: ${THEME_COLOR.WHITE};
  box-shadow: rgba(17, 17, 26, 0.05) 0px 4px 16px, rgba(17, 17, 26, 0.05) 0px 8px 32px;
  ${({ isOpenSideBar }) => isOpenSideBar && 'display: none;'}
  z-index:1;

  @media screen and (min-width: 1025px) {
    display: none;
  }
`;

const MainContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  overflow-y: auto;
  width: 100%;
`;

const TopContainer = styled.div`
  display: flex;
  gap: 0.6rem;
  justify-content: flex-end;
  padding: 2rem 8rem 0 0;
  min-height: 5.6rem;
  align-items: center;

  @media screen and (max-width: 1024px) {
    display: none;
  }
`;

const avatarStyles = css`
  &:hover {
    cursor: pointer;
  }
`;

const LogoContainer = styled.div`
  cursor: pointer;
`;

export { Container, MainContainer, TopContainer, avatarStyles, NavBar, LogoContainer };
