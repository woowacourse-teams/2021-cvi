import styled from '@emotion/styled';
import { Link, NavLink } from 'react-router-dom';
import { FONT_COLOR, THEME_COLOR } from '../../../constants';

const Container = styled.div`
  height: inherit;
  display: flex;
  flex-direction: column;
  padding-top: 6rem;
  width: 28rem;
  background-color: ${THEME_COLOR.PRIMARY};

  & > *:last-child {
    margin-top: 22rem;
  }
`;

const LogoContainer = styled(Link)`
  margin: 0 auto;
  padding-right: 1.6rem;
`;

const MenuContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 11rem;
`;

const NavLinkElement = styled(NavLink)`
  display: flex;
  align-items: center;
  padding-left: 7.4rem;
  margin-left: 1rem;
  gap: 0.8rem;
  color: ${FONT_COLOR.WHITE};
  width: 100%;
  height: 5.6rem;
  font-size: 1.8rem;
`;

const LogoutButton = styled.button`
  display: flex;
  font-size: 1.6rem;
  align-items: center;
  margin-left: 1rem;
  padding-left: 7.4rem;
  gap: 0.8rem;
  color: ${FONT_COLOR.WHITE};
  width: 100%;
  height: 5.2rem;
  font-size: 1.8rem;
`;

const selectedNavStyles = {
  backgroundColor: THEME_COLOR.BACKGROUND,
  color: FONT_COLOR.BLACK,
  borderTopLeftRadius: '1.6rem',
  borderBottomLeftRadius: '1.6rem',
  border: 'transparent',
};

export { Container, LogoContainer, MenuContainer, NavLinkElement, LogoutButton, selectedNavStyles };
