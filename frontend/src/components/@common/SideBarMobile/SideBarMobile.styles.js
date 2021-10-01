import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link, NavLink } from 'react-router-dom';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';

const Dimmer = styled.div`
  ${({ isOpenSideBar }) =>
    isOpenSideBar &&
    css`
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background-color: rgba(0, 0, 0, 0.5);
      z-index: 2;
    `}
`;

const Container = styled.div`
  display: none;

  ${({ isOpenSideBar }) =>
    isOpenSideBar &&
    css`
      position: absolute;
      z-index: 3;
      display: flex;
      flex-direction: column;
      width: 80%;
      height: 100vh;
      padding: 1rem 0 1rem 0;
      background-color: ${THEME_COLOR.WHITE};

      & > *:last-child {
        /* margin-top: auto; */
      }
    `}

  @media screen and (min-width: 1025px) {
    display: none;
  }
`;

const MenuContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding-top: 1.2rem;
`;

const CloseIconContainer = styled.div`
  display: flex;
  justify-content: flex-start;
  padding: 0.4rem 0 0 1rem;
`;

const NavLinkElement = styled(NavLink)`
  display: flex;
  align-items: center;
  padding-left: 4rem;
  gap: 1.2rem;
  color: ${FONT_COLOR.BLACK};
  width: 100%;
  height: 4.4rem;
  font-size: 1.6rem;
`;

const LogoutButton = styled.button`
  display: flex;
  font-size: 1.6rem;
  align-items: center;
  padding-left: 4rem;
  gap: 0.8rem;
  color: ${FONT_COLOR.BLACK};
  width: 100%;
  height: 4.4rem;
  font-size: 1.6rem;
`;

const MyPageMenuContainer = styled.div`
  font-size: 1.6rem;
  color: ${FONT_COLOR.BLACK};
`;

const MyPageLink = styled(Link)`
  font-size: 1.4rem;
  color: ${FONT_COLOR.GRAY};
  display: flex;
  align-items: center;
  gap: 0.2rem;
  height: 2.8rem;
  margin-left: 7.4rem;
`;

const ProfileContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 3rem 0;
`;

const User = styled.div`
  margin-top: 1.2rem;
  font-size: 1.8rem;
  color: ${FONT_COLOR.BLACK};
`;

export {
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
};
